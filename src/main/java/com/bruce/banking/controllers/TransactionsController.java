package com.bruce.banking.controllers;

import com.bruce.banking.dtos.ErrorResponse;
import com.bruce.banking.dtos.TransactionRequest;
import com.bruce.banking.dtos.TransferRequest;
import com.bruce.banking.models.Transaction;
import com.bruce.banking.services.TransactionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionsController {
    private final TransactionService transactionService;

    public TransactionsController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveMoney(@Valid @RequestBody TransactionRequest request){
        try{
            Transaction transaction = transactionService.saveMoney(request.getCustomerId(), request.getAmount());
            return ResponseEntity.ok(transaction);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @PostMapping("/withdraw")
    public ResponseEntity<?> withdrawMoney(@Valid @RequestBody TransactionRequest request){
        try{
            Transaction transaction = transactionService.withdrawMoney(request.getCustomerId(), request.getAmount());
            return ResponseEntity.ok(transaction);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @PostMapping("/transfer")
    public ResponseEntity<?> transferMoney(@Valid @RequestBody TransferRequest request){
        try{
            Transaction transaction = transactionService.transferMoney(request.getSenderId(), request.getReceiverAccount(), request.getAmount());

            return ResponseEntity.ok(transaction);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }
}
