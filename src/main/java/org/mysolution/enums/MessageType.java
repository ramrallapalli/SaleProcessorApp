package org.mysolution.enums;

public enum MessageType {
    TYPE1(0, "Msg1"),
    TYPE2(1, "Msg2"),
    TYPE3(2, "Msg3"),
    INVALID(3, "InvalidMessage");

    private final int value;
    private final String name;

    private MessageType(final int newValue, final String newName) {
        value = newValue;
        name = newName;
    }
}
