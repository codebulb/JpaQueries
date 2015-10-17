package ch.codebulb.jpaqueries.service;

import ch.codebulb.jpaqueries.model.Customer;

public class CustomerService extends BaseService<Customer> {
    @Override
    public Customer create() {
        return new Customer();
    }

    @Override
    protected Class<Customer> getModelClass() {
        return Customer.class;
    }
}
