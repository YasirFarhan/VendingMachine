package com.vendingmachine.vendingmachine.constants;

public interface ItemsTable {
    String TABLE_NAME = "items";

    interface Columns {
        String ID = "id";
        String NAME = "name";
        String PRICE = "price";
        String QUANTITY = "quantity";
    }
}
