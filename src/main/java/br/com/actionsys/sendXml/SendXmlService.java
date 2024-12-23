package br.com.actionsys.sendXml;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SendXmlService {

    @Autowired
    OtmSendXmlClient otmSendXmlClient;

    public void sendXmlToOtm(String xml, String fileName, String id) {
        try {
            String response = otmSendXmlClient.sendXml(xml);
            log.info("Response OTM - arquivo {}, id: {} : {}", fileName, id, response);
        } catch (FeignException e) {
            log.error("Erro ao enviar XML para o OTM. Arquivo: {}. Erro: {}", fileName, e.getMessage());
            throw new RuntimeException("Erro ao enviar XML para OTM: " + fileName, e);
        } catch (Exception e) {
            log.error("Erro inesperado ao enviar XML. Arquivo: {}. Erro: {}", fileName, e.getMessage());
            throw new RuntimeException("Erro ao enviar XML para OTM: " + fileName, e);
        }

    }

}
