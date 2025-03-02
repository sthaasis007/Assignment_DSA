/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package Controller;

import Model.Graph;
import Model.GraphEdge;
import View.Graphpage;
import DAO.GraphDAO;
import java.util.*;

public class GraphController {
    private Graphpage graphView;
    private Graph graph;
    private GraphDAO graphDAO;

    // Constructor with Dependency Injection
    public GraphController(Graphpage graphView, GraphDAO graphDAO, Graph graph) {
        this.graphView = graphView;
        this.graphDAO = graphDAO;
        this.graph = graph;
    }

    // Initialize Graph Page (called externally instead of constructor)
    public void initialize() {
        loadGraph(); // Load existing data from DB

        if (graphView != null) {
            graphView.setVisible(true); // Ensure the UI is displayed
        }
    }

    // Show the graph page
    public void showGraphPage() {
        if (graphView != null) {
            graphView.setVisible(true);
        }
    }

    // Load graph data from database
    public void loadGraph() {
        Map<String, List<GraphEdge>> adjacencyList = graphDAO.loadGraphData();
        adjacencyList.forEach((node, edges) -> {
            graph.addNode(node);
            edges.forEach(edge -> graph.addEdge(node, edge.getTarget(), edge.getCost(), edge.getBandwidth()));
        });

        // Update the UI with loaded graph data (Ensure graphView is not null)
        if (graphView != null) {
            graphView.updateGraphView(graph.getAdjacencyList());
        }
    }

    // Add a new node
    public void addNode(String nodeName) {
        graph.addNode(nodeName);
        graphDAO.addNodeToDatabase(nodeName);
    }

    // Add an edge with cost and bandwidth
    public void addEdge(String source, String target, int cost, int bandwidth) {
        graph.addEdge(source, target, cost, bandwidth);
        graphDAO.addEdgeToDatabase(source, target, cost, bandwidth);
    }

    // Get neighbors of a node
    public List<GraphEdge> getNeighbors(String node) {
        return graph.getNeighbors(node);
    }

    // Check if an edge exists
    public boolean edgeExists(String source, String target) {
        return graph.getNeighbors(source).stream()
                .anyMatch(edge -> edge.getTarget().equals(target));
    }

    // Get adjacency list representation
    public String getAdjacencyList() {
        StringBuilder result = new StringBuilder("Adjacency List:\n");
        for (Map.Entry<String, List<GraphEdge>> entry : graph.getAdjacencyList().entrySet()) {
            result.append(entry.getKey()).append(" -> ").append(entry.getValue()).append("\n");
        }
        return result.toString();
    }

    // Find the shortest path between two nodes using Dijkstra's Algorithm
    public String findShortestPath(String source, String target) {
        // Check if source and target nodes exist in the graph
        if (!graph.getAdjacencyList().containsKey(source) || !graph.getAdjacencyList().containsKey(target)) {
            return "Source or target node does not exist.";
        }

        // Initialize distances and previous node maps
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previousNodes = new HashMap<>();
        PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));

        // Initialize distances for all nodes as infinity, except the source node
        for (String node : graph.getAdjacencyList().keySet()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(source, 0); // Distance to source is 0

        // Add source node to the priority queue
        pq.add(new AbstractMap.SimpleEntry<>(source, 0));

        while (!pq.isEmpty()) {
            String currentNode = pq.poll().getKey();

            // If we reached the target node, break early
            if (currentNode.equals(target)) {
                break;
            }

            // Explore neighbors
            for (GraphEdge edge : graph.getNeighbors(currentNode)) {
                String neighbor = edge.getTarget();
                int newDist = distances.get(currentNode) + edge.getCost();

                // If a shorter path to the neighbor is found, update the distance and previous node
                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    previousNodes.put(neighbor, currentNode);
                    pq.add(new AbstractMap.SimpleEntry<>(neighbor, newDist));
                }
            }
        }

        // Reconstruct the path from target to source
        List<String> path = new ArrayList<>();
        String current = target;
        while (previousNodes.containsKey(current)) {
            path.add(current);
            current = previousNodes.get(current);
        }

        Collections.reverse(path); // Reverse the path to show from source to target

        if (path.isEmpty()) {
            return "No path found between " + source + " and " + target + ".";
        }

        // Build the result string with the path and its cost
        StringBuilder result = new StringBuilder("Shortest Path from " + source + " to " + target + ": ");
        result.append(String.join(" -> ", path));
        result.append("\nTotal Cost: ").append(distances.get(target));

        return result.toString();
    }

    // Optimize the network by finding the Minimum Spanning Tree (MST) using Kruskal's Algorithm
    public String optimizeNetwork() {
        // List to store all edges in the graph
        List<GraphEdge> edges = new ArrayList<>();
        for (Map.Entry<String, List<GraphEdge>> entry : graph.getAdjacencyList().entrySet()) {
            for (GraphEdge edge : entry.getValue()) {
                edges.add(edge);
            }
        }

        // Sort edges by cost (for MST, we usually want to minimize the cost)
        edges.sort(Comparator.comparingInt(GraphEdge::getCost));

        // Disjoint-set (Union-Find) to help with cycle detection
        UnionFind uf = new UnionFind(graph.getAdjacencyList().keySet());

        // List to store the edges that are part of the MST
        List<GraphEdge> mstEdges = new ArrayList<>();

        // Kruskal's algorithm
        for (GraphEdge edge : edges) {
            String source = edge.getSource();
            String target = edge.getTarget();

            // Check if adding this edge creates a cycle (using union-find)
            if (uf.find(source) != uf.find(target)) {
                mstEdges.add(edge);
                uf.union(source, target);  // Union the sets
            }
        }

        // Build the result string showing the edges in the MST
        if (mstEdges.isEmpty()) {
            return "No edges available to optimize.";
        }

        StringBuilder result = new StringBuilder("Optimized Network (Minimum Spanning Tree):\n");
        int totalCost = 0;
        for (GraphEdge edge : mstEdges) {
            result.append(edge.getSource()).append(" -> ").append(edge.getTarget())
                  .append(" [Cost: ").append(edge.getCost()).append(", Bandwidth: ")
                  .append(edge.getBandwidth()).append("]\n");
            totalCost += edge.getCost();
        }
        result.append("Total Cost of Optimized Network: ").append(totalCost);

        return result.toString();
    }

}

// UnionFind (Disjoint-Set) Helper Class
class UnionFind {
    private Map<String, String> parent;
    private Map<String, Integer> rank;

    public UnionFind(Set<String> nodes) {
        parent = new HashMap<>();
        rank = new HashMap<>();
        for (String node : nodes) {
            parent.put(node, node);  // Each node is its own parent initially
            rank.put(node, 0);       // Initially, the rank is 0 for all nodes
        }
    }

    public String find(String node) {
        if (!parent.get(node).equals(node)) {
            parent.put(node, find(parent.get(node)));  // Path compression
        }
        return parent.get(node);
    }

    public void union(String node1, String node2) {
        String root1 = find(node1);
        String root2 = find(node2);

        // Union by rank: attach the smaller tree to the root of the larger tree
        if (root1.equals(root2)) {
            return;
        }

        if (rank.get(root1) > rank.get(root2)) {
            parent.put(root2, root1);
        } else if (rank.get(root1) < rank.get(root2)) {
            parent.put(root1, root2);
        } else {
            parent.put(root2, root1);
            rank.put(root1, rank.get(root1) + 1);  // Increase rank if both trees have the same rank
        }
    }
}
