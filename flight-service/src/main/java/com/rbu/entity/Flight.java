package com.rbu.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Flight {

    @Id
    private String code;
    private String carrier;
    private String source;
    private String destination;
    private double cost;

    public Flight() {
    }

    public Flight(String code, String carrier, String source, String destination, double cost) {
        this.code = code;
        this.carrier = carrier;
        this.source = source;
        this.destination = destination;
        this.cost = cost;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
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

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
