package com.library.Model;

import java.io.*;

public class Connections {
    
    private final String folderPath = "data";

    public void readAllCsvFiles() {
        File folder = new File(folderPath);
        File[] listOfFiles = folder.listFiles((dir, name) -> name.endsWith(".csv"));

        if (listOfFiles == null || listOfFiles.length == 0) {
            System.out.println("Tidak ada file CSV ditemukan di folder: " + folderPath);
            return;
        }

        for (File file : listOfFiles) {
            System.out.println("\n=== Membaca file: " + file.getName() + " ===");
            readFile(file.getPath());
        }
    }
    private void readFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] row = line.split(",");
                for (String value : row) {
                    System.out.printf("%-15s", value);
                }
                System.out.println();
            }
        } catch (IOException e) {
            System.out.println("Gagal membaca file: " + filePath);
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Connections connections = new Connections();
        connections.readAllCsvFiles();
    }
}
