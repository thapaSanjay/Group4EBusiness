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

/**
 * JSF backing bean for order management.
 * Performs presentation-layer validation and delegates
 * order processing to OrderEJB.
 */
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

    /**
    * Validates the selected customer, product and quantity,
    * then creates a new order through OrderEJB.
    */
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

        // Prevent an order from exceeding the currently available product stock.
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

    /**
    * Deletes an order through OrderEJB.
    * OrderEJB also restores the ordered quantity to product stock.
    */
    public String deleteOrder(Long id) {
        orderEJB.deleteOrder(id);
        return "orders?faces-redirect=true";
    }

    /**
    * Retrieves all orders for display in the order table.
    */
    public List<CustomerOrder> getOrders() {
        return orderEJB.findAllOrders();
    }

    /**
    * Retrieves customers used in the order customer-selection menu.
    */
    public List<Customer> getCustomers() {
        return customerEJB.findAllCustomers();
    }

    /**
    * Retrieves products used in the order product-selection menu.
    */
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