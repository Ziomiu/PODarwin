package agh.ics.oop.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ConfigWriter {

    public boolean writeConfigToFile(HashMap<String, Integer> config, String fileName) {
        String folderPath = "./PODarwin/configs";
        File folder = new File(folderPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String filePath = folderPath + File.separator + fileName;
        File file = new File(filePath);
        if (file.exists()) {
            return false;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(file, config);
        } catch (IOException e) {
            System.err.println("Błąd podczas zapisu danych do pliku JSON: " + filePath);
            e.printStackTrace();
        }
        return true;
    }
}
