package com.group4.ebusiness.ejb;

import com.group4.ebusiness.entity.Product;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

/**
 * Stateless business component for product persistence,
 * retrieval, updates, deletion and product searching.
 */
@Stateless
public class ProductEJB {

    @PersistenceContext(unitName = "Group4EBusinessPU")
    private EntityManager em;

    /**
    * Persists a new laptop or smartphone.
    */
    public void createProduct(Product product) {
        em.persist(product);
    }

    /**
    * Finds a product using its primary key.
    */
    public Product findProductById(Long id) {
        return em.find(Product.class, id);
    }

    /**
    * Retrieves all products using the Product.findAll named query.
    */
    public List<Product> findAllProducts() {
        return em.createNamedQuery(
                "Product.findAll",
                Product.class
        ).getResultList();
    }

    /**
    * Updates an existing product record.
    */
    public Product updateProduct(Product product) {
        return em.merge(product);
    }

    /**
    * Deletes an existing product if it is found.
    */
    public void deleteProduct(Long id) {
        Product product = em.find(Product.class, id);

        if (product != null) {
            em.remove(product);
        }
    }
    
    /**
    * Searches products by brand or model.
    */
    public List<Product> searchProducts(String keyword) {

        return em.createNamedQuery(
                "Product.searchByKeyword",
                Product.class
        )
        .setParameter("keyword", "%" + keyword + "%")
        .getResultList();
    }
}