package br.com.actionsys.sendXml;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class OtmProperties {

    @Value("${otm.username}")
    private String username;

    @Value("${otm.password}")
    private String password;

}
