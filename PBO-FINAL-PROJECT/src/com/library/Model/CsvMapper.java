package com.library.Model;

public interface CsvMapper<T> {
    T mapFromCsv(String[] row);
}
