package br.com.actionsys.sftp;

import br.com.actionsys.util.FilesUtil;
import lombok.extern.slf4j.Slf4j;
import net.schmizz.sshj.SSHClient;
import net.schmizz.sshj.sftp.RemoteResourceInfo;
import net.schmizz.sshj.sftp.SFTPClient;
import net.schmizz.sshj.sftp.SFTPException;
import net.schmizz.sshj.transport.verification.PromiscuousVerifier;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SftpService {

    public List<String> downloadFiles(String remoteDir, String localDir, SftpConfig config) {
        SSHClient sshClient = null;
        List<String> arquivosBaixados = new ArrayList<>();

        try {
            sshClient = getSSHClient(
                    config.getHost(),
                    config.getPort(),
                    config.getUsername(),
                    config.getFtpArquivoChave(),
                    config.getPassword());

            SFTPClient sftpClient = sshClient.newSFTPClient();

            List<RemoteResourceInfo> arquivos = sftpClient.ls(remoteDir);
            for (RemoteResourceInfo arquivo : arquivos) {
                String remoteFilePath = remoteDir + "/" + arquivo.getName();
                String localFilePath = Paths.get(localDir, arquivo.getName()).toAbsolutePath().toString();
                FilesUtil.createDirectoryIfNotNull(localDir);

                sftpClient.get(remoteFilePath, localFilePath);
                log.info("Arquivo baixado: {}", arquivo.getName());
                arquivosBaixados.add(arquivo.getName());
            }

            sftpClient.close();
        } catch (SFTPException e) {
            log.info("Nenhum arquivo encontrado.");
        } catch (Exception e) {
            log.error("Erro ao baixar arquivos do servidor SFTP", e);
        } finally {
            if (sshClient != null) {
                desconectar(sshClient);
            }
        }

        return arquivosBaixados;
    }

    public void deleteFile(String remoteFilePath, SftpConfig config) {
        SSHClient sshClient = null;

        try {
            sshClient = getSSHClient(
                    config.getHost(),
                    config.getPort(),
                    config.getUsername(),
                    config.getFtpArquivoChave(),
                    config.getPassword());

            SFTPClient sftpClient = sshClient.newSFTPClient();

            sftpClient.rm(remoteFilePath);
            log.info("Arquivo deletado com sucesso do SFTP: " + remoteFilePath);

            sftpClient.close();
        } catch (Exception e) {
            log.error("Erro ao deletar arquivo do servidor SFTP: {}", remoteFilePath, e);
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

