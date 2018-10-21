package org.mysolution.enums;

import lombok.Data;

public enum Operation {
    ADD(0, '+', "Add"),
    SUBTRACT(1, '-', "Subtract"),
    MULTIPLY(2, 'x', "Multiply"),
    NONE(3, '?', "None");

    private final int value;
    private final char symbol;
    private final String name;

    private Operation(final int value, final char symbol, final String name) {
        this.value = value;
        this.symbol = symbol;
        this.name = name;
    }

    public static Operation getOperationFromName(String name) {
        if (name.equalsIgnoreCase("multiplication")) {
            return Operation.MULTIPLY;
        }

        //For all other cases
        for (Operation item : Operation.values()) {
            String tempVal = item.name.toLowerCase();
            if (name.toLowerCase().equals(tempVal) ||
                    name.toLowerCase().contains(tempVal)) {
                return item;
            }
        }

        return Operation.NONE;
    }
}
