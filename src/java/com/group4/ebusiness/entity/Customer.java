package com.group4.ebusiness.entity;

import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

/**
 * JPA entity containing customer contact information.
 * A customer can be associated with multiple orders.
 */
@Entity
@NamedQueries({
    @NamedQuery(
        name = "Customer.findAll",
        query = "SELECT c FROM Customer c"
    ),
    @NamedQuery(
        name = "Customer.searchByKeyword",
        query = "SELECT c FROM Customer c "
              + "WHERE LOWER(c.firstName) LIKE LOWER(:keyword) "
              + "OR LOWER(c.lastName) LIKE LOWER(:keyword) "
              + "OR LOWER(c.email) LIKE LOWER(:keyword)"
    ),
    @NamedQuery(
        name = "Customer.findWithOrders",
        query = "SELECT DISTINCT c FROM Customer c "
              + "LEFT JOIN FETCH c.orders "
              + "WHERE c.id = :id"
    )
})
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;

    // One customer may place multiple orders.
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<CustomerOrder> orders = new ArrayList<>();

    public Customer() {
    }

    public Customer(String firstName, String lastName, String email,
                    String phone, String address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<CustomerOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<CustomerOrder> orders) {
        this.orders = orders;
    }
}