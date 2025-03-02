package Assingment;

import java.util.*;

//1.Two Ways to Connect Devices
//Install a communication module on a device at modules[i - 1] cost.
//Use a direct connection between devices at connections[j][2] cost.
//.Goal
//Find the minimum total cost to ensure that all n devices can communicate.
//Graph Representation
//We treat devices as nodes.
//Each possible connection is an edge with a cost.
//Additionally, each device has a virtual edge representing the cost of installing a module.

//Solution Approach
//Create an Extended Graph
//Include all given connections.
//Add an extra node (device 0) representing the option to install a module at each device.
//Add edges from this node to all n devices with costs from modules array.
//Find the Minimum Spanning Tree (MST)
//Since we need to connect all devices with minimum cost, we use Kruskal’s Algorithm (or Prim’s Algorithm).
//Kruskal's Algorithm is well-suited as we have a list of edges.

public class NetworkConnection {
    static class Edge {
        int u, v, cost;
        Edge(int u, int v, int cost) {
            this.u = u;
            this.v = v;
            this.cost = cost;
        }
    }

    static class UnionFind {
        int[] parent, rank;
        
        UnionFind(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) parent[i] = i;
        }
        
        int find(int x) {
            if (parent[x] != x) 
                parent[x] = find(parent[x]);
            return parent[x];
        }
        
        boolean union(int x, int y) {
            int rootX = find(x), rootY = find(y);
            if (rootX == rootY) return false;
            
            if (rank[rootX] > rank[rootY]) 
                parent[rootY] = rootX;
            else if (rank[rootX] < rank[rootY]) 
                parent[rootX] = rootY;
            else {
                parent[rootY] = rootX;
                rank[rootX]++;
            }
            return true;
        }
    }

    public static int minNetworkCost(int n, int[] modules, int[][] connections) {
        List<Edge> edges = new ArrayList<>();
        
        // Add module installation as virtual edges (connecting device i to node 0)
        for (int i = 0; i < n; i++) {
            edges.add(new Edge(0, i + 1, modules[i]));
        }
        
        // Add given network connections
        for (int[] conn : connections) {
            edges.add(new Edge(conn[0], conn[1], conn[2]));
        }

        // Sort edges by cost
        edges.sort(Comparator.comparingInt(e -> e.cost));

        // Use Kruskal's algorithm to find MST
        UnionFind uf = new UnionFind(n + 1);
        int totalCost = 0, edgesUsed = 0;
        
        for (Edge edge : edges) {
            if (uf.union(edge.u, edge.v)) {
                totalCost += edge.cost;
                edgesUsed++;
                if (edgesUsed == n) break; // Stop when we have n connections
            }
        }

        return totalCost;
    }

    public static void main(String[] args) {
        int n = 3;
        int[] modules = {1, 2, 2};
        int[][] connections = {{1, 2, 1}, {2, 3, 1}};
        
        System.out.println(minNetworkCost(n, modules, connections)); // Output: 3
    }
}

