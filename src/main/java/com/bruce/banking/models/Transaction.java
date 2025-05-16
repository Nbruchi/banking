package com.bruce.banking.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bruce.banking.utils.CurrencyFormatter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.hibernate.annotations.UpdateTimestamp;

@SuppressWarnings("unused")
@Entity
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    @NotNull(message = "Customer is required")
    private Customer customer;

    @Column(nullable = false)
    @NotNull(message = "Account number is required")
    private String account;

    @Column(name = "transaction_date", nullable = false)
    @UpdateTimestamp
    private LocalDateTime bankingDateTime = LocalDateTime.now();

    @Column(nullable = false, precision = 19, scale = 4)
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Transaction type is required")
    private TransactionType type;

    // Default constructor
    protected Transaction() {
    }

    // Main constructor
    public Transaction(Customer customer, String account, BigDecimal amount,
                       TransactionType type) {
        this.customer = customer;
        this.account = account;
        this.amount = amount;
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @JsonIgnore
    public BigDecimal getAmount() {
        return amount;
    }

    @JsonProperty("amount")
    public String getFormattedAmount(){
        return CurrencyFormatter.format(amount);
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public LocalDateTime getBankingDateTime() {
        return bankingDateTime;
    }

    public void setBankingDateTime(LocalDateTime bankingDateTime) {
        this.bankingDateTime = bankingDateTime;
    }
}
