package ch.codebulb.jpaqueries.service;

import ch.codebulb.jpaqueries.model.Purchase;
import java.util.List;

public class PurchaseServiceWithDynamicJpqlQueries extends PurchaseService {
    public List<Purchase> findByCustomerId(Long id) {
        return em.createQuery("SELECT e FROM Purchase e\n" +
"INNER JOIN e.customer _customer\n" +
"WHERE _customer.id = :id", Purchase.class)
                .setParameter("id", id)
                .getResultList();
    }
    
    public long countByCustomerId(Long id) {
        return em.createQuery("SELECT COUNT(e.id) FROM Purchase e\n" +
"INNER JOIN e.customer _customer\n" +
"WHERE _customer.id = :id", Long.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
