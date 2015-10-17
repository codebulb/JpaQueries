package ch.codebulb.jpaqueries.service;

import ch.codebulb.jpaqueries.model.Customer;
import ch.codebulb.jpaqueries.model.Customer_;
import java.util.List;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class CustomerServiceWithCriteria extends CustomerService {
    @Override
    public Customer findById(Long id) {
        CriteriaQuery<Customer> query = em.getCriteriaBuilder().createQuery(Customer.class);
        Root<Customer> from = query.from(Customer.class);
        query.where(em.getCriteriaBuilder().equal(from.get(Customer_.id), id));
        return em.createQuery(query).getSingleResult();
    }

    @Override
    public List<Customer> findAll() {
        CriteriaQuery<Customer> query = em.getCriteriaBuilder().createQuery(Customer.class);
        Root<Customer> from = query.from(Customer.class);
        return em.createQuery(query).getResultList();
    }

    @Override
    public long countAll() {
        CriteriaQuery<Long> query = em.getCriteriaBuilder().createQuery(Long.class);
        Root<Customer> from = query.from(Customer.class);
        query.select(em.getCriteriaBuilder().count(from));
        return em.createQuery(query).getSingleResult();
    }
}
