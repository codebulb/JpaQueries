package ch.codebulb.jpaqueries.service;

import ch.codebulb.jpaqueries.model.Purchase;

public class PurchaseService extends BaseService<Purchase> {
    @Override
    public Purchase create() {
        return new Purchase();
    }

    @Override
    protected Class<Purchase> getModelClass() {
        return Purchase.class;
    }
}
