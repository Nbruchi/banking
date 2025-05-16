package com.bruce.banking.dtos;

import com.bruce.banking.utils.CurrencyFormatter;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;

public class TransferRequest {
    @NotNull(message = "Sender ID is required")
    private Long senderId;

    @NotNull(message = "Receiver account is required")
    private String receiverAccount;

    @NotNull(message = "Amount is required")
    @PositiveOrZero(message = "Amount must be positive or zero")
    @Digits(integer = 15, fraction = 2, message = "Amount can have up to 15 digits and 2 decimal places")
    private BigDecimal amount;

    public TransferRequest() {}

    public TransferRequest(Long senderId, String receiverAccount, BigDecimal amount) {
        this.senderId = senderId;
        this.receiverAccount = receiverAccount;
        this.amount = amount;
    }

    public Long getSenderId() {
        return senderId;
    }

    public void setSenderId(Long senderId) {
        this.senderId = senderId;
    }

    public String getReceiverAccount() {
        return receiverAccount;
    }

    public void setReceiverAccount(String receiverAccount) {
        this.receiverAccount = receiverAccount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "TransferRequest{" +
                "senderId=" + senderId +
                ", receiverAccount='" + receiverAccount + '\'' +
                ", amount=" + amount +
                '}';
    }
}
