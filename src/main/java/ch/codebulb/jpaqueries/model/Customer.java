package ch.codebulb.jpaqueries.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedNativeQueries({
    @NamedNativeQuery(name = Customer.SQL_FIND_BY_ID, query = "SELECT * FROM CUSTOMER WHERE ID = ?1", resultClass = Customer.class),
    @NamedNativeQuery(name = Customer.SQL_FIND_ALL, query = "SELECT * FROM CUSTOMER"),
    @NamedNativeQuery(name = Customer.SQL_COUNT_ALL, query = "SELECT COUNT(ID) FROM CUSTOMER"),
})
@NamedQueries({
    @NamedQuery(name = Customer.FIND_BY_ID, query = "SELECT e FROM Customer e WHERE e.id = :id"),
    @NamedQuery(name = Customer.FIND_ALL, query = "SELECT e FROM Customer e"),
    @NamedQuery(name = Customer.COUNT_ALL, query = "SELECT COUNT(e.id) FROM Customer e"),
})
public class Customer extends BaseModel {
    public static final QCustomer qCustomer = QCustomer.customer;
    
    public static final String SQL_FIND_BY_ID = "SQL.Customer.findbyId";
    public static final String SQL_FIND_ALL = "SQL.Customer.findAll";
    public static final String SQL_COUNT_ALL = "SQL.Customer.countAll";
    
    public static final String FIND_BY_ID = "Customer.findbyId";
    public static final String FIND_ALL = "Customer.findAll";
    public static final String COUNT_ALL = "Customer.countAll";
    
    private String name;
    @OneToMany(mappedBy = "customer", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private List<Purchase> purchases = new ArrayList<>();
    private boolean premium;
    
    public Customer addPurchase(Purchase purchase) {
        purchases.add(purchase);
        // explicitly add the reverse relationship
        purchase.setCustomer(this);
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }
}
