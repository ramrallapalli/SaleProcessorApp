package org.mysolution.model;

import lombok.Getter;
import lombok.Setter;
import org.mysolution.enums.CatalogItem;

import java.util.Date;

public class Sale {
    @Getter @Setter
    private CatalogItem productType;
    @Getter @Setter
    private double value;
    @Getter @Setter
    private StringBuilder adjustments;

    public Sale(CatalogItem productType, double value) {
        this.productType = productType;
        this.value = value;
        Date currDate = new Date(System.currentTimeMillis());
        adjustments = new StringBuilder(currDate.toString() + ": " + productType +
                " Sale @ " + value + "\n");
    }
}
