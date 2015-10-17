package ch.codebulb.jpaqueries.service;

import ch.codebulb.jpaqueries.model.Customer;
import ch.codebulb.jpaqueries.model.Product;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

public class ServiceTest {
    private static final String TEST_CUSTOMER_NAME = "Tim";
    private static final String TEST_CUSTOMER_NAME_UPDATED = "Tom";
    
    private final CustomerService customerService = new CustomerService();
    private final CustomerServiceWithDynamicNativeQueries customerServiceWithDynamicNativeQueries = new CustomerServiceWithDynamicNativeQueries();
    private final CustomerServiceWithDynamicJpqlQueries customerServiceWithDynamicJpqlQueries = new CustomerServiceWithDynamicJpqlQueries();
    private final CustomerServiceWithNamedNativeQueries customerServiceWithNamedNativeQueries = new CustomerServiceWithNamedNativeQueries();
    private final CustomerServiceWithNamedJpqlQueries customerServiceWithNamedJpqlQueries = new CustomerServiceWithNamedJpqlQueries();
    private final CustomerServiceWithCriteriaString customerServiceWithCriteriaString = new CustomerServiceWithCriteriaString();
    private final CustomerServiceWithCriteria customerServiceWithCriteria = new CustomerServiceWithCriteria();
    private final CustomerServiceWithQuerydsl customerServiceWithQuerydsl = new CustomerServiceWithQuerydsl();
    
    private final PurchaseService purchaseService = new PurchaseService();
    private final PurchaseServiceWithEntityManager purchaseServiceWithEntityManager = new PurchaseServiceWithEntityManager();
    private final PurchaseServiceWithDynamicNativeQueries purchaseServiceWithDynamicNativeQueries = new PurchaseServiceWithDynamicNativeQueries();
    private final PurchaseServiceWithDynamicJpqlQueries purchaseServiceWithDynamicJpqlQueries = new PurchaseServiceWithDynamicJpqlQueries();
    private final PurchaseServiceWithNamedNativeQueries purchaseServiceWithNamedNativeQueries = new PurchaseServiceWithNamedNativeQueries();
    private final PurchaseServiceWithNamedJpqlQueries purchaseServiceWithNamedJpqlQueries = new PurchaseServiceWithNamedJpqlQueries();
    private final PurchaseServiceWithCriteriaString purchaseServiceWithCriteriaString = new PurchaseServiceWithCriteriaString();
    private final PurchaseServiceWithCriteria purchaseServiceWithCriteria = new PurchaseServiceWithCriteria();
    private final PurchaseServiceWithQuerydsl purchaseServiceWithQuerydsl = new PurchaseServiceWithQuerydsl();
    
    private final ProductService productService = new ProductService();
    private final ProductServiceWithDynamicNativeQueries productServiceWithDynamicNativeQueries = new ProductServiceWithDynamicNativeQueries();
    private final ProductServiceWithDynamicJpqlQueries productServiceWithDynamicJpqlQueries = new ProductServiceWithDynamicJpqlQueries();
    private final ProductServiceWithNamedNativeQueries productServiceWithNamedNativeQueries = new ProductServiceWithNamedNativeQueries();
    private final ProductServiceWithNamedJpqlQueries productServiceWithNamedJpqlQueries = new ProductServiceWithNamedJpqlQueries();
    private final ProductServiceWithCriteriaString productServiceWithCriteriaString = new ProductServiceWithCriteriaString();
    private final ProductServiceWithCriteria productServiceWithCriteria = new ProductServiceWithCriteria();
    private final ProductServiceWithQuerydsl productServiceWithQuerydsl = new ProductServiceWithQuerydsl();
    
    private EntityTransaction transaction;
    
