/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package com.crawler.multithreadedwebcrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HtmlFetcher {
    public static String fetch(String url) {
        url = url.trim();
        try {
            System.out.println("🌐 Fetching: " + url);
            Document doc = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.150 Safari/537.36")
                    .timeout(5000)
                    .ignoreHttpErrors(true)
                    .get();

            String html = doc.html();
            System.out.println(" HTML length: " + html.length());

            return html; // ✅ Returns a plain string
        } catch (Exception e) {
            System.err.println(" Error fetching URL: " + url + " | " + e.getMessage());
            return ""; // ✅ Return empty string on failure
        }
    }
}

