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

    // Create (generic - any file)
    public static void saveToFile(String fileName, String data) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            bw.write(data);
            bw.newLine();
        }
    }

    // Read (movies - default)
    public static List<String> readAllLines() throws IOException {
        return readAllLines(FILE_PATH);
    }

    // Read (generic - any file)
    public static List<String> readAllLines(String fileName) throws IOException {
        List<String> lines = new ArrayList<>();
        File file = new File(fileName);
        if (!file.exists()) return lines;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) {
                    lines.add(line);
                }
            }
        }
        return lines;
    }

    // Update & Delete (movies - default)
    public static void overwriteFile(List<String> lines) throws IOException {
        overwriteFile(FILE_PATH, lines);
    }

    // Update & Delete (generic - any file)
    public static void overwriteFile(String fileName, List<String> lines) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        }
    }
}
