package org.mysolution.enums;

public enum CatalogItem {
    APPLE(0, "Apple"),
    ORANGE(1, "Orange"),
    BANANA(2, "Banana"),
    NONE(3, "None");

    private final int value;
    private final String name;

    private CatalogItem(final int newValue, final String newName) {
        value = newValue;
        name = newName;
    }

    public static CatalogItem getCatalogItemFromName(String name) {
        for (CatalogItem item : CatalogItem.values()) {
            String tempVal = item.name.toLowerCase();
            if (name.toLowerCase().equals(tempVal) ||
                    name.toLowerCase().contains(tempVal)) {
                return item;
            }
        }

        return CatalogItem.NONE;
    }

}
