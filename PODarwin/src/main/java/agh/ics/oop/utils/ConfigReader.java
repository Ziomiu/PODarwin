package agh.ics.oop.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ConfigReader {
    private static final String folderPath = "./PODarwin/configs";

    public List<String> getConfigs() {
        List<String> fileNames = new ArrayList<>();
        File folder = new File(folderPath);
        if (!folder.exists() || !folder.isDirectory()) {
            return fileNames;
        }
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    fileNames.add(file.getName());
                }
            }
        }
        return fileNames;
    }

    public HashMap<String, Integer> readFile(String filePath) {
        HashMap<String, Integer> configMap = new HashMap<>();
        filePath = "./PODarwin/configs/" + filePath;
        File file = new File(filePath);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            configMap = objectMapper.readValue(file, HashMap.class);
        } catch (IOException e) {
            System.err.println("Błąd podczas odczytu pliku JSON: " + filePath);
            e.printStackTrace();
        }
        return configMap;
    }
}
