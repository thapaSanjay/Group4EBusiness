package com.group4.ebusiness.ejb;

import com.group4.ebusiness.entity.CustomerOrder;
import com.group4.ebusiness.entity.Product;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 * Stateless business component responsible for order processing
 * and product stock management.
 */
@Stateless
public class OrderEJB {

    @PersistenceContext(unitName = "Group4EBusinessPU")
    private EntityManager em;

    /**
    * Creates a new customer order.
    * Validates the requested quantity and decreases product stock
    * before persisting the order.
    */
    public void createOrder(CustomerOrder order) {

        Product product = em.find(
                Product.class,
                order.getProduct().getId()
        );

        if (product == null) {
            throw new IllegalArgumentException("Product not found.");
        }

        // An order must contain at least one item.
        if (order.getQuantity() <= 0) {
            throw new IllegalArgumentException(
                    "Order quantity must be greater than zero."
            );
        }

        // Reject the order when the requested quantity exceeds available stock.
        if (product.getStockQuantity() < order.getQuantity()) {
            throw new IllegalArgumentException(
                    "Not enough stock available."
            );
        }

        // Reduce available stock by the quantity included in the new order.
        product.setStockQuantity(
                product.getStockQuantity() - order.getQuantity()
        );

        order.setProduct(product);

        em.persist(order);
        em.merge(product);
    }

    /**
    * Retrieves an order using its primary key.
    */
    public CustomerOrder findOrderById(Long id) {
        return em.find(CustomerOrder.class, id);
    }

    /**
    * Retrieves all orders using the CustomerOrder.findAll named query.
    */
    public List<CustomerOrder> findAllOrders() {
        return em.createNamedQuery(
                "CustomerOrder.findAll",
                CustomerOrder.class
        ).getResultList();
    }

    /**
    * Deletes an existing order and restores its quantity
    * to the associated product stock.
    */
    public void deleteOrder(Long id) {

        CustomerOrder order = em.find(CustomerOrder.class, id);
        // Return the ordered quantity to stock before deleting the order.
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
    /**
    * Searches for an order by its unique identifier.
    */
    public CustomerOrder searchOrder(Long id) {

        if (id == null) {
            return null;
        }

        return em.find(CustomerOrder.class, id);
    }
}