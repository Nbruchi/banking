package com.bruce.banking.services;

import com.bruce.banking.exceptions.CustomerNotFoundException;
import com.bruce.banking.exceptions.InsufficientFundsException;
import com.bruce.banking.models.Customer;
import com.bruce.banking.models.Transaction;
import com.bruce.banking.models.TransactionType;
import com.bruce.banking.repositories.CustomerRepository;
import com.bruce.banking.repositories.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Transactional
public class TransactionService {
    private final CustomerRepository customerRepository;
    private final MessageService messageService;
    private final TransactionRepository transactionRepository;

    public TransactionService(CustomerRepository customerRepository, MessageService messageService, TransactionRepository transactionRepository) {
        this.customerRepository = customerRepository;
        this.messageService = messageService;
        this.transactionRepository = transactionRepository;
    }

    public Transaction saveMoney(Long customerId, BigDecimal amount){
        Customer customer = customerRepository.findById(customerId).orElseThrow(() ->
                new CustomerNotFoundException("Customer with id " + customerId + " not found"));

        Transaction transaction = new Transaction(customer, customer.getAccount(),amount, TransactionType.SAVING);
        customer.setBalance(customer.getBalance().add(amount));
        transactionRepository.save(transaction);

        messageService.sendNotification(customer, transaction);

        return transaction;
    }

    public Transaction withdrawMoney(Long customerId, BigDecimal amount){
        Customer customer = customerRepository.findById(customerId).orElseThrow(() ->
                new CustomerNotFoundException("Customer with id " + customerId + " not found"));

        if (customer.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds for withdrawal");
        }

        BigDecimal newBalance = customer.getBalance().subtract(amount);
        customer.setBalance(newBalance);
        customerRepository.save(customer);

        Transaction transaction = new Transaction(customer, customer.getAccount(),amount, TransactionType.WITHDRAW);
        transactionRepository.save(transaction);

        messageService.sendNotification(customer, transaction);

        return transaction;
    }

    public Transaction transferMoney(Long senderId, String recipientAccount, BigDecimal amount){
        Customer sender = customerRepository.findById(senderId).orElseThrow(() ->
                new CustomerNotFoundException("Customer with id " + senderId + " not found"));

        Customer recipient = customerRepository.findByAccount(recipientAccount).orElseThrow(
                () -> new CustomerNotFoundException("Customer with account " + recipientAccount + " not found")
        );

        if (sender.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException("Insufficient funds for transfer");
        }

        BigDecimal newSenderBalance = sender.getBalance().subtract(amount);
        sender.setBalance(newSenderBalance);
        customerRepository.save(sender);

        BigDecimal newRecipientBalance = recipient.getBalance().add(amount);
        recipient.setBalance(newRecipientBalance);
        customerRepository.save(recipient);

        Transaction transaction = new Transaction(sender, recipientAccount,amount, TransactionType.TRANSFER);
        transactionRepository.save(transaction);

        Transaction recipientTransaction = new Transaction(recipient, sender.getAccount(),amount, TransactionType.TRANSFER_RECEIVED);
        transactionRepository.save(recipientTransaction);

        messageService.sendNotification(sender, transaction);
        messageService.sendNotification(recipient, recipientTransaction);

        return transaction;
    }
}
