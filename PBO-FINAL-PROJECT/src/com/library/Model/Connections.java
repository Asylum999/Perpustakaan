package com.library.Model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Connections {
    String file = "src/com/library/database/database.csv";

    public void readFile() {
        BufferedReader reader = null;
        String line;

        try {
            reader = new BufferedReader(new FileReader(file));
            while ((line = reader.readLine()) != null) {
                String[] row = line.split(", ");
                for (String index : row) {
                    System.out.printf("%-10s", index); // Fixed printf
                }
                System.out.println();
            }
        } catch (Exception e) { // Fixed syntax
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) reader.close(); // Avoid NullPointerException
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
