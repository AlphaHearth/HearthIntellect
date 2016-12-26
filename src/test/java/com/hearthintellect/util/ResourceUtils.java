package com.hearthintellect.util;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class ResourceUtils {

    public static String readResource(String fileName) {
        URL resourceUrl = ResourceUtils.class.getClassLoader().getResource(fileName);
        if (resourceUrl == null)
            throw new AssertionError("Failed to find `" + fileName + "` in resources folder. Check if it is deleted.");
        try {
            return new String(Files.readAllBytes(Paths.get(resourceUrl.toURI())));
        } catch (URISyntaxException ex) {
            throw new AssertionError("Failed to convert URL `" + resourceUrl.toString() + "` to URI.", ex);
        } catch (IOException ex) {
            throw new AssertionError("Failed to read from `" + resourceUrl.toString() + "`.", ex);
        }
    }

    public static <T> T readResrouceJsonAsEntity(String resourceJsonPath, Type requestedType, Gson gson) {
        String content = readResource(resourceJsonPath);
        try {
            return gson.fromJson(content, requestedType);
        } catch (JsonSyntaxException ex) {
            throw new AssertionError("Failed to parse the given file as requested JSON entity.", ex);
        }
    }

}
