package ch.codebulb.jpaqueries.service;

import static ch.codebulb.jpaqueries.model.Customer.qCustomer;
import ch.codebulb.jpaqueries.model.Purchase;
import static ch.codebulb.jpaqueries.model.Purchase.qPurchase;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;

public class PurchaseServiceWithQuerydsl extends PurchaseService {
    public List<Purchase> findByCustomerId(Long id) {
        return new JPAQueryFactory(em).selectFrom(qPurchase)
                .innerJoin(qPurchase.customer, qCustomer)
                .where(qCustomer.id.eq(id))
                .fetch();
    }
    
    public long countByCustomerId(Long id) {
        return new JPAQueryFactory(em).selectFrom(qPurchase)
                .innerJoin(qPurchase.customer, qCustomer)
                .where(qCustomer.id.eq(id))
                .fetchCount();
    }
}
