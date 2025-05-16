package com.bruce.banking.services;

import com.bruce.banking.models.Customer;
import com.bruce.banking.utils.CurrencyFormatter;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@SuppressWarnings("unused")
@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendTransactionEmail(Customer customer, String transactionType, BigDecimal amount, String account) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(customer.getEmail());
        message.setSubject("Transaction Notification");
        message.setText(String.format(
                "Dear %s %s, Your %s of %s on your account %s has been Completed Successfully.",
                customer.getFirstName(), customer.getLastName(),
                transactionType, CurrencyFormatter.formatWithCurrency(amount), account));
        mailSender.send(message);
    }
}