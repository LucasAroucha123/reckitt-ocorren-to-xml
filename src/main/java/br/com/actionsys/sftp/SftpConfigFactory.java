package br.com.actionsys.sftp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SftpConfigFactory {
    @Value("${sftp.solistica.folder}")
    private String solisticaFolder;
    @Value("${sftp.solistica.host}")
    private String solisticaHost;
    @Value("${sftp.solistica.port}")
    private int solisticaPort;
    @Value("${sftp.solistica.username}")
    private String solisticaUsername;
    @Value("${sftp.solistica.password}")
    private String solisticaPassword;
    @Value("${sftp.solistica.arquivoChave:}")
    private String solisticaArquivoChave;

    @Value("${sftp.luft.folder}")
    private String luftFolder;
    @Value("${sftp.luft.host}")
    private String luftHost;
    @Value("${sftp.luft.port}")
    private int luftPort;
    @Value("${sftp.luft.username}")
    private String luftUsername;
    @Value("${sftp.luft.password}")
    private String luftPassword;
    @Value("${sftp.luft.arquivoChave:}")
    private String luftArquivoChave;

    public SftpConfig getSolisticaConfig() {
        return new SftpConfig(solisticaFolder, solisticaHost, solisticaPort, solisticaUsername, solisticaPassword, solisticaArquivoChave);
    }

    public SftpConfig getLuftConfig() {
        return new SftpConfig(luftFolder, luftHost, luftPort, luftUsername, luftPassword, luftArquivoChave);
    }
}
