package br.com.actionsys.sftp;

import lombok.Data;

@Data
public class SftpConfig {

    private String inputFolder;
    private String host;
    private int port;
    private String username;
    private String password;
    private String ftpArquivoChave;

    public SftpConfig(String inputFolder, String host, int port, String username, String password, String ftpArquivoChave) {
        this.inputFolder = inputFolder;
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.ftpArquivoChave = ftpArquivoChave;
    }
}