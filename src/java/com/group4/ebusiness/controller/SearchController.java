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
import java.util.List;

@Named
@RequestScoped
public class SearchController {

    @EJB
    private ProductEJB productEJB;

    @EJB
    private CustomerEJB customerEJB;

    @EJB
    private OrderEJB orderEJB;

    private String productKeyword;
    private String customerKeyword;
    private Long orderId;

    private List<Product> productResults;
    private List<Customer> customerResults;
    private CustomerOrder orderResult;

    public void searchProducts() {
        productResults = productEJB.searchProducts(productKeyword);
    }

    public void searchCustomers() {
        customerResults = customerEJB.searchCustomers(customerKeyword);
    }

    public void searchOrder() {
        orderResult = orderEJB.searchOrder(orderId);
    }

    public String getProductKeyword() {
        return productKeyword;
    }

    public void setProductKeyword(String productKeyword) {
        this.productKeyword = productKeyword;
    }

    public String getCustomerKeyword() {
        return customerKeyword;
    }

    public void setCustomerKeyword(String customerKeyword) {
        this.customerKeyword = customerKeyword;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List<Product> getProductResults() {
        return productResults;
    }

    public List<Customer> getCustomerResults() {
        return customerResults;
    }

    public CustomerOrder getOrderResult() {
        return orderResult;
    }
}