package org.mysolution.model;

import lombok.Getter;
import lombok.Setter;

public class Report {
    @Getter @Setter
    private int totalNoOfSales = -1;

    @Getter @Setter
    private double totalValueOfSales = 0.0;
}
