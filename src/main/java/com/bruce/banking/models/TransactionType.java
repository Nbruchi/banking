package com.bruce.banking.models;

public enum TransactionType {
    WITHDRAW,
    TRANSFER,
    SAVING, TRANSFER_RECEIVED;

    // You can override toString() if you want a custom string representation
    @Override
    public String toString() {
        return name().charAt(0) + name().substring(1).toLowerCase();
    }
}