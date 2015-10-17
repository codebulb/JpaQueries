package ch.codebulb.jpaqueries.service;

import ch.codebulb.jpaqueries.model.Purchase;
import java.util.List;

public class PurchaseServiceWithNamedNativeQueries extends PurchaseService {
    public List<Purchase> findByCustomerId(Long id) {
        return em.createNamedQuery(Purchase.SQL_FIND_BY_CUSTOMER_ID, Purchase.class)
                .setParameter(1, id)
                .getResultList();
    }
    
    public long countByCustomerId(Long id) {
        return ((Number)em.createNamedQuery(Purchase.SQL_COUNT_BY_CUSTOMER_ID, Long.class)
                .setParameter(1, id)
                .getSingleResult()).longValue();
    }
}
