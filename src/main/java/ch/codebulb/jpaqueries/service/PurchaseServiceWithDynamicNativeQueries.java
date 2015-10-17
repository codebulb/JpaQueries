package ch.codebulb.jpaqueries.service;

import ch.codebulb.jpaqueries.model.Purchase;
import java.util.List;

public class PurchaseServiceWithDynamicNativeQueries extends PurchaseService {
    public List<Purchase> findByCustomerId(Long id) {
        return em.createNativeQuery("SELECT PURCHASE.* FROM PURCHASE\n" +
"INNER JOIN CUSTOMER\n" +
"ON PURCHASE.CUSTOMER_ID=CUSTOMER.ID\n" +
"WHERE CUSTOMER.ID = ?1", Purchase.class)
                .setParameter(1, id)
                .getResultList();
    }
    
    public long countByCustomerId(Long id) {
        return ((Number) em.createNativeQuery("SELECT COUNT(PURCHASE.ID) FROM PURCHASE\n" +
"INNER JOIN CUSTOMER\n" +
"ON PURCHASE.CUSTOMER_ID=CUSTOMER.ID\n" +
"WHERE CUSTOMER.ID = ?1")
                .setParameter(1, id)
                .getSingleResult()).longValue();
    }
}
