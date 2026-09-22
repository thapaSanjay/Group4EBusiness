package com.group4.ebusiness.controller;

import com.group4.ebusiness.ejb.CustomerEJB;
import com.group4.ebusiness.entity.Customer;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class CustomerController {

    @EJB
    private CustomerEJB customerEJB;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;

    public String createCustomer() {

        Customer customer = new Customer(
                firstName,
                lastName,
                email,
                phone,
                address
        );

        customerEJB.createCustomer(customer);

        return "customers?faces-redirect=true";
    }

    public List<Customer> getCustomers() {
        return customerEJB.findAllCustomers();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}