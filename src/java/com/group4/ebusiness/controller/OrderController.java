package com.group4.ebusiness.controller;

import com.group4.ebusiness.ejb.CustomerEJB;
import com.group4.ebusiness.ejb.OrderEJB;
import com.group4.ebusiness.ejb.ProductEJB;
import com.group4.ebusiness.entity.Customer;
import com.group4.ebusiness.entity.CustomerOrder;
import com.group4.ebusiness.entity.Product;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.time.LocalDateTime;
import java.util.List;

@Named
@RequestScoped
public class OrderController {

    @EJB
    private OrderEJB orderEJB;

    @EJB
    private CustomerEJB customerEJB;

    @EJB
    private ProductEJB productEJB;

    private Long customerId;
    private Long productId;
    private int quantity;

    public String createOrder() {

        Customer customer = customerEJB.findCustomerById(customerId);
        Product product = productEJB.findProductById(productId);

        if (customer == null || product == null) {
            return null;
        }

        CustomerOrder order = new CustomerOrder();
        order.setCustomer(customer);
        order.setProduct(product);
        order.setQuantity(quantity);
        order.setOrderDate(LocalDateTime.now());

        orderEJB.createOrder(order);

        return "orders?faces-redirect=true";
    }

    public String deleteOrder(Long id) {
        orderEJB.deleteOrder(id);
        return "orders?faces-redirect=true";
    }

    public List<CustomerOrder> getOrders() {
        return orderEJB.findAllOrders();
    }

    public List<Customer> getCustomers() {
        return customerEJB.findAllCustomers();
    }

    public List<Product> getProducts() {
        return productEJB.findAllProducts();
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}