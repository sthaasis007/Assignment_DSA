/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crawler.multithreadedwebcrawler;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class DataStorage {
    private static final String FILE_PATH = "crawled_data.txt"; // Modify based on your path

    public DataStorage() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            System.out.println("⚠️ Storage file already exists. Deleting old data...");
            file.delete(); // Deletes old data to start fresh
        }
    }

    public void savePage(String url, String html) {
        try {
            Files.write(Paths.get(FILE_PATH), (url + "\n" + html + "\n\n").getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
            System.out.println("✅ Saved content from: " + url);
        } catch (IOException e) {
            System.err.println("❌ Error saving page: " + url + " | " + e.getMessage());
        }
    }
}
