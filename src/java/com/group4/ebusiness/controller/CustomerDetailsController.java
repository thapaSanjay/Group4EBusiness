package com.group4.ebusiness.controller;

import com.group4.ebusiness.ejb.CustomerEJB;
import com.group4.ebusiness.entity.Customer;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;

@Named
@RequestScoped
public class CustomerDetailsController {

    @EJB
    private CustomerEJB customerEJB;

    private Long customerId;
    private Customer customer;

    public void loadCustomer() {
        if (customerId != null) {
            customer = customerEJB.findCustomerWithOrders(customerId);
        }
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Customer getCustomer() {
        return customer;
    }
}