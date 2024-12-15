package br.com.actionsys;

import br.com.actionsys.entity.ocoren.Ocorren;
import br.com.actionsys.entity.ocoren.OcorrenciaEntrega;
import br.com.actionsys.entity.ocoren.TxtOcoren;
import br.com.actionsys.sendXml.SendXmlService;
import br.com.actionsys.sftp.SftpService;
import br.com.actionsys.util.CodeUtil;
import br.com.actionsys.util.FilesUtil;
import jakarta.annotation.PostConstruct;
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

    // Sftp Folders
    @Value("${sftp.input.solistica}")
    private String dirInputSolistica;
    @Value("${sftp.input.luft}")
    private String dirInputLuft;

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

    // Cria os diretorios caso não haja
    @PostConstruct
    public void initializeDirectories() {
        createDirectoryIfNotExists(dirTxtInput + TRANSPORTADORA_LUFT);
        createDirectoryIfNotExists(dirTxtInput + TRANSPORTADORA_SOLISTICA);
        createDirectoryIfNotExists(dirTxtError);
        createDirectoryIfNotExists(dirTxtOthers);
        createDirectoryIfNotExists(dirXmlError);
        createDirectoryIfNotExists(dirXmlOutput + TRANSPORTADORA_LUFT);
        createDirectoryIfNotExists(dirXmlOutput + TRANSPORTADORA_SOLISTICA);
    }

    private void createDirectoryIfNotExists(String dirPath) {
        try {
            Path path = Paths.get(dirPath).toAbsolutePath();
            if (!Files.exists(path)) {
                Files.createDirectories(path);
                log.info("Diretório criado: {}", path);
            }
        } catch (IOException e) {
            log.error("Erro ao criar o diretório: {}", dirPath, e);
        }
    }

    public void process() {
        try {
            log.info("Iniciando processamento de arquivos");

            // Lê o diretório das duas transportadoras
            List<String> directoryList = List.of(dirInputSolistica, dirInputLuft);

            for (String directory : directoryList) {

                // Baixa o Arquivo (Assim que ele baixa com sucesso, é excluído do SFTP)
                List<String> downloadedFiles = sftpService.downloadFiles(directory, dirTxtInput);

                for (String file : downloadedFiles) {
                    Path filePath = Paths.get(dirTxtInput, file);
                    if (filePath.toString().endsWith(".txt")) {
                        try {
                            // Identifica qual transportadora estamos a processar
                            String carrier = identifyCarrier(directory);

                            // Transforma txt para objeto Ocorren
                            Ocorren ocorren = readOcorenFile(filePath.toString());
                            // Gera xml OTM e envia para OTM.
                            generateSendXml(ocorren, file, carrier);

                            // Move para pasta de saída correspondente a transportadora
                            Path outputPath = Paths.get(dirTxtOutput, carrier, file);
                            FilesUtil.move(filePath, outputPath.toString());
                            log.info("Arquivo processado e movido para saída: {}", file);
                        } catch (Exception e) {
                            Path errorPath = Paths.get(dirTxtError, file);
                            FilesUtil.move(filePath, errorPath.toString());
                            log.error("Erro ao processar arquivo: {}, movido para pasta de erro.", file, e);
                        }
                    } else {
                        // Caso não seja txt
                        Path othersPath = Paths.get(dirTxtOthers, file);
                        FilesUtil.move(filePath, othersPath.toString());
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

    private void generateSendXml(Ocorren ocorren, String fileName, String carrier) throws Exception {
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
            Files.write(Paths.get(Paths.get(dirXmlOutput, carrier, fileName + "_" + i + ".xml").toAbsolutePath().toString()), xmlTemplate.getBytes());
        }
    }

    private String identifyCarrier(String filePath) {
        if (filePath.contains(TRANSPORTADORA_SOLISTICA)) {
            return TRANSPORTADORA_SOLISTICA;
        } else if (filePath.contains(TRANSPORTADORA_LUFT)) {
            return TRANSPORTADORA_LUFT;
        }
        return filePath;
    }

}