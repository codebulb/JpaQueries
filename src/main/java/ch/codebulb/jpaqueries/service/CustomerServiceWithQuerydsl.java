package ch.codebulb.jpaqueries.service;

import ch.codebulb.jpaqueries.model.Customer;
import static ch.codebulb.jpaqueries.model.Customer.qCustomer;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;

public class CustomerServiceWithQuerydsl extends CustomerService {
    @Override
    public Customer findById(Long id) {
        return new JPAQueryFactory(em).selectFrom(qCustomer)
                .where(qCustomer.id.eq(id))
                .fetchOne();
    }

    @Override
    public List<Customer> findAll() {
        return new JPAQueryFactory(em).selectFrom(qCustomer)
                .fetch();
    }

    @Override
    public long countAll() {
        return new JPAQueryFactory(em).selectFrom(qCustomer)
                .fetchCount();
                
    }
}
