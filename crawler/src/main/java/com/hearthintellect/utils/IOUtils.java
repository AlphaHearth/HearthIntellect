package com.hearthintellect.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class IOUtils {

    public static String readFile(String fileUrl) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileUrl)));
    }

}
