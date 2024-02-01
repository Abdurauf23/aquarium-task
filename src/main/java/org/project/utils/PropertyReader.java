package org.project.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class PropertyReader {
    public static final String propertyFileName = "src/main/resources/application.properties";

    public static Map<String, String> readProperties() {
        Map<String, String> props = new HashMap<>();
        try {
            String properties = Files.readString(Paths.get(propertyFileName));
            String[] splittedString = properties.split("\n");
            for (String line : splittedString) {
                String[] keyValue = line
                        .replace(" ", "")
                        .replace("\r", "")
                        .split("=");
                props.put(keyValue[0], keyValue[1]);
            }

        } catch (IOException e) {
            System.out.println("""
                    Error in reading properties file. Please make sure file exists at resources folder and is correct.
                    Correct format:
                    key=value
                    key=value""");
        }
        return props;
    }
}
