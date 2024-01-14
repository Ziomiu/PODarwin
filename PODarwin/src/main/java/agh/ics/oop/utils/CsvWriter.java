package agh.ics.oop.utils;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static com.opencsv.ICSVParser.DEFAULT_ESCAPE_CHARACTER;
import static com.opencsv.ICSVParser.DEFAULT_QUOTE_CHARACTER;
import static com.opencsv.ICSVWriter.DEFAULT_LINE_END;

public class CsvWriter {
    public File openSaveStatsDialog() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Zapisz statystyki");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Pliki CSV", "*.csv"));
        Stage saveConfigStage = new Stage();
        File selectedFile = fileChooser.showSaveDialog(saveConfigStage);
        if(containsData(selectedFile)){
            clearFile(selectedFile);
        }
        return selectedFile;
    }

    public void writeStatsToFile(ArrayList<String> statsList, File file) {
        String[] stats = statsList.toArray(new String[0]);
        String csvFilePath = file.toString();
        try {
            FileWriter fileWriter = new FileWriter(csvFilePath,true);
            CSVWriter csvWriter = new CSVWriter(
                fileWriter,
                ';',
                DEFAULT_QUOTE_CHARACTER,
                DEFAULT_ESCAPE_CHARACTER,
                DEFAULT_LINE_END
            );

            if(!containsData(file)) {
                String[] header = {"Day", "TotalGrass", "TotalAnimals","MostPopularGenome","Total tunnels",
                "PlacesLeft","AverageLifetime","AverageChildren","AverageEnergy"};
                csvWriter.writeNext(header);
            }
            csvWriter.writeNext(stats);
            csvWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static boolean containsData(File file) {
        if(!file.exists()){
            return false;
        }
        try (CSVReader csvReader = new CSVReader(new FileReader(file))) {
            List<String[]> lines = csvReader.readAll();
            return lines.size() > 1;
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        } catch (CsvException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    private void clearFile(File file) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
