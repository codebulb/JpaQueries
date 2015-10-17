package ch.codebulb.jpaqueries.service;

import ch.codebulb.jpaqueries.model.Customer;
import ch.codebulb.jpaqueries.model.Product;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductServiceWithNamedJpqlQueries extends ProductService {
    CustomerService customerService;
    
    public List<Product> findForPurchaseCustomerPremium() {
        return em.createNamedQuery(Product.FIND_FOR_PURCHASE_CUSTOMER_PREMIUM, Product.class)
                .getResultList();
    }
    
    public Map<Customer, Double> sumPriceByPurchaseCustomer() {
        List results = em.createNamedQuery(Product.SUM_PRICE_BY_PURCHASE_CUSTOMER)
                .getResultList();
        Map<Customer, Double> ret = new HashMap<>();
        for (Object result : results) {
            Object[] arr = (Object[]) result;
            ret.put(customerService.findById((Long)arr[0]), ((Double)arr[1]));
        }
        return ret;
    }
}
