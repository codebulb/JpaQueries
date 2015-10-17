package ch.codebulb.jpaqueries.service;

import ch.codebulb.jpaqueries.model.BaseModel_;
import ch.codebulb.jpaqueries.model.Customer;
import ch.codebulb.jpaqueries.model.Customer_;
import ch.codebulb.jpaqueries.model.Product;
import ch.codebulb.jpaqueries.model.Product_;
import ch.codebulb.jpaqueries.model.Purchase;
import ch.codebulb.jpaqueries.model.Purchase_;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.Tuple;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ListJoin;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

public class ProductServiceWithCriteria extends ProductService {
    CustomerService customerService;
   
    public List<Product> findForPurchaseCustomerPremium() {
        // main query on products
        CriteriaQuery<Product> query = em.getCriteriaBuilder().createQuery(Product.class);
        Root<Product> from = query.from(Product.class);
        
        // subquery on product ids
        Subquery<Long> subQuery = query.subquery(Long.class);
        Root<Customer> subFrom = subQuery.from(Customer.class);
        ListJoin<Customer, Purchase> joinPurchase = subFrom.join(Customer_.purchases);
        ListJoin<Purchase, Product> joinProduct = joinPurchase.join(Purchase_.products);
        // Explicitly add to SELECT clause; otherwise, throws Error Code: 30000 SQLSyntaxErrorException
        subQuery.select(joinProduct.get(Product_.id)).distinct(true);
        subQuery.where(em.getCriteriaBuilder().equal(subFrom.get(Customer_.premium), true));
        
        query.select(from);
        query.where(em.getCriteriaBuilder().in(from.get(Product_.id)).value(subQuery));
        return em.createQuery(query).getResultList();
    }
    
    public Map<Customer, Double> sumPriceByPurchaseCustomer() {
        // Create "tuple" query for use with groupBy; otherwise, throws PersistenceException: Exception [EclipseLink-6051]
        CriteriaQuery<Tuple> query = em.getCriteriaBuilder().createTupleQuery();
        Root<Customer> from = query.from(Customer.class);
        ListJoin<Customer, Purchase> joinPurchase = from.join(Customer_.purchases);
        ListJoin<Purchase, Product> joinProduct = joinPurchase.join(Purchase_.products);
        query.multiselect(from.get(BaseModel_.id), em.getCriteriaBuilder().sum(joinProduct.get(Product_.price)));
        query.groupBy(from.get(BaseModel_.id));
        List<Tuple> results = em.createQuery(query).getResultList();
        
        Map<Customer, Double> ret = new HashMap<>();
        for (Tuple result : results) {
            Object[] arr = result.toArray();
            ret.put(customerService.findById((Long)arr[0]), ((Double)arr[1]));
        }
        return ret;
    }
}
