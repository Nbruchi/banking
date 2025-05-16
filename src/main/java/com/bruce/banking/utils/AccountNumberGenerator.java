package com.bruce.banking.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class AccountNumberGenerator {
    private static final String COUNTRY_PREFIX = "RW";
    private final SecureRandom random = new SecureRandom();

    public String generateAccountNumber() {
        StringBuilder accountNumber = new StringBuilder(COUNTRY_PREFIX);

        int ACCOUNT_NUMBER_LENGTH = 9;
        for (int i = 0; i < ACCOUNT_NUMBER_LENGTH; i++) {
            accountNumber.append(random.nextInt(10));
        }

        return accountNumber.toString();
    }
}
