package ch.codebulb.jpaqueries.service;

import ch.codebulb.jpaqueries.model.Product;

public class ProductService extends BaseService<Product> {
    @Override
    public Product create() {
        return new Product();
    }

    @Override
    protected Class<Product> getModelClass() {
        return Product.class;
    }
}
