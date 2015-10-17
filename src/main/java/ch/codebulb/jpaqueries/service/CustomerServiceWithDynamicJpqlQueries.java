package ch.codebulb.jpaqueries.service;

import ch.codebulb.jpaqueries.model.Customer;
import java.util.List;

public class CustomerServiceWithDynamicJpqlQueries extends CustomerService {
    @Override
    public Customer findById(Long id) {
        return em.createQuery("SELECT e FROM Customer e WHERE e.id = :id", Customer.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    @Override
    public List<Customer> findAll() {
        return em.createQuery("SELECT e FROM Customer e", Customer.class)
                .getResultList();
    }

    @Override
    public long countAll() {
        return em.createQuery("SELECT COUNT(e.id) FROM Customer e", Long.class)
                .getSingleResult();
    }
}
