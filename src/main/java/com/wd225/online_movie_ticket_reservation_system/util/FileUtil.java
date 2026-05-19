package com.wd225.online_movie_ticket_reservation_system.util;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {
    public static final String FILE_PATH = "data/txt/movies.txt";

    // Create (movies - default)
    public static void saveToFile(String data) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(data);
            bw.newLine();
        }
    }

    // Read (movies - default)
    public static List<String> readAllLines() throws IOException {
        return readAllLines(FILE_PATH);
    }

    // Update & Delete (movies - default)
    public static void overwriteFile(List<String> lines) throws IOException {
        overwriteFile(FILE_PATH, lines);
    }
