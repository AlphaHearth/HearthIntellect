package com.hearthintellect.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Paths;

public class IOUtils {
    private static final Logger LOG = LoggerFactory.getLogger(IOUtils.class);

    public static String readFile(String fileUrl) throws IOException {
        return new String(Files.readAllBytes(Paths.get(fileUrl)));
    }

    public static InputStream openConnWithRetry(URL url, int retryTime, int sleepTime) throws IOException {
        URLConnection conn = null;
        InputStream input = null;
        while (retryTime >= 0) {
            try {
                conn = url.openConnection();
                conn.setRequestProperty("User-Agent", "Mozilla");
                input = conn.getInputStream();
                break;
            } catch (IOException ex) {
                if (retryTime >= 0)
                    LOG.warn("Failed to open connection to `{}`. Retry in {}ms...",
                        url.toString(), sleepTime);
                else
                    throw ex;
                retryTime--;
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {}
            }
        }
        return input;
    }

}
