package org.mysolution.processors;

import org.mysolution.constants.Constants;
import org.mysolution.enums.CatalogItem;
import org.mysolution.enums.MessageType;
import org.mysolution.enums.Operation;
import org.mysolution.model.Report;
import org.mysolution.model.Sale;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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
    public String save(Sale sale, List<Sale> sales) {
        sales.add(sale);

        String result = getReportLogMessage(sales);
        if (!result.isEmpty())
            return result;
        else
            return Constants.EMPTY_STRING;
    }

    public void saveBulkSales(List<Sale> newSales, List<Sale> sales) {
        for (Sale currSale : newSales) {
            String result = this.save(currSale, sales);
            if (!result.isEmpty()) {
                System.out.println(result);
            }
        }
    }

    /**
     * After every 10 sales, generates the sales report when invoked.
     * @param sales
     * @return
     */
    private String getReportLogMessage(List<Sale> sales) {
        Map<CatalogItem, Report> report = new TreeMap<>();
        StringBuilder result = new StringBuilder(Constants.EMPTY_STRING);

        if (sales.size() % 10 == 0) {
            for (Sale thisSale : sales) {
                if (report.containsKey(thisSale.getProductType())) {
                    Report thisReport = report.get(thisSale.getProductType());

                    thisReport.setTotalNoOfSales(thisReport.getTotalNoOfSales() + 1);
                    thisReport.setTotalValueOfSales(thisReport.getTotalValueOfSales() +
                                                        thisSale.getValue());
                } else {
                    Report newReport = new Report();
                    newReport.setTotalNoOfSales(1);
                    newReport.setTotalValueOfSales(thisSale.getValue());
                    report.put(thisSale.getProductType(), newReport);
                }
            }

            for (CatalogItem key : report.keySet()) {
                Report thisReport = report.get(key);
                result.append(key.name() + ": Total Sales - " + thisReport.getTotalNoOfSales() +
                "; Total Value - " + thisReport.getTotalValueOfSales() + "\n");
            }
        }

        return result.toString();
    }

    private boolean isValidNumber(String msgPart) {
        Pattern regex = Pattern.compile("[1-9]");
        Matcher matcher = regex.matcher(msgPart);

        return matcher.find();
    }
}

