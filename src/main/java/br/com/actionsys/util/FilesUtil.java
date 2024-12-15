package br.com.actionsys.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.FileTime;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
public class FilesUtil {

    public static void write(String path, List<String> lines) throws IOException {
        write(Paths.get(path), lines);
    }

    public static void write(Path path, List<String> lines) throws IOException {
        Files.createDirectories(path.getParent());
        Files.write(path, lines, StandardCharsets.UTF_8, StandardOpenOption.CREATE);
    }

    public static void write(String path, String content) {
        write(Paths.get(path), content);
    }

    public static void write(Path path, String content) {
        write(path, content.getBytes());
    }

    public static void add(Path path, byte[] content) {
        write(path, content, StandardOpenOption.APPEND);
    }

    public static void write(Path path, byte[] content) {
        write(path, content, StandardOpenOption.CREATE);
    }

    public static void write(Path path, byte[] content, StandardOpenOption standardOpenOption) {
        try {
            Files.createDirectories(path.getParent());
            Files.write(path, content, standardOpenOption);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void move(File origin, String to) {
        try {
            Path source = Paths.get(origin.getAbsolutePath());
            Path target = Paths.get(to);
            log.info("Movendo arquivo {} para {}", source.toAbsolutePath(), target.toAbsolutePath());
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
            // atualiza data e hora do aquivo movido
            Files.setLastModifiedTime(target, FileTime.fromMillis(System.currentTimeMillis()));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao mover arquivo. De : " + origin.getAbsolutePath() + " para : " + to);
        }
    }

    public static void createDirectoryIfNotNull(String dirPath) {
        if (dirPath != null) {
            FilesUtil.createDirectory(dirPath);
        }
    }

    public static void createDirectory(String directory) {
        File file = new File(directory);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    public static List<String> readLines(Path file) {
        try {
            return Files.readAllLines(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Stream<String> readLines(Resource resource) {
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            return bufferedReader.lines();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void atomicWrite(ByteArrayOutputStream out, String fileTempStr, String fileWorkStr) throws Exception {
        File fi = new File(fileTempStr);
        if (fi.exists()) {
            fi.delete();
            log.info("Arquivo já existia e foi removido : " + fileTempStr);
        }

        File fileTemp = new File(fileTempStr);
        try (FileOutputStream fileTempO = new FileOutputStream(fileTemp)) {
            fileTempO.write(out.toByteArray());
        } catch (Exception e) {
            throw new Exception("Erro ao escrever anexo da memória ram para o disco, nome do arquivo : " + fileTempStr, e);
        }

        File fileWork = new File(fileWorkStr);
        if (fileWork.exists()) {
            log.warn("Arquivo já existe na pasta work, ele será sobreescrito. : " + fileWorkStr);
        }
        fileTemp.renameTo(fileWork);
    }

    public static void deleteFile(String fileUrl) throws IOException {

        Files.deleteIfExists(Path.of(fileUrl));
    }

    public static void deleteDir(String dirUrl) throws IOException {

        try (Stream<Path> paths = Files.walk(Path.of(dirUrl))) {
            paths.sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
    }

    public static void copyDirectory(Path sourcePath, String destDir) throws IOException {

        log.info("Copiando diretorios {} para {}", sourcePath, destDir);

        try (Stream<Path> paths = Files.walk(sourcePath)) {

            for (Path source : paths.toList()) {

                Path destination = Paths.get(destDir, source.toString().substring(sourcePath.toString().length()));

                //TODO DEVE SUBSTITUIR ARQUIVOS EXISTENTES?
                if (!Files.exists(destination) || !Files.isDirectory(destination)) {
                    Files.createDirectories(Path.of(destDir));
                    Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }

    public static Path convertUriToPath(URI uri, String relativePath) throws IOException {
        if (uri.getScheme().contains("jar")) {
            try {
                return FileSystems.newFileSystem(uri, Collections.emptyMap()).getPath(relativePath);
            } catch (FileSystemAlreadyExistsException e) {
                return FileSystems.getFileSystem(uri).getPath(relativePath);
            }
        } else {
            return Path.of(uri);
        }
    }

    public static Path insertDatePathInFile(Path path, LocalDate localDate) {

        return path.getParent()
                .resolve(localDate.format(DateTimeFormatter.ofPattern("yyyy-MM")))
                .resolve(path.getFileName());
    }

}
