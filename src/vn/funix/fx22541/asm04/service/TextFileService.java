package vn.funix.fx22541.asm04.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TextFileService {

    private static final String COMMA_DELIMITER = ",";

    public static List<List<String>> readFile(String fileName) {
        List<List<String>> result = new ArrayList<>();
        try {

            result = Files.lines(Path.of(fileName))
                    .map(e -> List.of(e.split(COMMA_DELIMITER)))
                    .toList();
            System.out.println("Importing customers... \n");
        } catch (NoSuchFileException e) {
            System.out.println("File not found. ");
        } catch (IOException e) {
            System.out.println("File not found. ");
        }
            return result;
    }
}