    @Before
    public void init() {
        EntityManager em = Persistence.createEntityManagerFactory("ch.codebulb.jpaqueriesPU").createEntityManager();
        
        customerService.em = em;
        customerServiceWithDynamicNativeQueries.em = em;
        customerServiceWithDynamicJpqlQueries.em = em;
        customerServiceWithNamedNativeQueries.em = em;
        customerServiceWithNamedJpqlQueries.em = em;
        customerServiceWithCriteriaString.em = em;
        customerServiceWithCriteria.em = em;
        customerServiceWithQuerydsl.em = em;
        
        purchaseService.em = em;
        purchaseServiceWithEntityManager.em = em;
        purchaseServiceWithDynamicNativeQueries.em = em;
        purchaseServiceWithDynamicJpqlQueries.em = em;
        purchaseServiceWithNamedNativeQueries.em = em;
        purchaseServiceWithNamedJpqlQueries.em = em;
        purchaseServiceWithCriteriaString.em = em;
        purchaseServiceWithCriteria.em = em;
        purchaseServiceWithQuerydsl.em = em;
        
        productService.em = em;
        productServiceWithDynamicNativeQueries.em = em;
        productServiceWithDynamicJpqlQueries.em = em;
        productServiceWithNamedNativeQueries.em = em;
        productServiceWithNamedJpqlQueries.em = em;
        productServiceWithCriteriaString.em = em;
        productServiceWithCriteria.em = em;
        productServiceWithQuerydsl.em = em;
        
        productServiceWithDynamicNativeQueries.customerService = customerService;
        productServiceWithDynamicJpqlQueries.customerService = customerService;
        productServiceWithNamedNativeQueries.customerService = customerService;
        productServiceWithNamedJpqlQueries.customerService = customerService;
        productServiceWithCriteriaString.customerService = customerService;
        productServiceWithCriteria.customerService = customerService;
        productServiceWithQuerydsl.customerService = customerService;
        
        transaction = em.getTransaction();
        
        transaction.begin();
        
        // Initially, it's empty
        List<Customer> found = customerService.findAll();
        assertEquals(0, found.size());
        assertEquals(0, customerService.countAll());
        
        assertEquals(0, customerServiceWithDynamicNativeQueries.findAll().size());
        assertEquals(0, customerServiceWithDynamicNativeQueries.countAll());
        assertEquals(0, customerServiceWithDynamicJpqlQueries.findAll().size());
        assertEquals(0, customerServiceWithDynamicJpqlQueries.countAll());
        assertEquals(0, customerServiceWithNamedNativeQueries.findAll().size());
        assertEquals(0, customerServiceWithNamedNativeQueries.countAll());
        assertEquals(0, customerServiceWithNamedJpqlQueries.findAll().size());
        assertEquals(0, customerServiceWithNamedJpqlQueries.countAll());
        assertEquals(0, customerServiceWithCriteriaString.findAll().size());
        assertEquals(0, customerServiceWithCriteriaString.countAll());
        assertEquals(0, customerServiceWithCriteria.findAll().size());
        assertEquals(0, customerServiceWithCriteria.countAll());
        assertEquals(0, customerServiceWithQuerydsl.findAll().size());
        assertEquals(0, customerServiceWithQuerydsl.countAll());
    }
    
    @After
    public void destroy() {
        transaction.rollback();
    }
    
