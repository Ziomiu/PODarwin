package agh.ics.oop.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ConfigReader {
    public HashMap<String, Integer> readConfig() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Wybierz plik konfiguracyjny");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Pliki JSON", "*.json"));
        Stage readConfigStage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(readConfigStage);
        if (selectedFile != null) {
            return readFile(selectedFile);
        }
        return new HashMap<>();
    }


    private HashMap<String, Integer> readFile(File file) {
        HashMap<String, Integer> configMap = new HashMap<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            configMap = objectMapper.readValue(file, HashMap.class);
        } catch (IOException e) {
            System.err.println("Error loading JSON: " + file.getAbsolutePath());
            e.printStackTrace();
        }
        return configMap;
    }
}
