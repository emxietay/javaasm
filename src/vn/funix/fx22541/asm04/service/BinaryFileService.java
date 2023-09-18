package vn.funix.fx22541.asm04.service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class BinaryFileService {
    public static <T> List<T>  readFile(String fileName) {
        List<T> objects = new ArrayList<>();
        try (var ois = new ObjectInputStream(
                            new BufferedInputStream(
                                Files.newInputStream(
                                    Path.of(fileName)
                                )))) {
            boolean eof = false;
            while (!eof) {
                try {
                    var object = ois.readObject();
                    if (object != null) {
                        objects.add((T) object);
                    }
                } catch (EOFException e) {
                    eof = true;
                }
            }

        } catch (EOFException e) {
            return new ArrayList<>();
        } catch (IOException ioException) {
            System.out.println("IO Exception " + ioException.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println(e.getClass().toString() + e.getMessage());
        }
        return objects;
    }

    public static <T> void writeFile(String fileName, List<T> objects) {
        try (var oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)))) {
            for (T object : objects) {
                oos.writeObject(object);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