    @Test
    public void testCustomerCrud() {
        // create
        Customer entity = customerService.create();
        entity.setName(TEST_CUSTOMER_NAME);
        
        entity = customerService.save(entity);
        
        // find
        assertEquals(1, customerService.findAll().size());
        assertEquals(1, customerService.countAll());
        assertEquals(entity, customerService.findById(entity.getId()));
        assertEquals(TEST_CUSTOMER_NAME, customerService.findById(entity.getId()).getName());
        
        assertEquals(1, customerServiceWithDynamicNativeQueries.findAll().size());
        assertEquals(1, customerServiceWithDynamicNativeQueries.countAll());
        assertEquals(entity, customerServiceWithDynamicNativeQueries.findById(entity.getId()));
        assertEquals(1, customerServiceWithDynamicJpqlQueries.findAll().size());
        assertEquals(1, customerServiceWithDynamicJpqlQueries.countAll());
        assertEquals(entity, customerServiceWithDynamicJpqlQueries.findById(entity.getId()));
        assertEquals(1, customerServiceWithNamedNativeQueries.findAll().size());
        assertEquals(1, customerServiceWithNamedNativeQueries.countAll());
        assertEquals(entity, customerServiceWithNamedNativeQueries.findById(entity.getId()));
        assertEquals(1, customerServiceWithNamedJpqlQueries.findAll().size());
        assertEquals(1, customerServiceWithNamedJpqlQueries.countAll());
        assertEquals(entity, customerServiceWithNamedJpqlQueries.findById(entity.getId()));
        assertEquals(1, customerServiceWithCriteriaString.findAll().size());
        assertEquals(1, customerServiceWithCriteriaString.countAll());
        assertEquals(entity, customerServiceWithCriteriaString.findById(entity.getId()));
        assertEquals(1, customerServiceWithCriteria.findAll().size());
        assertEquals(1, customerServiceWithCriteria.countAll());
        assertEquals(entity, customerServiceWithCriteria.findById(entity.getId()));
        assertEquals(1, customerServiceWithQuerydsl.findAll().size());
        assertEquals(1, customerServiceWithQuerydsl.countAll());
        assertEquals(entity, customerServiceWithQuerydsl.findById(entity.getId()));
        
        // update
        Customer entityUpdated = customerService.findById(entity.getId());
        entityUpdated.setName(TEST_CUSTOMER_NAME_UPDATED);
        
        entityUpdated = customerService.save(entity);
        
        assertEquals(entity.getId(), entityUpdated.getId());
        assertEquals(1, customerService.findAll().size());
        assertEquals(1, customerService.countAll());
        assertEquals(entityUpdated, customerService.findById(entity.getId()));
        assertEquals(TEST_CUSTOMER_NAME_UPDATED, customerService.findById(entityUpdated.getId()).getName());
        
        // delete
        Long entityId = customerService.findAll().get(0).getId();
        customerService.delete(entityId);
        
        assertEquals(0, customerService.findAll().size());
        assertEquals(0, customerService.countAll());
        assertEquals(null, customerService.findById(entityId));
    }
    
    @Test
    public void testPurchasesByCustomerId() {
        // Initially, it's empty
        assertEquals(0, purchaseServiceWithEntityManager.findByCustomerId(1l).size());
        assertEquals(0, purchaseServiceWithEntityManager.countByCustomerId(1l));
        assertEquals(0, purchaseServiceWithDynamicNativeQueries.findByCustomerId(1l).size());
        assertEquals(0, purchaseServiceWithDynamicNativeQueries.countByCustomerId(1l));
        assertEquals(0, purchaseServiceWithDynamicJpqlQueries.findByCustomerId(1l).size());
        assertEquals(0, purchaseServiceWithDynamicJpqlQueries.countByCustomerId(1l));
        assertEquals(0, purchaseServiceWithNamedNativeQueries.findByCustomerId(1l).size());
        assertEquals(0, purchaseServiceWithNamedNativeQueries.countByCustomerId(1l));
        assertEquals(0, purchaseServiceWithNamedJpqlQueries.findByCustomerId(1l).size());
        assertEquals(0, purchaseServiceWithNamedJpqlQueries.countByCustomerId(1l));
        assertEquals(0, purchaseServiceWithCriteriaString.findByCustomerId(1l).size());
        assertEquals(0, purchaseServiceWithCriteriaString.countByCustomerId(1l));
        assertEquals(0, purchaseServiceWithCriteria.findByCustomerId(1l).size());
        assertEquals(0, purchaseServiceWithCriteria.countByCustomerId(1l));
        assertEquals(0, purchaseServiceWithQuerydsl.findByCustomerId(1l).size());
        assertEquals(0, purchaseServiceWithQuerydsl.countByCustomerId(1l));
        
        Customer customer = customerService.create();
        customer.addPurchase(purchaseService.create());
        customer = customerService.save(customer);
        
        assertEquals(1, purchaseServiceWithEntityManager.findByCustomerId(customer.getId()).size());
        assertEquals(1, purchaseServiceWithEntityManager.countByCustomerId(customer.getId()));
        assertEquals(1, purchaseServiceWithDynamicNativeQueries.findByCustomerId(customer.getId()).size());
        assertEquals(1, purchaseServiceWithDynamicNativeQueries.countByCustomerId(customer.getId()));
        assertEquals(1, purchaseServiceWithDynamicJpqlQueries.findByCustomerId(customer.getId()).size());
        assertEquals(1, purchaseServiceWithDynamicJpqlQueries.countByCustomerId(customer.getId()));
        assertEquals(1, purchaseServiceWithNamedNativeQueries.findByCustomerId(customer.getId()).size());
        assertEquals(1, purchaseServiceWithNamedNativeQueries.countByCustomerId(customer.getId()));
        assertEquals(1, purchaseServiceWithNamedJpqlQueries.findByCustomerId(customer.getId()).size());
        assertEquals(1, purchaseServiceWithNamedJpqlQueries.countByCustomerId(customer.getId()));
        assertEquals(1, purchaseServiceWithCriteriaString.findByCustomerId(customer.getId()).size());
        assertEquals(1, purchaseServiceWithCriteriaString.countByCustomerId(customer.getId()));
        assertEquals(1, purchaseServiceWithCriteria.findByCustomerId(customer.getId()).size());
        assertEquals(1, purchaseServiceWithCriteria.countByCustomerId(customer.getId()));
        assertEquals(1, purchaseServiceWithQuerydsl.findByCustomerId(customer.getId()).size());
        assertEquals(1, purchaseServiceWithQuerydsl.countByCustomerId(customer.getId()));
    }
    
