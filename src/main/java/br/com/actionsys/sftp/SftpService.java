package br.com.actionsys.sftp;

import lombok.extern.slf4j.Slf4j;

import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.RemoteResourceInfo;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SftpService {

    @Autowired
    private SftpConfig sftpConfig;

    public List<String> downloadFiles(String remoteDir, String localDir) {
        SSHClient sshClient = null;
        List<String> arquivosBaixados = new ArrayList<>();

        try {
            sshClient = getSSHClient(
                    sftpConfig.getHost(),
                    sftpConfig.getPort(),
                    sftpConfig.getUsername(),
                    sftpConfig.getFtpArquivoChave(),
                    sftpConfig.getPassword());

            SFTPClient sftpClient = sshClient.newSFTPClient();

            List<RemoteResourceInfo> arquivos = sftpClient.ls(remoteDir);
            for (RemoteResourceInfo arquivo : arquivos) {
                String remoteFilePath = remoteDir + "/" + arquivo.getName();
                Path localFilePath = Paths.get(localDir, arquivo.getName()).toAbsolutePath();

                sftpClient.get(remoteFilePath, localFilePath.toString());
                log.info("Arquivo baixado: {}", arquivo.getName());
                arquivosBaixados.add(arquivo.getName());
                deleteFile(remoteFilePath);
            }

            sftpClient.close();
        } catch (Exception e) {
            log.error("Erro ao baixar arquivos do servidor SFTP", e);
        } finally {
            if (sshClient != null) {
                desconectar(sshClient);
            }
        }

        return arquivosBaixados;
    }

    public void deleteFile(String remoteFilePath) {
        SSHClient sshClient = null;

        try {
            sshClient = getSSHClient(
                    sftpConfig.getHost(),
                    sftpConfig.getPort(),
                    sftpConfig.getUsername(),
                    sftpConfig.getFtpArquivoChave(),
                    sftpConfig.getPassword());

            SFTPClient sftpClient = sshClient.newSFTPClient();

            sftpClient.rm(remoteFilePath);

            sftpClient.close();
        } catch (Exception e) {
            log.error("Erro ao deletar arquivo do servidor SFTP: {}", remoteFilePath, e);
        } finally {
            if (sshClient != null) {
                desconectar(sshClient);
            }
        }
    }


    public void listFiles(String remoteDir) {
        SSHClient sshClient = null;

        try {
            sshClient = getSSHClient(
                    sftpConfig.getHost(),
                    sftpConfig.getPort(),
                    sftpConfig.getUsername(),
                    sftpConfig.getFtpArquivoChave(),
                    sftpConfig.getPassword());

            SFTPClient sftpClient = sshClient.newSFTPClient();

            List<RemoteResourceInfo> arquivos = sftpClient.ls(remoteDir);
            arquivos.forEach(arquivo -> log.info("Arquivo encontrado: {}", arquivo.getName()));

            sftpClient.close();

        } catch (Exception e) {
            log.error("Erro ao listar arquivos no servidor SFTP", e);
        } finally {
            if (sshClient != null) {
                desconectar(sshClient);
            }
        }
    }

    private SSHClient getSSHClient(String host, int porta, String usuario, String arquivoChave, String senha) {
        try {
            SSHClient sshClient = new SSHClient();
            sshClient.addHostKeyVerifier(new PromiscuousVerifier());

            if (porta != 0) {
                sshClient.connect(host, porta);
            } else {
                sshClient.connect(host);
            }

            if (!arquivoChave.isBlank()) {
                sshClient.authPublickey(usuario, arquivoChave);
            } else {
                sshClient.authPassword(usuario, senha);
            }

            return sshClient;

        } catch (Exception e) {
            log.error("Erro na conexão com o servidor SFTP", e);
            throw new RuntimeException("Erro na conexão com o servidor SFTP", e);
        }
    }

    private void desconectar(SSHClient sshClient) {
        try {
            sshClient.disconnect();
        } catch (Exception e) {
            log.warn("Erro ao desconectar do servidor SFTP", e);
        }
    }
}

