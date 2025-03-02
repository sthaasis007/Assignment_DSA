/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Model;

import java.util.*;

public class Graph {
    private Map<String, List<GraphEdge>> adjacencyList;

    public Graph() {
        adjacencyList = new HashMap<>();
    }

    public void addNode(String nodeName) {
        adjacencyList.putIfAbsent(nodeName, new ArrayList<>());
    }

    public void addEdge(String source, String target, int cost, int bandwidth) {
        GraphEdge edge = new GraphEdge(source,target, cost, bandwidth);
        adjacencyList.get(source).add(edge);
    }

    public Map<String, List<GraphEdge>> getAdjacencyList() {
        return adjacencyList;
    }

    public List<GraphEdge> getNeighbors(String node) {
        return adjacencyList.getOrDefault(node, new ArrayList<>());
    }
}
