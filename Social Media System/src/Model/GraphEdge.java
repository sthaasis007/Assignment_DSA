/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Model;

public class GraphEdge {
    private String source;
    private String target;
    private int cost;
    private int bandwidth;

    // Constructor
    public GraphEdge(String source, String target, int cost, int bandwidth) {
        this.source = source;
        this.target = target;
        this.cost = cost;
        this.bandwidth = bandwidth;
    }

    // Getters and Setters
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getBandwidth() {
        return bandwidth;
    }

    public void setBandwidth(int bandwidth) {
        this.bandwidth = bandwidth;
    }

    @Override
    public String toString() {
        return "Source: " + source + ", Target: " + target + ", Cost: " + cost + ", Bandwidth: " + bandwidth;
    }
}
