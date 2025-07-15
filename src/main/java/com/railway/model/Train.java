package com.railway.model;

public class Train {
    private int id;
    private String name;
    private String source;
    private String destination;
    private int totalSeats;

    public Train() {}

    public Train(int id, String name, String source, String destination, int totalSeats) {
        this.id = id;
        this.name = name;
        this.source = source;
        this.destination = destination;
        this.totalSeats = totalSeats;
    }

    public Train(String name, String source, String destination, int totalSeats) {
        this.name = name;
        this.source = source;
        this.destination = destination;
        this.totalSeats = totalSeats;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public int getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(int totalSeats) {
        this.totalSeats = totalSeats;
    }

    @Override
    public String toString() {
        return "Train [ID=" + id + ", Name=" + name + ", Source=" + source +
               ", Destination=" + destination + ", TotalSeats=" + totalSeats + "]";
    }
}
