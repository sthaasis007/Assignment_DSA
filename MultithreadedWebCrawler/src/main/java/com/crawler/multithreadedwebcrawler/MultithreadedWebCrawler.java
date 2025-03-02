/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.crawler.multithreadedwebcrawler;

import java.util.Set;
import java.util.concurrent.*;

public class MultithreadedWebCrawler {
    private static final int THREAD_POOL_SIZE = 5;
    private static final int MAX_PAGES_TO_CRAWL = 20; // Limit the number of pages

    public static void main(String[] args) {
        System.out.println("🚀 Crawler is starting...");

        UrlQueue urlQueue = new UrlQueue();
        urlQueue.addUrl("https://example.com"); // Initial seed URL
        DataStorage dataStorage = new DataStorage(); // Data storage instance

        Set<String> visitedUrls = ConcurrentHashMap.newKeySet(); // Thread-safe visited URLs set

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        while (visitedUrls.size() < MAX_PAGES_TO_CRAWL) {
            String url = urlQueue.pollUrl();
            if (url != null && !visitedUrls.contains(url)) {
                visitedUrls.add(url); // Mark URL as visited before submission
                executor.execute(new WebCrawler(url, urlQueue, dataStorage, visitedUrls));
            }
        }

        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        System.out.println("✅ Crawling complete!");
    }
}
