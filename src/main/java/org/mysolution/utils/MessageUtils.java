package org.mysolution.utils;

import org.mysolution.enums.CatalogItem;
import org.mysolution.enums.Operation;
import org.mysolution.model.Sale;

import java.util.ArrayList;
import java.util.List;

public class MessageUtils {
    /**
     * Processes the sale message type 1 and returns the sale object
     * @param message
     * @return
     */
    public static Sale processSingleSale(String message) {
        String [] msgParts = message.split(" ");

        //process the messsage type and value
        CatalogItem productType = CatalogItem.getCatalogItemFromName(msgParts[0]);
        double value = getProductValue(msgParts[2]);

        return new Sale(productType, value);
    }

    /**
     * Processes the sale message type 2 and returns the List of sale objects
     * ex - 20 sales of apples at 10p each
     * @param message
     * @return
     */
    public static List<Sale> processMultiSale(String message) {
        List<Sale> sales = new ArrayList<>();
        String [] msgParts = message.split(" ");

        int numberOfSales = Integer.parseInt(msgParts[0]);
        CatalogItem productType = CatalogItem.getCatalogItemFromName(msgParts[3]);
        double value = getProductValue(msgParts[5]);

        for (int i = 0; i < numberOfSales; i++) {
            Sale newSale = new Sale(productType, value);
            sales.add(newSale);
        }

        return sales;
    }

    /**
     * Performs adjustment operation on Sales
     * ex - Add 20p Apples or Multiply Apples by 5
     * @param currentSales
     * @param message
     */
    public static void runAdjustmentOperationOnSales(List<Sale> currentSales, String message) {
        String [] msgParts = message.split(" ");
        Operation operation = Operation.getOperationFromName(msgParts[0]);

        if (operation == Operation.ADD) {
            double value = getProductValue(msgParts[1]);
            CatalogItem product = CatalogItem.getCatalogItemFromName(msgParts[2]);

            for (Sale thisSale : currentSales) {
                if (thisSale.getProductType() == product) {
                    double newVal = thisSale.getValue() + value;
                    thisSale.setValue(newVal);
                }
            }
        } else if (operation == Operation.SUBTRACT) {
            double value = getProductValue(msgParts[1]);
            CatalogItem product = CatalogItem.getCatalogItemFromName(msgParts[2]);

            for (Sale thisSale : currentSales) {
                if (thisSale.getProductType() == product) {
                    double currValue = thisSale.getValue();
                    double newVal = currValue - value;

                    if (newVal > 0.0)
                        thisSale.setValue(newVal);
                }
            }
        } else {
            double factor = Double.parseDouble(msgParts[3]);
            CatalogItem product = CatalogItem.getCatalogItemFromName(msgParts[1]);

            for (Sale thisSale : currentSales) {
                if (thisSale.getProductType() == product) {
                    double newVal = thisSale.getValue() * factor;
                    thisSale.setValue(newVal);
                }
            }
        }
    }

    /**
     * converts the price in string (ex - 10p) to double value 10.0
     * @param productValue
     * @return
     */
    private static double getProductValue(String productValue) {
        char [] priceArray = productValue.toCharArray();

        StringBuilder val = new StringBuilder();
        for (int i = 0; i < priceArray.length; i++) {
            if (priceArray[i] != 'p') {
                val.append(priceArray[i]);
            } else {
                break;
            }
        }

        double result = Double.parseDouble(val.toString());

        return result;
    }
}
