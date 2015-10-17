package ch.codebulb.jpaqueries.service;

import ch.codebulb.jpaqueries.model.Customer;
import ch.codebulb.jpaqueries.model.Product;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductServiceWithDynamicNativeQueries extends ProductService {
    CustomerService customerService;
    
    public List<Product> findForPurchaseCustomerPremium() {
        return em.createNativeQuery("SELECT PRODUCT.* FROM PRODUCT\n" +
"WHERE PRODUCT.ID IN (\n" +
"    SELECT DISTINCT PRODUCT.ID FROM PRODUCT\n" +
"    INNER JOIN PURCHASE_PRODUCT\n" +
"    ON PURCHASE_PRODUCT.PRODUCTS_ID=PRODUCT.ID\n" +
"    INNER JOIN PURCHASE\n" +
"    ON PURCHASE.ID=PURCHASE_PRODUCT.PURCHASE_ID\n" +
"    INNER JOIN CUSTOMER\n" +
"    ON PURCHASE.CUSTOMER_ID=CUSTOMER.ID\n" +
"    WHERE CUSTOMER.PREMIUM = 1\n" +
")", Product.class)
                .getResultList();
    }
    
    public Map<Customer, Double> sumPriceByPurchaseCustomer() {
        List results = em.createNativeQuery("SELECT CUSTOMER.ID, SUM(PRODUCT.PRICE) FROM CUSTOMER\n" +
                "INNER JOIN PURCHASE\n" +
                "ON PURCHASE.CUSTOMER_ID=CUSTOMER.ID\n" +
                "INNER JOIN PURCHASE_PRODUCT\n" +
                "ON PURCHASE.ID=PURCHASE_PRODUCT.PURCHASE_ID\n" +
                "INNER JOIN PRODUCT\n" +
                "ON PURCHASE_PRODUCT.PRODUCTS_ID=PRODUCT.ID\n" +
                "GROUP BY CUSTOMER.ID")
                .getResultList();
        Map<Customer, Double> ret = new HashMap<>();
        for (Object result : results) {
            Object[] arr = (Object[]) result;
            ret.put(customerService.findById((Long)arr[0]), ((Double)arr[1]));
        }
        return ret;
    }
}
