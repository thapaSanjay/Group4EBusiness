package com.group4.ebusiness.entity;

import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
    @NamedQuery(
        name = "Product.findAll",
        query = "SELECT p FROM Product p"
    ),
    @NamedQuery(
        name = "Product.searchByKeyword",
        query = "SELECT p FROM Product p "
              + "WHERE LOWER(p.brand) LIKE LOWER(:keyword) "
              + "OR LOWER(p.model) LIKE LOWER(:keyword)"
    )
})
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String brand;
    private String model;
    private double displaySize;
    private double weight;
    private String operatingSystem;
    private String camera;
    private String wifi;
    private int stockQuantity;

    public Product() {
    }

    public Product(String brand, String model, double displaySize,
                   double weight, String operatingSystem,
                   String camera, String wifi, int stockQuantity) {
        this.brand = brand;
        this.model = model;
        this.displaySize = displaySize;
        this.weight = weight;
        this.operatingSystem = operatingSystem;
        this.camera = camera;
        this.wifi = wifi;
        this.stockQuantity = stockQuantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getDisplaySize() {
        return displaySize;
    }

    public void setDisplaySize(double displaySize) {
        this.displaySize = displaySize;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getWifi() {
        return wifi;
    }

    public void setWifi(String wifi) {
        this.wifi = wifi;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
}