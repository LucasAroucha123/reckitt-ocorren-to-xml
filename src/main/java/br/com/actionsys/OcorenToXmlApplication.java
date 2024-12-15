package br.com.actionsys;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@EnableScheduling
@SpringBootApplication(scanBasePackages = "br.com.actionsys")
@EnableFeignClients
@Component
@Slf4j
public class OcorenToXmlApplication implements ApplicationRunner {

    @Autowired
    private Environment env;

    public static void main(String[] args) {
        SpringApplication.run(OcorenToXmlApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("------------- {} {} -------------", env.getProperty("pom.project.name"), env.getProperty("pom.project.version"));
        log.info("Versão do java no pom : {}\n", env.getProperty("pom.java.version"));

        log.info("-------------OcorrenToXmlConfig-------------");
        log.info("schedule.delay-seconds : {}\n", env.getProperty("schedule.delay-seconds"));

        log.info("-------------Log-------------");
        log.info("dir.log : {}\n", env.getProperty("dir.log"));

        log.info("-------------Configurações de pastas TXT-------------");
        log.info("dir.txt.input : {}", env.getProperty("dir.txt.input"));
        log.info("dir.txt.output : {}", env.getProperty("dir.txt.output"));
        log.info("dir.txt.error : {}", env.getProperty("dir.txt.error"));
        log.info("dir.txt.others : {}", env.getProperty("dir.txt.others"));

        log.info("-------------Configurações de pastas XML-------------");
        log.info("dir.xml.output : {}", env.getProperty("dir.xml.output"));
        log.info("dir.xml.error : {}", env.getProperty("dir.xml.error"));

        log.info("-------------Configurações de pastas FTP-------------");
        log.info("ftp.input : {}", env.getProperty("ftp.input"));
        log.info("-------------------------------------------------------------");
    }
}