package br.com.actionsys.sftp;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class SftpConfig {
    @Value("${sftp.host}")
    private String host;

    @Value("${sftp.port:0}")
    private int port;

    @Value("${sftp.username}")
    private String username;

    @Value("${sftp.password}")
    private String password;

    @Value("${sftp.arquivoChave:}")
    private String ftpArquivoChave;
}