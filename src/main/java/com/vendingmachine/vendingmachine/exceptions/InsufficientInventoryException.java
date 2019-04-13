package com.vendingmachine.vendingmachine.exceptions;

public class InsufficientInventoryException extends Exception {

    public InsufficientInventoryException(String message) {
        super(message);
    }
}