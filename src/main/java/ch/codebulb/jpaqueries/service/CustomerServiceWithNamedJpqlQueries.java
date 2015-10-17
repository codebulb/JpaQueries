package ch.codebulb.jpaqueries.service;

import ch.codebulb.jpaqueries.model.Customer;
import java.util.List;

public class CustomerServiceWithNamedJpqlQueries extends CustomerService {
    @Override
    public Customer findById(Long id) {
        return em.createNamedQuery(Customer.FIND_BY_ID, Customer.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public List<Customer> findAll() {
        return em.createNamedQuery(Customer.FIND_ALL, Customer.class)
                .getResultList();
    }

    @Override
    public long countAll() {
        return em.createNamedQuery(Customer.COUNT_ALL, Long.class)
                .getSingleResult();
    }
}
