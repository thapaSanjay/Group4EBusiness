package com.group4.ebusiness.controller;

import com.group4.ebusiness.ejb.ProductEJB;
import com.group4.ebusiness.entity.Laptop;
import com.group4.ebusiness.entity.Product;
import com.group4.ebusiness.entity.Smartphone;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class ProductController {

    @EJB
    private ProductEJB productEJB;

    private String productType;

    private String brand;
    private String model;
    private double displaySize;
    private double weight;
    private String operatingSystem;
    private String camera;
    private String wifi;
    private int stockQuantity;

    // Laptop fields
    private String networkInterface;
    private String hardDrive;
    private String opticalDrive;

    // Smartphone fields
    private String cellularConnectivity;
    private String location;
    private String simCard;

    public String createProduct() {

        if ("Laptop".equals(productType)) {

            Laptop laptop = new Laptop(
                    brand,
                    model,
                    displaySize,
                    weight,
                    operatingSystem,
                    camera,
                    wifi,
                    stockQuantity,
                    networkInterface,
                    hardDrive,
                    opticalDrive
            );

            productEJB.createProduct(laptop);

        } else if ("Smartphone".equals(productType)) {

            Smartphone smartphone = new Smartphone(
                    brand,
                    model,
                    displaySize,
                    weight,
                    operatingSystem,
                    camera,
                    wifi,
                    stockQuantity,
                    cellularConnectivity,
                    location,
                    simCard
            );

            productEJB.createProduct(smartphone);
        }

        return "products?faces-redirect=true";
    }

    public List<Product> getProducts() {
        return productEJB.findAllProducts();
    }

    // Getters and setters

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
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

    public String getNetworkInterface() {
        return networkInterface;
    }

    public void setNetworkInterface(String networkInterface) {
        this.networkInterface = networkInterface;
    }

    public String getHardDrive() {
        return hardDrive;
    }

    public void setHardDrive(String hardDrive) {
        this.hardDrive = hardDrive;
    }

    public String getOpticalDrive() {
        return opticalDrive;
    }

    public void setOpticalDrive(String opticalDrive) {
        this.opticalDrive = opticalDrive;
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