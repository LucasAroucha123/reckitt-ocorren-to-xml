package br.com.actionsys.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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

            createDirectory(to);

            log.info("Movendo arquivo {} para {}", source.toAbsolutePath(), target.toAbsolutePath());
            Files.move(source, target, StandardCopyOption.REPLACE_EXISTING);
            // atualiza data e hora do arquivo movido
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

    public static void writeErrorLog(String fileName, Exception e, String dirError) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String logFileName = fileName.substring(0, fileName.lastIndexOf('.')) + ".log";

        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        e.printStackTrace(printWriter);
        String stackTrace = stringWriter.toString();

        String logContent = timestamp + " - Exception:\n" + stackTrace;

        try {
            createDirectory(dirError);
            Path logFilePath = Paths.get(dirError, logFileName);
            Files.writeString(logFilePath, logContent, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException ioException) {
            log.error("Erro ao gravar arquivo de log: {}", logFileName, ioException);
        }
    }




}
