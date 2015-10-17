package ch.codebulb.jpaqueries.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedNativeQueries({
    @NamedNativeQuery(name = Purchase.SQL_FIND_BY_CUSTOMER_ID, query = "SELECT PURCHASE.* FROM PURCHASE INNER JOIN CUSTOMER ON PURCHASE.CUSTOMER_ID=CUSTOMER.ID WHERE CUSTOMER.ID = ?1"),
    @NamedNativeQuery(name = Purchase.SQL_COUNT_BY_CUSTOMER_ID, query = "SELECT COUNT(PURCHASE.ID) FROM PURCHASE INNER JOIN CUSTOMER ON PURCHASE.CUSTOMER_ID=CUSTOMER.ID WHERE CUSTOMER.ID = ?1"),
})
@NamedQueries({
    @NamedQuery(name = Purchase.FIND_BY_CUSTOMER_ID, query = "SELECT e FROM Purchase e INNER JOIN e.customer _customer WHERE _customer.id = :id"),
    @NamedQuery(name = Purchase.COUNT_BY_CUSTOMER_ID, query = "SELECT COUNT(e.id) FROM Purchase e INNER JOIN e.customer _customer WHERE _customer.id = :id"),
})
public class Purchase extends BaseModel {
    public static final QPurchase qPurchase = QPurchase.purchase;
    
    public static final String SQL_FIND_BY_CUSTOMER_ID = "SQL.Purchase.findByCustomerId";
    public static final String SQL_COUNT_BY_CUSTOMER_ID = "SQL.Purchase.countByCustomerId";
    
    public static final String FIND_BY_CUSTOMER_ID = "Purchase.findByCustomerId";
    public static final String COUNT_BY_CUSTOMER_ID = "Purchase.countByCustomerId";
    
    @ManyToOne
    @JoinColumn(name="CUSTOMER_ID")
    private Customer customer;
    @Temporal(TemporalType.DATE)
    private Date orderDate = new Date();
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Product> products = new ArrayList<>();
    
    public Purchase addProduct(Product product) {
        products.add(product);
        return this;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
