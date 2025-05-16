
package com.bruce.banking.utils;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyFormatter {
    private static final NumberFormat currencyFormatter;

    static {
        currencyFormatter = NumberFormat.getNumberInstance(Locale.US);
        currencyFormatter.setMinimumFractionDigits(2);
        currencyFormatter.setMaximumFractionDigits(2);
    }

    public static String format(BigDecimal amount) {
        if (amount == null) {
            return "0.00";
        }
        return currencyFormatter.format(amount);
    }

    // You can add more formatting methods here if needed
    // For example, if you need to format with different locales or specific currency symbols
    public static String formatWithCurrency(BigDecimal amount) {
        if (amount == null) {
            return "RWF 0.00";
        }
        return "RWF " + currencyFormatter.format(amount);
    }
}