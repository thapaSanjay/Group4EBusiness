package com.group4.ebusiness.ejb;

import com.group4.ebusiness.entity.Product;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Stateless
public class ProductEJB {

    @PersistenceContext(unitName = "Group4EBusinessPU")
    private EntityManager em;

    public void createProduct(Product product) {
        em.persist(product);
    }

    public Product findProductById(Long id) {
        return em.find(Product.class, id);
    }

    public List<Product> findAllProducts() {
        return em.createQuery(
                "SELECT p FROM Product p",
                Product.class
        ).getResultList();
    }

    public Product updateProduct(Product product) {
        return em.merge(product);
    }

    public void deleteProduct(Long id) {
        Product product = em.find(Product.class, id);

        if (product != null) {
            em.remove(product);
        }
    }
    
    public List<Product> searchProducts(String keyword) {

        return em.createQuery(
                "SELECT p FROM Product p "
                + "WHERE LOWER(p.brand) LIKE LOWER(:keyword) "
                + "OR LOWER(p.model) LIKE LOWER(:keyword)",
                Product.class
        )
        .setParameter("keyword", "%" + keyword + "%")
        .getResultList();
    }
}