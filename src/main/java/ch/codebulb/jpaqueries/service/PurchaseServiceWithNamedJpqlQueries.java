package ch.codebulb.jpaqueries.service;

import ch.codebulb.jpaqueries.model.Purchase;
import java.util.List;

public class PurchaseServiceWithNamedJpqlQueries extends PurchaseService {
    public List<Purchase> findByCustomerId(Long id) {
        return em.createNamedQuery(Purchase.FIND_BY_CUSTOMER_ID, Purchase.class)
                .setParameter("id", id)
                .getResultList();
    }
    
    public long countByCustomerId(Long id) {
        return em.createNamedQuery(Purchase.COUNT_BY_CUSTOMER_ID, Long.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
