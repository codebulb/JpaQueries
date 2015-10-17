package ch.codebulb.jpaqueries.service;

import ch.codebulb.jpaqueries.model.Customer;
import ch.codebulb.jpaqueries.model.Purchase;
import java.util.ArrayList;
import java.util.List;

public class PurchaseServiceWithEntityManager extends PurchaseService {
    public List<Purchase> findByCustomerId(Long id) {
        Customer customer = em.find(Customer.class, id);
        if (customer == null) {
            return new ArrayList<>();
        }
        
        return customer.getPurchases();
    }
    
    public long countByCustomerId(Long id) {
        Customer customer = em.find(Customer.class, id);
        if (customer == null) {
            return 0;
        }
        
        return customer.getPurchases().size();
    }
}
