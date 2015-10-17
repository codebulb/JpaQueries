package ch.codebulb.jpaqueries.service;

import ch.codebulb.jpaqueries.model.Customer;
import ch.codebulb.jpaqueries.model.Purchase;
import java.util.List;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

public class PurchaseServiceWithCriteriaString extends PurchaseService {
    public List<Purchase> findByCustomerId(Long id) {
        CriteriaQuery<Purchase> query = em.getCriteriaBuilder().createQuery(Purchase.class);
        Root<Purchase> from = query.from(Purchase.class);
        Join<Purchase, Customer> join = from.join("customer");
        query.where(em.getCriteriaBuilder().equal(join.get("id"), id));
        return em.createQuery(query).getResultList();
    }
    
    public long countByCustomerId(Long id) {
        CriteriaQuery<Long> query = em.getCriteriaBuilder().createQuery(Long.class);
        Root<Purchase> from = query.from(Purchase.class);
        Join<Purchase, Customer> join = from.join("customer");
        query.where(em.getCriteriaBuilder().equal(join.get("id"), id));
        query.select(em.getCriteriaBuilder().count(from));
        return em.createQuery(query).getSingleResult();
    }
}
