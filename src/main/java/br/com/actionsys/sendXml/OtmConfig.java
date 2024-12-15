package br.com.actionsys.sendXml;

import feign.auth.BasicAuthRequestInterceptor;
import feign.codec.Encoder;
import feign.form.FormEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

public class OtmConfig {

    @Autowired
    OtmProperties otmProperties;

    @Bean
    public BasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new BasicAuthRequestInterceptor(otmProperties.getUsername(), otmProperties.getPassword());
    }

    @Bean
    public Encoder encoder() {
        return new FormEncoder();
    }

}
