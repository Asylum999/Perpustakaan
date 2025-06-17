package com.library.Model;

import java.io.BufferedReader;
import java.io.FileReader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

public class Connections {
    String file = "src\\database.csv";
    BufferedReader br = null;
    String line = "";

        try {
        Reader = new BufferedReader(new FileReader(file));
        while((line = reader.readline()) != null) {

            String[] row = line.split(", ");

        }
    }
        catch (Exception e;) {

    }
    finally
}
