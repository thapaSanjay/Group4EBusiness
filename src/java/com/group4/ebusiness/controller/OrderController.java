package com.group4.ebusiness.controller;

import com.group4.ebusiness.ejb.CustomerEJB;
import com.group4.ebusiness.ejb.OrderEJB;
import com.group4.ebusiness.ejb.ProductEJB;
import com.group4.ebusiness.entity.Customer;
import com.group4.ebusiness.entity.CustomerOrder;
import com.group4.ebusiness.entity.Product;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
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
    private Integer quantity;

    public String createOrder() {

        FacesContext context = FacesContext.getCurrentInstance();

        if (customerId == null) {
            context.addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Order failed",
                            "Please select a customer."
                    )
            );
            return null;
        }

        if (productId == null) {
            context.addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Order failed",
                            "Please select a product."
                    )
            );
            return null;
        }

        if (quantity == null || quantity <= 0) {
            context.addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Order failed",
                            "Quantity must be greater than 0."
                    )
            );
            return null;
        }

        Customer customer = customerEJB.findCustomerById(customerId);
        Product product = productEJB.findProductById(productId);

        if (customer == null) {
            context.addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Order failed",
                            "Selected customer was not found."
                    )
            );
            return null;
        }

        if (product == null) {
            context.addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Order failed",
                            "Selected product was not found."
                    )
            );
            return null;
        }

        if (quantity > product.getStockQuantity()) {
            context.addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Order failed. Insufficient stock. Available quantity: "
                                    + product.getStockQuantity(),
                            null
                    )
            );
            return null;
        }

        try {

            CustomerOrder order = new CustomerOrder();
            order.setCustomer(customer);
            order.setProduct(product);
            order.setQuantity(quantity);
            order.setOrderDate(LocalDateTime.now());

            orderEJB.createOrder(order);

            context.addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_INFO,
                            "Order created",
                            "The order was created successfully."
                    )
            );

            return "orders?faces-redirect=true";

        } catch (Exception e) {

            context.addMessage(
                    null,
                    new FacesMessage(
                            FacesMessage.SEVERITY_ERROR,
                            "Order failed",
                            "The order could not be created."
                    )
            );

            return null;
        }
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}