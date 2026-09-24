package com.group4.ebusiness.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

/**
 * JPA entity representing a laptop product.
 * Adds laptop-specific properties to the common Product fields.
 */
@Entity
@PrimaryKeyJoinColumn(name = "product_id")
public class Laptop extends Product {

    private String networkInterface;
    private String hardDrive;
    private String opticalDrive;

    //No args constructor
    public Laptop() {
    }

    /**
    * Creates a laptop using common product properties
    * and laptop-specific hardware information.
    */
    public Laptop(String brand, String model, double displaySize,
                  double weight, String operatingSystem,
                  String camera, String wifi, int stockQuantity,
                  String networkInterface, String hardDrive,
                  String opticalDrive) {

        super(brand, model, displaySize, weight, operatingSystem,
              camera, wifi, stockQuantity);

        this.networkInterface = networkInterface;
        this.hardDrive = hardDrive;
        this.opticalDrive = opticalDrive;
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
}