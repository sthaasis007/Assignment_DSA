/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crawler.multithreadedwebcrawler;

import java.util.Set;
// import java.util.concurrent.ConcurrentHashMap;

public class WebCrawler implements Runnable {
    private final String url;
    private final UrlQueue urlQueue;
    private final DataStorage dataStorage;
    private final Set<String> visitedUrls; // Thread-safe set to track visited URLs

    public WebCrawler(String url, UrlQueue urlQueue, DataStorage dataStorage, Set<String> visitedUrls) {
        this.url = url;
        this.urlQueue = urlQueue;
        this.dataStorage = dataStorage;
        this.visitedUrls = visitedUrls;
    }

    @Override
    public void run() {
        try {
            // Check if URL is already visited to prevent duplicate crawling
            if (visitedUrls.contains(url)) {
                return; // Skip already visited URLs
            }
            visitedUrls.add(url); // Mark URL as visited

            System.out.println("🔍 Crawling: " + url);
            String html = HtmlFetcher.fetch(url);

            if (html != null && !html.isEmpty()) {
                // Save page content
                dataStorage.savePage(url, html);

                // Extract and enqueue new URLs
                Set<String> extractedUrls = UrlExtractor.extractUrls(html);
                for (String newUrl : extractedUrls) {
                    if (!visitedUrls.contains(newUrl)) { // Prevent duplicate URLs
                        urlQueue.addUrl(newUrl);
                    }
                }

                System.out.println("✅ Found " + extractedUrls.size() + " new links from " + url);
            } else {
                System.out.println("⚠️ No content fetched from: " + url);
            }
        } catch (Exception e) {
            System.err.println("❌ Error processing URL: " + url + " | " + e.getMessage());
        }
    }
}
