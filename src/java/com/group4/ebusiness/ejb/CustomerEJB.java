package com.group4.ebusiness.ejb;

import com.group4.ebusiness.entity.Customer;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class CustomerEJB {

    @PersistenceContext(unitName = "Group4EBusinessPU")
    private EntityManager em;

    public void createCustomer(Customer customer) {
        em.persist(customer);
    }

    public Customer findCustomerById(Long id) {
        return em.find(Customer.class, id);
    }

    public List<Customer> findAllCustomers() {
        return em.createNamedQuery(
                "Customer.findAll",
                Customer.class
        ).getResultList();
    }

    public Customer updateCustomer(Customer customer) {
        return em.merge(customer);
    }

    public void deleteCustomer(Long id) {
        Customer customer = em.find(Customer.class, id);

        if (customer != null) {
            em.remove(customer);
        }
    }
    
    public List<Customer> searchCustomers(String keyword) {

        return em.createNamedQuery(
                "Customer.searchByKeyword",
                Customer.class
        )
        .setParameter("keyword", "%" + keyword + "%")
        .getResultList();
    }
    
    public Customer findCustomerWithOrders(Long id) {
        return em.createNamedQuery(
                "Customer.findWithOrders",
                Customer.class
        )
        .setParameter("id", id)
        .getSingleResult();
    }
}