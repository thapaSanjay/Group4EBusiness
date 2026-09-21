package com.group4.ebusiness.ejb;

import com.group4.ebusiness.entity.CustomerOrder;
import com.group4.ebusiness.entity.Product;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class OrderEJB {

    @PersistenceContext(unitName = "Group4EBusinessPU")
    private EntityManager em;

    public void createOrder(CustomerOrder order) {

        Product product = em.find(
                Product.class,
                order.getProduct().getId()
        );

        if (product == null) {
            throw new IllegalArgumentException("Product not found.");
        }

        if (order.getQuantity() <= 0) {
            throw new IllegalArgumentException(
                    "Order quantity must be greater than zero."
            );
        }

        if (product.getStockQuantity() < order.getQuantity()) {
            throw new IllegalArgumentException(
                    "Not enough stock available."
            );
        }

        product.setStockQuantity(
                product.getStockQuantity() - order.getQuantity()
        );

        order.setProduct(product);

        em.persist(order);
        em.merge(product);
    }

    public CustomerOrder findOrderById(Long id) {
        return em.find(CustomerOrder.class, id);
    }

    public List<CustomerOrder> findAllOrders() {
        return em.createQuery(
                "SELECT o FROM CustomerOrder o",
                CustomerOrder.class
        ).getResultList();
    }

    public void deleteOrder(Long id) {

        CustomerOrder order = em.find(CustomerOrder.class, id);

        if (order != null) {

            Product product = order.getProduct();

            product.setStockQuantity(
                    product.getStockQuantity()
                    + order.getQuantity()
            );

            em.merge(product);
            em.remove(order);
        }
    }
}