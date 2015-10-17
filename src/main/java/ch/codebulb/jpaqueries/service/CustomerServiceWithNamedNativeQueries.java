package ch.codebulb.jpaqueries.service;

import ch.codebulb.jpaqueries.model.Customer;
import java.util.List;

public class CustomerServiceWithNamedNativeQueries extends CustomerService {
    @Override
    public Customer findById(Long id) {
        return em.createNamedQuery(Customer.SQL_FIND_BY_ID, Customer.class)
                .setParameter(1, id)
                .getSingleResult();
    }

    @Override
    public List<Customer> findAll() {
        return em.createNamedQuery(Customer.SQL_FIND_ALL, Customer.class)
                .getResultList();
    }

    @Override
    public long countAll() {
        return ((Number)em.createNamedQuery(Customer.SQL_COUNT_ALL, Long.class)
                .getSingleResult()).longValue();
    }
}
