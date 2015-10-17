package ch.codebulb.jpaqueries.service;

import ch.codebulb.jpaqueries.model.BaseModel_;
import ch.codebulb.jpaqueries.model.Customer;
import ch.codebulb.jpaqueries.model.Purchase;
import ch.codebulb.jpaqueries.model.Purchase_;
import java.util.List;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

public class PurchaseServiceWithCriteria extends PurchaseService {
    public List<Purchase> findByCustomerId(Long id) {
        CriteriaQuery<Purchase> query = em.getCriteriaBuilder().createQuery(Purchase.class);
        Root<Purchase> from = query.from(Purchase.class);
        Join<Purchase, Customer> join = from.join(Purchase_.customer);
        query.where(em.getCriteriaBuilder().equal(join.get(BaseModel_.id), id));
        return em.createQuery(query).getResultList();
    }
    
    public long countByCustomerId(Long id) {
        CriteriaQuery<Long> query = em.getCriteriaBuilder().createQuery(Long.class);
        Root<Purchase> from = query.from(Purchase.class);
        Join<Purchase, Customer> join = from.join(Purchase_.customer);
        query.where(em.getCriteriaBuilder().equal(join.get(BaseModel_.id), id));
        query.select(em.getCriteriaBuilder().count(from));
        return em.createQuery(query).getSingleResult();
    }
}
