package Assingment;

import java.util.*;

public class PackageDelivery {
    public static int minRoadsToTraverse(int[] packages, int[][] roads) {
        int n = packages.length;
        List<List<Integer>> graph = new ArrayList<>();
        
        // Step 1: Build the adjacency list representation of the graph
        for (int i = 0; i < n; i++) {
            graph.add(new ArrayList<>());
        }
        for (int[] road : roads) {
            graph.get(road[0]).add(road[1]);
            graph.get(road[1]).add(road[0]);
        }

        // Step 2: Identify important nodes (nodes with packages)
        Set<Integer> packageNodes = new HashSet<>();
        for (int i = 0; i < n; i++) {
            if (packages[i] == 1) {
                packageNodes.add(i);
            }
        }

        // Step 3: Use BFS to find the shortest paths
        boolean[] visited = new boolean[n];
        Queue<int[]> queue = new LinkedList<>();
        
        // Start from any node that has a package
        int startNode = packageNodes.iterator().next();
        queue.add(new int[]{startNode, 0});
        visited[startNode] = true;

        int totalDistance = 0;
        
        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            int node = curr[0], distance = curr[1];

            // If this node contains a package, collect it
            if (packageNodes.contains(node)) {
                totalDistance += distance * 2;  // We need to return as well
                packageNodes.remove(node);  // Mark the package as collected
            }

            // Add neighbors to the queue
            for (int neighbor : graph.get(node)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    queue.add(new int[]{neighbor, distance + 1});
                }
            }

            // If all packages are collected, break early
            if (packageNodes.isEmpty()) {
                break;
            }
        }

        return totalDistance;
    }

    public static void main(String[] args) {
        int[] packages1 = {1, 0, 0, 0, 0, 1};
        int[][] roads1 = {{0, 1}, {1, 2}, {2, 3}, {3, 4}, {4, 5}};
        System.out.println(minRoadsToTraverse(packages1, roads1)); // Output: 2

        int[] packages2 = {0, 0, 0, 1, 1, 0, 0, 1};
        int[][] roads2 = {{0, 1}, {0, 2}, {1, 3}, {1, 4}, {2, 5}, {5, 6}, {5, 7}};
        System.out.println(minRoadsToTraverse(packages2, roads2)); // Output: 2
    }
}

