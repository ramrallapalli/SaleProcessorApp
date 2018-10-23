package org.mysolution.model;


import org.junit.Assert;
import org.junit.Test;
import org.mysolution.enums.CatalogItem;

public class SaleSpec {

    private Sale sale;
    private static final CatalogItem PRODUCT_TYPE = CatalogItem.APPLE;
    private static final double PRODUCT_VALUE = 0.0;

    @Test
    public void whenInstantiatedProductTypeIsSet() {
        sale = new Sale(PRODUCT_TYPE, PRODUCT_VALUE);
        Assert.assertEquals(PRODUCT_TYPE, sale.getProductType());
    }

    @Test
    public void whenInstantiatedValueIsSet() {
        sale = new Sale(PRODUCT_TYPE, PRODUCT_VALUE);
        Assert.assertEquals(PRODUCT_VALUE, sale.getValue(), 0D);
    }

}
