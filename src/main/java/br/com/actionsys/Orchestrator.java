package br.com.actionsys;

import br.com.actionsys.entity.ocorren.Ocorren;
import br.com.actionsys.entity.ocorren.OcorrenciaEntrega;
import br.com.actionsys.entity.ocorren.TxtOcoren;
import br.com.actionsys.sendXml.SendXmlService;
import br.com.actionsys.sftp.SftpConfig;
import br.com.actionsys.sftp.SftpConfigFactory;
import br.com.actionsys.sftp.SftpService;
import br.com.actionsys.util.CodeUtil;
import br.com.actionsys.util.FilesUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class Orchestrator {

    @Autowired
    private CodeUtil codeUtil;
    @Autowired
    private SendXmlService sendXmlService;
    @Autowired
    private SftpService sftpService;
    @Autowired
    private SftpConfigFactory sftpConfigFactory;

    // txt folders
    @Value("${dir.txt.input}")
    private String dirTxtInput;
    @Value("${dir.txt.error}")
    private String dirTxtError;
    @Value("${dir.txt.others}")
    private String dirTxtOthers;
    @Value("${dir.txt.output}")
    private String dirTxtOutput;

    // xml folders
    @Value("${dir.xml.error}")
    private String dirXmlError;
    @Value("${dir.xml.output}")
    private String dirXmlOutput;

    // Transportadoras
    private static final String TRANSPORTADORA_LUFT = "luft";
    private static final String TRANSPORTADORA_SOLISTICA = "solistica";

    public void process() {

        // Faz uma lista de SFTP
        try {
            List<SftpConfig> configs = List.of(
                    sftpConfigFactory.getSolisticaConfig(),
                    sftpConfigFactory.getLuftConfig()
            );

            for (SftpConfig config : configs) {
                // Baixa o Arquivo (Assim que ele baixa com sucesso, é excluído do SFTP)
                List<String> downloadedFiles = sftpService.downloadFiles(config.getInputFolder(), dirTxtInput, config);

                for (String file : downloadedFiles) {
                    Path filePath = Paths.get(dirTxtInput, file);
                    if (filePath.toString().endsWith(".txt")) {
                        try {
                            // Transforma txt para objeto Ocorren
                            Ocorren ocorren = readOcorenFile(filePath.toString());
                            // Gera xml OTM e envia para OTM.
                            generateSendXml(ocorren, file);

                            // Move para pasta de saída correspondente a transportadora
                            Path outputPath = Paths.get(dirTxtOutput, identifyCarrier(config.getUsername()), file);
                            FilesUtil.move(filePath.toFile(), outputPath.toString());
                            log.info("Arquivo processado e movido para saída: {}", file);
                        } catch (Exception e) {
                            Path errorPath = Paths.get(dirTxtError, file);
                            FilesUtil.writeErrorLog(file, e, dirTxtError);
                            FilesUtil.move(filePath.toFile(), errorPath.toString());
                            log.error("Erro ao processar arquivo: {}, movido para pasta de erro.", file, e);
                        }
                    } else {
                        // Caso não seja txt
                        Path othersPath = Paths.get(dirTxtOthers, file);
                        FilesUtil.move(filePath.toFile(), othersPath.toString());
                        log.info("Arquivo não suportado movido para 'outros': {}", file);
                    }
                }

            }
        } catch (Exception e) {
            log.error("Erro durante o processamento dos arquivos.", e);
        }
    }

    private Ocorren readOcorenFile(String filePath) throws IOException {
        Ocorren ocorren = new Ocorren();
        TxtOcoren txtOcoren = new TxtOcoren();
        List<OcorrenciaEntrega> ocorrenciaEntregasList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            if (br.readLine() == null) {
                throw new IOException("O arquivo está vazio: " + filePath);
            }

            String line;
            while ((line = br.readLine()) != null) {
                String idRegistry = line.substring(0, 3).trim();
                if (idRegistry.equals("541")) {
                    txtOcoren.setCnpjTransportadora(line.substring(8, 18));
                    ocorren.getOcorrencias().add(txtOcoren);
                }
                if (idRegistry.equals("542")) {
                    OcorrenciaEntrega ocorrenciaEntrega = new OcorrenciaEntrega();
                    ocorrenciaEntrega.setCodigoOcorrencia(line.substring(29, 32).trim());
                    ocorrenciaEntrega.setDataOcorrencia(line.substring(32, 40).trim());
                    ocorrenciaEntrega.setHoraOcorrencia(line.substring(40, 44).trim());
                    ocorrenciaEntrega.setIdEmbarque(line.substring(46, 66).trim());
                    ocorrenciaEntregasList.add(ocorrenciaEntrega);
                    txtOcoren.setOcorrenciasEntregaList(ocorrenciaEntregasList);
                    ocorren.getOcorrencias().add(txtOcoren);
                }
            }
            return ocorren;
        }
    }

    private void generateSendXml(Ocorren ocorren, String fileName) throws Exception {
        for (int i = 0; i < ocorren.getOcorrencias().size(); i++) {
            TxtOcoren txtOcoren = ocorren.getOcorrencias().get(i);
            String xmlTemplate = new String(Files.readAllBytes(
                    Paths.get(Objects.requireNonNull(getClass().getResource("/TemplateXml/XML_PADRAO_PEDIDO_OBD.xml")).toURI())));

            // Se o código ocorrência for igual a 001, não mandar para OTM.
            if (txtOcoren.getOcorrenciasEntregaList().get(i).getCodigoOcorrencia().equals("001")) {
                continue;
            }
            xmlTemplate = xmlTemplate.replace("${xidStatusCode}", codeUtil.transformCode(txtOcoren.getOcorrenciasEntregaList().get(i).getCodigoOcorrencia()));

            xmlTemplate = xmlTemplate.replace("${domainNameStatusCode}", "RBBR");
            xmlTemplate = xmlTemplate.replace("${xidTimeZoneGid}", "America/Sao_Paulo");
            xmlTemplate = xmlTemplate.replace("${gLogDateEventDt}", txtOcoren.getOcorrenciasEntregaList().get(i).getDataOcorrencia().substring(4)
                    + txtOcoren.getOcorrenciasEntregaList().get(i).getDataOcorrencia().substring(2, 4)
                    + txtOcoren.getOcorrenciasEntregaList().get(i).getDataOcorrencia().substring(0, 2)
                    + txtOcoren.getOcorrenciasEntregaList().get(i).getHoraOcorrencia() + "00");
            xmlTemplate = xmlTemplate.replace("${tZIdEventDt}", "America/Sao_Paulo");
            xmlTemplate = xmlTemplate.replace("${tZOffSetEventDt}", "-03:00");
            xmlTemplate = xmlTemplate.replace("${ssStop}", "2");
            xmlTemplate = xmlTemplate.replace("${domainNameStatusGroupGid}", "RBBR");
            xmlTemplate = xmlTemplate.replace("${xidStatusGroupGid}", "RB_CS_OCORRENCIAS_SG");
            xmlTemplate = xmlTemplate.replace("${domainNameRResponsiblePartyGid}", "RBBR");
            xmlTemplate = xmlTemplate.replace("${xidResponsiblePartyGid}", "RB_TRANSPORTADORA");
            xmlTemplate = xmlTemplate.replace("${gLogDateEventRecdDate}", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
            xmlTemplate = xmlTemplate.replace("${tZIdEventRecdDate}", "UTC");
            xmlTemplate = xmlTemplate.replace("${tZOffSetEventRecdDate}", "+00:00");
            xmlTemplate = xmlTemplate.replace("${domainNameReleaseGid}", "RBBR/RBC");
            xmlTemplate = xmlTemplate.replace("${xidReleaseGid}", txtOcoren.getOcorrenciasEntregaList().get(i).getIdEmbarque());
            xmlTemplate = xmlTemplate.replace("${attribute17}", "INFORMATIVA");

            sendXmlService.sendXmlToOtm(xmlTemplate, fileName, txtOcoren.getOcorrenciasEntregaList().get(i).getIdEmbarque());
            Files.write(Paths.get(Paths.get(dirXmlOutput, txtOcoren.getCnpjTransportadora(), fileName + "_" + i + ".xml").toAbsolutePath().toString()), xmlTemplate.getBytes());
        }
    }

    private String identifyCarrier(String carrier) {
        if (carrier.contains("Dev_ftp-int-solistica")) {
            return TRANSPORTADORA_SOLISTICA;
        } else if (carrier.contains("Dev_ftp-int-luft")) {
            return TRANSPORTADORA_LUFT;
        }
        return carrier;
    }

}