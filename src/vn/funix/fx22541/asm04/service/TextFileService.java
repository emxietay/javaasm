package vn.funix.fx22541.asm04.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class TextFileService {

    private static final String COMMA_DELIMITER = ",";
    private static final String CUSTOMER_NAME_REGEX = "*";

    public static List<List<String>> readFile(String fileName) {
        List<List<String>> result;
        try {
            result = Files.lines(Path.of(fileName))
//                    .filter(e -> e.matches(Validator.CUSTOMER_ID_REGEX + COMMA_DELIMITER + CUSTOMER_NAME_REGEX))
                    .map(e -> List.of(e.split(COMMA_DELIMITER)))
                    .toList();
//            System.out.println(result);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

}
