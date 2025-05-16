package com.bruce.banking.controllers;

import com.bruce.banking.dtos.ErrorResponse;
import com.bruce.banking.dtos.MessageResponse;
import com.bruce.banking.exceptions.MessageNotFoundException;
import com.bruce.banking.models.Message;
import com.bruce.banking.services.MessageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getCustomerMessages(
            @PathVariable Long customerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){
        try{
            PageRequest pageRequest = PageRequest.of(page, size, Sort.by("dateTime").descending());

            Page<Message> messages = messageService.getCustomerMessages(customerId,pageRequest);

            Page<MessageResponse> responseMessages = messages.map(this::convertToResponse);
            return ResponseEntity.ok(responseMessages);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/{messageId}")
    public ResponseEntity<?> getMessageBy(@PathVariable Long messageId){
        try{
            Message message = messageService.getMessageById(messageId).orElseThrow(
                    () -> new MessageNotFoundException("Message with id " + messageId + " not found")
            );

            return ResponseEntity.ok(convertToResponse(message));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @PostMapping("/{messageId}/retry")
    public ResponseEntity<?> retryFailedMessage(@PathVariable Long messageId){
        try{
            messageService.retryFailedMessage(messageId);
            return ResponseEntity.ok("Message retried successfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    private MessageResponse convertToResponse(Message message){
        MessageResponse response = new MessageResponse();
        response.setId(message.getId());
        response.setCustomerId(message.getCustomer().getId());
        response.setMessage(message.getMessage());
        response.setDateTime(message.getDateTime());
        response.setStatus(message.getStatus());

        if (message.getTransaction() != null){
            response.setTransactionId(message.getTransaction().getId());
        }
        return response;
    }
}
