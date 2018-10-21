package org.mysolution;

import org.mysolution.constants.Constants;
import org.mysolution.enums.MessageType;
import org.mysolution.model.Sale;
import org.mysolution.processors.MessageProcessor;
import org.mysolution.utils.MessageUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Application {
    public static void main(String [] args) {
        Scanner in = new Scanner(System.in);
        MessageProcessor processor = new MessageProcessor();
        List<Sale> sales = new ArrayList<>();
        int messageCounter = 0;

        System.out.print("Sale/Adjustments mesaages can be in following format:\n " +
                "1. Apple at 10p (Single Sale)\n " +
                "2. 20 sales of apples at 10p each (Multiple/Bulk Sale)\n " +
                "3. Add/Subtract 20p apples (Adjustment Operation)\n " +
                "4. Multiply Banana by 5p (Adjustment Operation)\n");
        String message = Constants.EMPTY_STRING;
        while (!message.equalsIgnoreCase("Exit")) {
            if (messageCounter == 5) {
                System.out.println("System Paused! Cannot process further messages ....");
                reportAdjustmentsForTheDay(sales);
                break;
            }

            System.out.print("Enter a Sale: ");
            message = in.nextLine();
            MessageType type = processor.getMessageType(message);

            if (type == MessageType.INVALID) {
                System.out.println("Invalid Message!");
                System.out.print("Enter a Sale: ");
            } else if (type == MessageType.TYPE1) {
                Sale sale = MessageUtils.processSingleSale(message);
                String response = processor.save(sale, sales);
                if (!response.isEmpty()) {
                    System.out.println(response);
                }
                messageCounter++;
            } else if (type == MessageType.TYPE2) {
                List<Sale> newSales = MessageUtils.processMultiSale(message);
                processor.saveBulkSales(newSales, sales);
                messageCounter++;
            } else if (type == MessageType.TYPE3) {
                MessageUtils.runAdjustmentOperationOnSales(sales, message);
                messageCounter++;
            }
        }
    }

    private static void reportAdjustmentsForTheDay(List<Sale> sales) {
        for (Sale thisSale : sales) {
            System.out.println(thisSale.getAdjustments().toString() + "\n");
        }
    }
}
