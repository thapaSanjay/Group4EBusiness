package com.group4.ebusiness.entity;

import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;

/**
 * JPA entity representing an individual customer order.
 * Each order belongs to one customer and references one product.
 */
@Entity
@NamedQueries({
    @NamedQuery(
        name = "CustomerOrder.findAll",
        query = "SELECT o FROM CustomerOrder o"
    )
})
public class CustomerOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int quantity;

    private LocalDateTime orderDate;

    // Customer who placed this order.
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    // Product purchased in this order.
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    public CustomerOrder() {
    }

    public CustomerOrder(int quantity, Customer customer, Product product) {
        this.quantity = quantity;
        this.customer = customer;
        this.product = product;
        this.orderDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}