package ch.codebulb.jpaqueries.model;

import javax.persistence.Entity;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedNativeQueries({
    @NamedNativeQuery(name = Product.SQL_FIND_FOR_PURCHASE_CUSTOMER_PREMIUM, query = "SELECT PRODUCT.* FROM PRODUCT WHERE PRODUCT.ID IN (SELECT DISTINCT PRODUCT.ID FROM PRODUCT INNER JOIN PURCHASE_PRODUCT ON PURCHASE_PRODUCT.PRODUCTS_ID=PRODUCT.ID INNER JOIN PURCHASE ON PURCHASE.ID=PURCHASE_PRODUCT.PURCHASE_ID INNER JOIN CUSTOMER ON PURCHASE.CUSTOMER_ID=CUSTOMER.ID WHERE CUSTOMER.PREMIUM = 1)",
            resultClass = Product.class),
    @NamedNativeQuery(name = Product.SQL_SUM_PRICE_BY_PURCHASE_CUSTOMER, query = "SELECT CUSTOMER.ID, SUM(PRODUCT.PRICE) FROM CUSTOMER INNER JOIN PURCHASE ON PURCHASE.CUSTOMER_ID=CUSTOMER.ID INNER JOIN PURCHASE_PRODUCT ON PURCHASE.ID=PURCHASE_PRODUCT.PURCHASE_ID INNER JOIN PRODUCT ON PURCHASE_PRODUCT.PRODUCTS_ID=PRODUCT.ID GROUP BY CUSTOMER.ID"),
})
@NamedQueries({
    @NamedQuery(name = Product.FIND_FOR_PURCHASE_CUSTOMER_PREMIUM, query = "SELECT e from Product e where e.id IN (SELECT DISTINCT _products.id FROM Customer _customer INNER JOIN _customer.purchases _purchases INNER JOIN _purchases.products _products WHERE _customer.premium = true)"),
    @NamedQuery(name = Product.SUM_PRICE_BY_PURCHASE_CUSTOMER, query = "SELECT _customer.id, SUM(_products.price) FROM Customer _customer INNER JOIN _customer.purchases _purchases INNER JOIN _purchases.products _products GROUP BY _customer.id"),
})
public class Product extends BaseModel {
    public static final QProduct qProduct = QProduct.product;
    
    public static final String SQL_FIND_FOR_PURCHASE_CUSTOMER_PREMIUM = "SQL.Product.findForPurchaseCustomerPremium";
    public static final String SQL_SUM_PRICE_BY_PURCHASE_CUSTOMER = "SQL.Product.sumPriceByPurchaseCustomer";
    
    public static final String FIND_FOR_PURCHASE_CUSTOMER_PREMIUM = "Product.findForPurchaseCustomerPremium";
    public static final String SUM_PRICE_BY_PURCHASE_CUSTOMER = "Product.sumPriceByPurchaseCustomer";
    
    private String name;
    private double price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
