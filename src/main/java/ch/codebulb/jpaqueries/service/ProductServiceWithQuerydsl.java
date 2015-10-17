package ch.codebulb.jpaqueries.service;

import ch.codebulb.jpaqueries.model.Customer;
import static ch.codebulb.jpaqueries.model.Customer.qCustomer;
import ch.codebulb.jpaqueries.model.Product;
import static ch.codebulb.jpaqueries.model.Product.qProduct;
import static ch.codebulb.jpaqueries.model.Purchase.qPurchase;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductServiceWithQuerydsl extends ProductService {
    CustomerService customerService;
   
    public List<Product> findForPurchaseCustomerPremium() {
        return new JPAQueryFactory(em).selectFrom(qProduct)
                .where(qProduct.id.in(JPAExpressions.selectFrom(qCustomer)
                        .innerJoin(qCustomer.purchases, qPurchase)
                        .innerJoin(qPurchase.products, qProduct)
                        .select(qProduct.id)
                        .where(qCustomer.premium.eq(true))
                ))
                .fetch();
    }
    
    public Map<Customer, Double> sumPriceByPurchaseCustomer() {
        List<Tuple> results = new JPAQueryFactory(em).from(qCustomer)
                .innerJoin(qCustomer.purchases, qPurchase)
                .innerJoin(qPurchase.products, qProduct)
                .groupBy(qCustomer.id)
                .select(qCustomer.id, qProduct.price.sum())
                .fetch();
        
        Map<Customer, Double> ret = new HashMap<>();
        for (Tuple result : results) {
            ret.put(customerService.findById(result.get(qCustomer.id)), result.get(qProduct.price.sum()));
        }
        return ret;
    }
}
