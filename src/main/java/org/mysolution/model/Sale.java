package org.mysolution.model;

import lombok.Getter;
import lombok.Setter;
import org.mysolution.enums.CatalogItem;

public class Sale {
    @Getter @Setter
    private CatalogItem productType;
    @Getter @Setter
    private double value;

    public Sale(CatalogItem productType, double value) {
        this.productType = productType;
        this.value = value;
    }
}
