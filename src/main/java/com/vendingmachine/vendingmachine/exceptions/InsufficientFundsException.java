package com.vendingmachine.vendingmachine.exceptions;

public class InsufficientFundsException extends Exception {

    public InsufficientFundsException(String message) {
        super(message);
    }
}
