package br.com.actionsys.sendXml;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;

/** Cliente para envio de XMLs para o OTM */
@FeignClient(
        url = "${otm.send-xml.url}",
        name = "OtmSendXmlClient",
        configuration = OtmConfig.class)
public interface OtmSendXmlClient {

  @PostMapping(consumes = MediaType.APPLICATION_XML_VALUE)
  String sendXml(String xml);

}
