package ch.codebulb.jpaqueries.service;

import ch.codebulb.jpaqueries.model.Customer;
import ch.codebulb.jpaqueries.model.Product;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductServiceWithDynamicJpqlQueries extends ProductService {
    CustomerService customerService;
    
    public List<Product> findForPurchaseCustomerPremium() {
        return em.createQuery("SELECT e from Product e where e.id IN (SELECT DISTINCT _products.id FROM Customer _customer\n" +
"INNER JOIN _customer.purchases _purchases\n" +
"INNER JOIN _purchases.products _products\n" +
"WHERE _customer.premium = true)", Product.class)
                .getResultList();
    }
    
    public Map<Customer, Double> sumPriceByPurchaseCustomer() {
        List results = em.createQuery("SELECT _customer.id, SUM(_products.price) FROM Customer _customer\n" +
"INNER JOIN _customer.purchases _purchases\n" +
"INNER JOIN _purchases.products _products\n" +
"GROUP BY _customer.id")
                .getResultList();
        Map<Customer, Double> ret = new HashMap<>();
        for (Object result : results) {
            Object[] arr = (Object[]) result;
            ret.put(customerService.findById((Long)arr[0]), ((Double)arr[1]));
        }
        return ret;
    }
}
