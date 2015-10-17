package ch.codebulb.jpaqueries.service;

import ch.codebulb.jpaqueries.model.Customer;
import java.util.List;

public class CustomerServiceWithDynamicNativeQueries extends CustomerService {
    @Override
    public Customer findById(Long id) {
        /*
         * Returns an Object[] array unless an explicit return value class is provided.
         * But even if it is provided, you still have to cast the return value because it doesn't build a TypedQuery.
         */
        /*
         * Also note that named parameters are not supported: http://stackoverflow.com/a/3145275/1399395
         * not even in EclipseLink JPA 2: https://wiki.eclipse.org/EclipseLink/UserGuide/JPA/Basic_JPA_Development/Querying/Native#Parameters
         */
        return (Customer) em.createNativeQuery("SELECT * FROM CUSTOMER WHERE ID = ?1", Customer.class)
                .setParameter(1, id)
                .getSingleResult();
    }

    @Override
    public List<Customer> findAll() {
        return em.createNativeQuery("SELECT * FROM CUSTOMER", Customer.class)
                .getResultList();
    }

    @Override
    public long countAll() {
        /*
         * Here, we *must not* specify an explicit return value class.
         * Otherwise, throws PersistenceException: Exception [EclipseLink-6007]: Exception Description: Missing descriptor for [class java.lang.Long].
         * Note that the return value may be an int, depeding on the database implementation: http://stackoverflow.com/a/10834444/1399395
         * Watch out to keep your implementation portable!
         */
        return ((Number) em.createNativeQuery("SELECT COUNT(ID) FROM CUSTOMER")
                .getSingleResult()).longValue();
    }
}
