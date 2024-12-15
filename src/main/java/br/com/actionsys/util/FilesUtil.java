package br.com.actionsys.util;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class FilesUtil {

    public static void move(Path origin, String to) {
        try {
            Files.move(origin, Paths.get(to), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao mover arquivo. De : " + origin + " para : " + to);
        }
    }

    public static List<Path> list(Path dir) {

        if (!Files.isDirectory(dir)) {
            throw new IllegalArgumentException("O caminho fornecido não é um diretório " + dir);
        }

        try (Stream<Path> paths = Files.walk(dir)) {
            return paths.filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Erro ao listar os arquivos do diretório.", e);
        }
    }

}
