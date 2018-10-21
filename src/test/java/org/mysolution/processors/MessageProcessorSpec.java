package org.mysolution.processors;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mysolution.enums.MessageType;
import org.mysolution.model.Sale;
import org.mysolution.utils.MessageUtils;

import java.util.ArrayList;
import java.util.List;

public class MessageProcessorSpec {
    private MessageProcessor processor;

    @Before
    public final void before() {
        processor = new MessageProcessor();
    }

    @Test
    public void givenCorrectValueThenReturnTrue() {
        String value = "20p";
        Assert.assertEquals(Boolean.TRUE, processor.isValue(value));
    }

    @Test
    public void givenWrongValueThenReturnFalse() {
        String value = "Apples";
        Assert.assertEquals(Boolean.FALSE, processor.isValue(value));
    }

    @Test
    public void whenMessageType1ReceivedThenReturnMessageType1() {
        String message = "Apple at 10p";
        Assert.assertEquals(MessageType.TYPE1, processor.getMessageType(message));
    }

    @Test
    public void whenMessageType2ReceivedThenReturnMessageType2() {
        String message = "20 sales of apples at 10p each";
        Assert.assertEquals(MessageType.TYPE2, processor.getMessageType(message));
    }

    @Test
    public void whenMessageType3ReceivedThenReturnMessageType3() {
        String message = "Subtract 2p Orange";
        Assert.assertEquals(MessageType.TYPE3, processor.getMessageType(message));
    }

    @Test
    public void givenNewSaleSaveIt() {
        List<Sale> sales = new ArrayList<>();
        Sale sale = MessageUtils.processSingleSale("Apple at 10p");

        processor.save(sale, sales);
        Assert.assertEquals(1, sales.size());
    }

    public void whenTenthSaleLogTheTotalSales() {

    }
}
