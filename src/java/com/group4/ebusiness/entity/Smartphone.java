package com.group4.ebusiness.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "product_id")
public class Smartphone extends Product {

    private String cellularConnectivity;
    private String location;
    private String simCard;

    public Smartphone() {
    }

    public Smartphone(String brand, String model, double displaySize,
                      double weight, String operatingSystem,
                      String camera, String wifi, int stockQuantity,
                      String cellularConnectivity, String location,
                      String simCard) {

        super(brand, model, displaySize, weight, operatingSystem,
              camera, wifi, stockQuantity);

        this.cellularConnectivity = cellularConnectivity;
        this.location = location;
        this.simCard = simCard;
    }

    public String getCellularConnectivity() {
        return cellularConnectivity;
    }

    public void setCellularConnectivity(String cellularConnectivity) {
        this.cellularConnectivity = cellularConnectivity;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSimCard() {
        return simCard;
    }

    public void setSimCard(String simCard) {
        this.simCard = simCard;
    }
}