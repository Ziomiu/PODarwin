package agh.ics.oop.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ConfigWriter {

    public void openSaveConfigDialog(HashMap<String, Integer> config) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Zapisz konfigurację");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Pliki JSON", "*.json"));
        Stage saveConfigStage = new Stage();
        File selectedFile = fileChooser.showSaveDialog(saveConfigStage);
        if (selectedFile != null) {
            writeConfigToFile(config, selectedFile);
        }
    }

    private void writeConfigToFile(HashMap<String, Integer> config, File file) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(file, config);
        } catch (IOException e) {
            System.err.println("Błąd podczas zapisu danych do pliku JSON: " + file.getAbsolutePath());
            e.printStackTrace();
        }
    }
}
