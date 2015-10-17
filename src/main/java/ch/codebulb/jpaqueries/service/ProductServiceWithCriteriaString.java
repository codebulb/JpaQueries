package ch.codebulb.jpaqueries.service;

import ch.codebulb.jpaqueries.model.Customer;
import ch.codebulb.jpaqueries.model.Product;
import ch.codebulb.jpaqueries.model.Purchase;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

public class ProductServiceWithCriteriaString extends ProductService {
    CustomerService customerService;
   
    public List<Product> findForPurchaseCustomerPremium() {
        // main query on products
        CriteriaQuery<Product> query = em.getCriteriaBuilder().createQuery(Product.class);
        Root<Product> from = query.from(Product.class);
        
        // subquery on product ids
        Subquery<Long> subQuery = query.subquery(Long.class);
        Root<Customer> subFrom = subQuery.from(Customer.class);
        Join<Customer, Purchase> joinPurchase = subFrom.join("purchases");
        Join<Purchase, Product> joinProduct = joinPurchase.join("products");
        // Explicitly add to SELECT clause; otherwise, throws Error Code: 30000 SQLSyntaxErrorException
        subQuery.select(joinProduct.get("id").as(Long.class)).distinct(true);
        subQuery.where(em.getCriteriaBuilder().equal(subFrom.get("premium"), true));
        
        query.select(from);
        query.where(em.getCriteriaBuilder().in(from.get("id")).value(subQuery));
        return em.createQuery(query).getResultList();
    }
    
    public Map<Customer, Double> sumPriceByPurchaseCustomer() {
        // Create "tuple" query for use with groupBy; otherwise, throws PersistenceException: Exception [EclipseLink-6051]
        CriteriaQuery<Tuple> query = em.getCriteriaBuilder().createTupleQuery();
        Root<Customer> from = query.from(Customer.class);
        Join<Customer, Purchase> joinPurchase = from.join("purchases");
        Join<Purchase, Product> joinProduct = joinPurchase.join("products");
        query.multiselect(from.get("id"), em.getCriteriaBuilder().sum(joinProduct.get("price").as(Double.class)));
        query.groupBy(from.get("id"));
        List<Tuple> results = em.createQuery(query).getResultList();
        
        Map<Customer, Double> ret = new HashMap<>();
        for (Tuple result : results) {
            Object[] arr = result.toArray();
            ret.put(customerService.findById((Long)arr[0]), ((Double)arr[1]));
        }
        return ret;
    }
}
