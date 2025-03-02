/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crawler.multithreadedwebcrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.HashSet;
import java.util.Set;

public class UrlExtractor {
    public static Set<String> extractUrls(String htmlContent) {
        Set<String> urls = new HashSet<>();
        
        try {
            Document doc = Jsoup.parse(htmlContent);
            Elements links = doc.select("a[href]");

            for (Element link : links) {
                String url = link.absUrl("href");
                if (!url.isEmpty() && (url.startsWith("http://") || url.startsWith("https://"))) {
                    urls.add(url);
                }
            }

            System.out.println("🔍 Extracted links: " + urls);

        } catch (Exception e) {
            System.err.println("❌ Error extracting URLs: " + e.getMessage());
        }

        return urls;
    }
}