    @Test
    public void testFindForPurchaseCustomerPremium() {
        // Initially, it's empty
        assertEquals(0, productServiceWithDynamicNativeQueries.findForPurchaseCustomerPremium().size());
        assertEquals(0, productServiceWithDynamicJpqlQueries.findForPurchaseCustomerPremium().size());
        assertEquals(0, productServiceWithNamedNativeQueries.findForPurchaseCustomerPremium().size());
        assertEquals(0, productServiceWithNamedJpqlQueries.findForPurchaseCustomerPremium().size());
        assertEquals(0, productServiceWithCriteriaString.findForPurchaseCustomerPremium().size());
        assertEquals(0, productServiceWithCriteria.findForPurchaseCustomerPremium().size());
        assertEquals(0, productServiceWithQuerydsl.findForPurchaseCustomerPremium().size());
        
        Customer customerNonPremium = customerService.create();
        assertFalse(customerNonPremium.isPremium());
        Product product1 = productService.create();
        product1.setPrice(5);
        customerNonPremium.addPurchase(purchaseService.create()
            .addProduct(product1)
        );
        customerService.save(customerNonPremium);
        
        Customer customerPremium = customerService.create();
        customerPremium.setPremium(true);
        assertTrue(customerPremium.isPremium());
        Product product2 = productService.create();
        product2.setPrice(10);
        Product product3 = productService.create();
        product3.setPrice(10);
        customerPremium.addPurchase(purchaseService.create()
            .addProduct(product2).addProduct(product3)
        ).addPurchase(purchaseService.create()
            .addProduct(product2)
        );
        customerService.save(customerPremium);
        
        assertListEquals(productServiceWithDynamicNativeQueries.findForPurchaseCustomerPremium(), product2, product3);
        assertListEquals(productServiceWithDynamicJpqlQueries.findForPurchaseCustomerPremium(), product2, product3);
        assertListEquals(productServiceWithNamedNativeQueries.findForPurchaseCustomerPremium(), product2, product3);
        assertListEquals(productServiceWithNamedJpqlQueries.findForPurchaseCustomerPremium(), product2, product3);
        assertListEquals(productServiceWithCriteriaString.findForPurchaseCustomerPremium(), product2, product3);
        assertListEquals(productServiceWithCriteria.findForPurchaseCustomerPremium(), product2, product3);
        assertListEquals(productServiceWithQuerydsl.findForPurchaseCustomerPremium(), product2, product3);
    }
    
