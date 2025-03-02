/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package DAO;


import Model.GraphEdge;
import Model.Graph;
import View.Graphpage;
import Database.DatabaseConnection;
import java.sql.*;
import java.util.*;

public class GraphDAO {

    // Method to load graph data (nodes and edges)
    public Map<String, List<GraphEdge>> loadGraphData() {
        Map<String, List<GraphEdge>> adjacencyList = new HashMap<>();

        String nodesQuery = "SELECT name FROM Nodes";
        String edgesQuery = "SELECT n1.name AS node1, n2.name AS node2, e.cost, e.bandwidth " +
                             "FROM Edges e " +
                             "JOIN Nodes n1 ON e.node1_id = n1.id " +
                             "JOIN Nodes n2 ON e.node2_id = n2.id";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            // Load nodes
            try (ResultSet rs = stmt.executeQuery(nodesQuery)) {
                while (rs.next()) {
                    adjacencyList.put(rs.getString("name"), new ArrayList<>());
                }
            }

            // Load edges and populate adjacency list
            try (ResultSet rs = stmt.executeQuery(edgesQuery)) {
                while (rs.next()) {
                    String node1 = rs.getString("node1");
                    String node2 = rs.getString("node2");
                    int cost = rs.getInt("cost");
                    int bandwidth = rs.getInt("bandwidth");

                    // Avoid null nodes (should not happen if data is correct)
                    if (adjacencyList.containsKey(node1)) {
                        adjacencyList.get(node1).add(new GraphEdge(node1,node2, cost, bandwidth));
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return adjacencyList;
    }

    // Method to add a node to the database
    public void addNodeToDatabase(String nodeName) {
        String query = "INSERT IGNORE INTO Nodes (name) VALUES (?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nodeName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to add an edge to the database
    public void addEdgeToDatabase(String node1, String node2, int cost, int bandwidth) {
        String query = "INSERT INTO Edges (node1_id, node2_id, cost, bandwidth) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection()) {
            int node1Id = getNodeId(conn, node1);
            int node2Id = getNodeId(conn, node2);

            // Early exit if one of the nodes doesn't exist
            if (node1Id == -1 || node2Id == -1) {
                System.out.println("Error: One or both nodes do not exist.");
                return;
            }

            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setInt(1, node1Id);
                stmt.setInt(2, node2Id);
                stmt.setInt(3, cost);
                stmt.setInt(4, bandwidth);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to get the ID of a node by name
    private int getNodeId(Connection conn, String nodeName) throws SQLException {
        String query = "SELECT id FROM Nodes WHERE name = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nodeName);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
            }
        }
        return -1;
    }

    // Method to get all nodes from the database
    public List<String> getNodes() {
        List<String> nodes = new ArrayList<>();
        String query = "SELECT name FROM Nodes";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                nodes.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nodes;
    }

    // Method to get all edges from the database
    public List<String> getEdges() {
        List<String> edges = new ArrayList<>();
        String query = "SELECT n1.name AS node1, n2.name AS node2, e.cost, e.bandwidth " +
                       "FROM Edges e " +
                       "JOIN Nodes n1 ON e.node1_id = n1.id " +
                       "JOIN Nodes n2 ON e.node2_id = n2.id";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                edges.add(rs.getString("node1") + " -> " + rs.getString("node2") +
                        " (Cost: " + rs.getInt("cost") + ", Bandwidth: " + rs.getInt("bandwidth") + ")");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return edges;
    }
}