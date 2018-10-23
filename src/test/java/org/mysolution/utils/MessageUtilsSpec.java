package org.mysolution.utils;

import org.junit.Assert;
import org.junit.Test;
import org.mysolution.enums.CatalogItem;
import org.mysolution.model.Sale;

import java.util.ArrayList;
import java.util.List;


public class MessageUtilsSpec {

    @Test
    public void whenMessageType1NewSaleReturned() {
        String message = "Apple at 10p";
        Sale sale = MessageUtils.processSingleSale(message);
        Assert.assertEquals(CatalogItem.APPLE, sale.getProductType());
        Assert.assertEquals(10.0, sale.getValue(), 0D);
    }

    @Test
    public void whenMessageType2SaleItemsReturned() {
        String message = "20 sales of apples at 10p each";
        List<Sale> sales = MessageUtils.processMultiSale(message);
        Assert.assertEquals(20, sales.size());

        for (Sale thisSale : sales) {
            Assert.assertEquals(CatalogItem.APPLE, thisSale.getProductType());
            Assert.assertEquals(10.0, thisSale.getValue(), 0D);
        }
    }

    @Test
    public void whenMessageType3AdjustmentOperationOnSales() {
        List<Sale> currentSales = new ArrayList<>();

        Sale appleSale = new Sale(CatalogItem.APPLE, 20);
        Sale orangeSale = new Sale(CatalogItem.ORANGE, 12);
        Sale pumpkinSale = new Sale(CatalogItem.BANANA, 7);

        currentSales.add(appleSale);
        currentSales.add(orangeSale);
        currentSales.add(pumpkinSale);

        //Add Operation
        String message = "Add 20p apples";
        MessageUtils.runAdjustmentOperationOnSales(currentSales, message);

        for (Sale thisSale : currentSales) {
            if (thisSale.getProductType() == CatalogItem.APPLE) {
                Assert.assertEquals(40.0, thisSale.getValue(), 0D);
            }
        }

        //Subtract Operation
        message = "Subtract 2p Orange";
        MessageUtils.runAdjustmentOperationOnSales(currentSales, message);

        for (Sale thisSale : currentSales) {
            if (thisSale.getProductType() == CatalogItem.ORANGE) {
                Assert.assertEquals(10.0, thisSale.getValue(), 0D);
            }
        }

        //Multiply Operation
        message = "Multiply Banana by 5";
        MessageUtils.runAdjustmentOperationOnSales(currentSales, message);

        for (Sale thisSale : currentSales) {
            if (thisSale.getProductType() == CatalogItem.BANANA) {
                Assert.assertEquals(35.0, thisSale.getValue(), 0D);
            }
        }
    }
}