    @Test
    public void testSumPriceByPurchaseCustomer() {
        // Initially, it's empty
        assertEquals(0, productServiceWithDynamicNativeQueries.sumPriceByPurchaseCustomer().size());
        assertEquals(0, productServiceWithDynamicJpqlQueries.sumPriceByPurchaseCustomer().size());
        assertEquals(0, productServiceWithNamedNativeQueries.sumPriceByPurchaseCustomer().size());
        assertEquals(0, productServiceWithNamedJpqlQueries.sumPriceByPurchaseCustomer().size());
        assertEquals(0, productServiceWithCriteriaString.sumPriceByPurchaseCustomer().size());
        assertEquals(0, productServiceWithCriteria.sumPriceByPurchaseCustomer().size());
        assertEquals(0, productServiceWithQuerydsl.sumPriceByPurchaseCustomer().size());
        
        Customer customerNonPremium = customerService.create();
        assertFalse(customerNonPremium.isPremium());
        Product product1 = productService.create();
        product1.setPrice(5);
        customerNonPremium.addPurchase(purchaseService.create()
            .addProduct(product1)
        );
        customerService.save(customerNonPremium);
        
        Customer customerPremium = customerService.create();
        customerPremium.setPremium(true);
        assertTrue(customerPremium.isPremium());
        Product product2 = productService.create();
        product2.setPrice(10);
        Product product3 = productService.create();
        product3.setPrice(10);
        customerPremium.addPurchase(purchaseService.create()
            .addProduct(product2).addProduct(product3)
        ).addPurchase(purchaseService.create()
            .addProduct(product2)
        );
        customerService.save(customerPremium);
        
        assertSumEquals(product1.getPrice(), product2.getPrice() + product3.getPrice() + product2.getPrice(), 
                productServiceWithDynamicNativeQueries.sumPriceByPurchaseCustomer(), 
                customerPremium);
        assertSumEquals(product1.getPrice(), product2.getPrice() + product3.getPrice() + product2.getPrice(), 
                productServiceWithDynamicJpqlQueries.sumPriceByPurchaseCustomer(), 
                customerPremium);
        assertSumEquals(product1.getPrice(), product2.getPrice() + product3.getPrice() + product2.getPrice(), 
                productServiceWithNamedNativeQueries.sumPriceByPurchaseCustomer(), 
                customerPremium);
        assertSumEquals(product1.getPrice(), product2.getPrice() + product3.getPrice() + product2.getPrice(), 
                productServiceWithNamedJpqlQueries.sumPriceByPurchaseCustomer(), 
                customerPremium);
        assertSumEquals(product1.getPrice(), product2.getPrice() + product3.getPrice() + product2.getPrice(), 
                productServiceWithCriteriaString.sumPriceByPurchaseCustomer(), 
                customerPremium);
        assertSumEquals(product1.getPrice(), product2.getPrice() + product3.getPrice() + product2.getPrice(), 
                productServiceWithCriteria.sumPriceByPurchaseCustomer(), 
                customerPremium);
        assertSumEquals(product1.getPrice(), product2.getPrice() + product3.getPrice() + product2.getPrice(), 
                productServiceWithQuerydsl.sumPriceByPurchaseCustomer(), 
                customerPremium);
    }
    
    private static <T> void assertListEquals(List<T> actual, T... expected) {
        assertEquals(expected.length, actual.size());
        assertEquals(new HashSet<>(Arrays.asList(expected)), new HashSet<>(actual));
    }
    
    private static void assertSumEquals(double sumNonPremiumExpected, double sumPremiumExpected, Map<Customer, Double> actual, Customer customerPremium) {
        double sumNonPremium = 0;
        double sumPremium = 0;
        
        for (Customer customer : actual.keySet()) {
            if (!(customer.getId().equals(customerPremium.getId()))) {
                sumNonPremium = actual.get(customer);
            }
            else {
                sumPremium = actual.get(customer);
            }
        }
        assertEquals(sumNonPremiumExpected, sumNonPremium, 1);
        assertEquals(sumPremiumExpected, sumPremium, 1);
    }
}
