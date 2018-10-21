package org.mysolution.processors;

import org.mysolution.enums.CatalogItem;
import org.mysolution.enums.MessageType;
import org.mysolution.enums.Operation;
import org.mysolution.model.Sale;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageProcessor {
    /**
     * Checks the type of message and returns it.
     * @param message
     * @return
     */
    public MessageType getMessageType(String message) {
        String [] msgParts = message.split(" ");

        CatalogItem productType = CatalogItem.NONE;
        Operation operation = Operation.NONE;
        String value = null;
        int noOfSales = 0;

        for (int i = 0; i < msgParts.length; i++) {
            if (isValue(msgParts[i])) {
                value = msgParts[i];
            } else if (isValidNumber(msgParts[i])) {
                noOfSales = Integer.parseInt(msgParts[i]);
            } else if (productType == CatalogItem.NONE || operation == Operation.NONE) {
                if (productType == CatalogItem.NONE) {
                    productType = CatalogItem.getCatalogItemFromName(msgParts[i]);
                }

                if (operation == Operation.NONE) {
                    operation = Operation.getOperationFromName(msgParts[i]);
                }
            }
        }

        if (noOfSales > 0 && productType != CatalogItem.NONE && isValue(value)) {
            return MessageType.TYPE2;
        } else if ((operation == Operation.ADD || operation == Operation.SUBTRACT) &&
                isValue(value) &&
                productType != CatalogItem.NONE){
            return MessageType.TYPE3;
        } else if (operation == Operation.MULTIPLY &&
                noOfSales > 0 &&
                productType != CatalogItem.NONE) {
            return MessageType.TYPE3;
        } else if (productType != CatalogItem.NONE &&
                isValue(value)) {
            return MessageType.TYPE1;
        } else {
            return MessageType.INVALID;
        }
    }

    /**
     * Check if the string is valid value
     * ex - 10p or 5p
     * @param msgPart
     * @return
     */
    public boolean isValue(String msgPart) {
        Pattern regex = Pattern.compile("[0-9]+p");
        Matcher matcher = regex.matcher(msgPart);

        return matcher.find();
    }

    /**
     * Saves the sale into the array provided
     * @param sale
     * @param sales
     */
    public void save(Sale sale, List<Sale> sales) {
        sales.add(sale);
    }

    private boolean isValidNumber(String msgPart) {
        Pattern regex = Pattern.compile("[1-9]");
        Matcher matcher = regex.matcher(msgPart);

        return matcher.find();
    }
}
