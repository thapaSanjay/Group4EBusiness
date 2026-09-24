package com.group4.ebusiness.ejb;

import com.group4.ebusiness.entity.Customer;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 * Stateless business component that manages customer persistence,
 * retrieval, searching and customer-order relationships.
 */
@Stateless
public class CustomerEJB {

    @PersistenceContext(unitName = "Group4EBusinessPU")
    private EntityManager em;

    /**
    * Persists a new customer in the database.
    */
    public void createCustomer(Customer customer) {
        em.persist(customer);
    }

    /**
    * Finds a customer by primary key.
    */
    public Customer findCustomerById(Long id) {
        return em.find(Customer.class, id);
    }

    /**
    * Retrieves all customers using the Customer.findAll named query.
    */
    public List<Customer> findAllCustomers() {
        return em.createNamedQuery(
                "Customer.findAll",
                Customer.class
        ).getResultList();
    }

    /**
    * Updates an existing customer record.
    */
    public Customer updateCustomer(Customer customer) {
        return em.merge(customer);
    }

    /**
    * Deletes the selected customer if it exists.
    */
    public void deleteCustomer(Long id) {
        Customer customer = em.find(Customer.class, id);

        if (customer != null) {
            em.remove(customer);
        }
    }
    
    /**
    * Searches customers by first name, last name or email.
    */
    public List<Customer> searchCustomers(String keyword) {

        return em.createNamedQuery(
                "Customer.searchByKeyword",
                Customer.class
        )
        .setParameter("keyword", "%" + keyword + "%")
        .getResultList();
    }
    
    /**
    * Retrieves a customer and fetches the associated orders
    * for the Customer Details page.
    */
    public Customer findCustomerWithOrders(Long id) {
        return em.createNamedQuery(
                "Customer.findWithOrders",
                Customer.class
        )
        .setParameter("id", id)
        .getSingleResult();
    }
}