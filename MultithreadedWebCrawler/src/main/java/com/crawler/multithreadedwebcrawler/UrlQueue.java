/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.crawler.multithreadedwebcrawler;


import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class UrlQueue {
    private final Queue<String> queue = new ConcurrentLinkedQueue<>();
    private final Set<String> visitedUrls = ConcurrentHashMap.newKeySet();
    private final int maxDepth; // Optional: Control how deep the crawler goes

    public UrlQueue() {
        this.maxDepth = Integer.MAX_VALUE; // Default: No depth limit
    }

    public UrlQueue(int maxDepth) {
        this.maxDepth = maxDepth;
    }

    public synchronized void addUrl(String url) {
        if (visitedUrls.size() < maxDepth && visitedUrls.add(url)) { // Ensures uniqueness & depth control
            queue.offer(url);
        }
    }

    public String pollUrl() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int getVisitedSize() {
        return visitedUrls.size();
    }

    public int getQueueSize() {
        return queue.size();
    }
}
