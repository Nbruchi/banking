package com.bruce.banking.services;

import com.bruce.banking.exceptions.MessageNotFoundException;
import com.bruce.banking.models.Customer;
import com.bruce.banking.models.Message;
import com.bruce.banking.models.MessageStatus;
import com.bruce.banking.models.Transaction;
import com.bruce.banking.repositories.MessageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final EmailService emailService;
    private final JavaMailSender emailSender;

    public MessageService(MessageRepository messageRepository, EmailService emailService, JavaMailSender emailSender) {
        this.messageRepository = messageRepository;
        this.emailService = emailService;
        this.emailSender = emailSender;
    }

    public void sendNotification(Customer customer, Transaction transaction){
        try {
            // Send email
            sendTransactionEmail(customer, transaction);
            // Save notification to the database
            String messageText = buildNotification(customer, transaction);
            Message message = new Message(customer, messageText);
            message.setTransaction(transaction);
            message.setStatus(MessageStatus.SENT);
            messageRepository.save(message);
        } catch (Exception e) {
            // Handle the error (log it, save failed status, etc.)
            Message message = new Message(customer, e.getMessage());
            message.setTransaction(transaction);
            message.setStatus(MessageStatus.FAILED);
            messageRepository.save(message);
        }
    }

    private String buildNotification(Customer customer, Transaction transaction){
        return String.format(
                "Dear %s %s, Your %s of RWF %,.2f on your account %s has been completed successfully!",
                customer.getFirstName(),
                customer.getLastName(),
                transaction.getType().toString().toLowerCase(),
                transaction.getAmount(),
                transaction.getAccount()
        );
    }

    public Page<Message> getCustomerMessages(Long customerId, Pageable pageable){
        return messageRepository.findByCustomerId(customerId,pageable);
    }

    public Optional<Message> getMessageById(Long id){
        return messageRepository.findById(id);
    }

    public void retryFailedMessage(Long messageId){
        Message message = getMessageById(messageId).orElseThrow(() ->
                new MessageNotFoundException("Message with id " + messageId + " not found"));

        if (message.getStatus() == MessageStatus.FAILED){
            try {
                SimpleMailMessage mailMessage = new SimpleMailMessage();
                mailMessage.setTo(message.getCustomer().getEmail());
                mailMessage.setSubject("Transaction Notification - Retry");
                mailMessage.setText(createMessageContent(message.getTransaction()));

                emailSender.send(mailMessage);
                message.setStatus(MessageStatus.SENT);
            }catch (Exception e){
                message.setStatus(MessageStatus.FAILED);
                message.setMessage(e.getMessage());
            }
            messageRepository.save(message);
        }
    }

    public void sendTransactionEmail(Customer customer, Transaction transaction){
        try{
            emailService.sendTransactionEmail(
                    customer,
                    transaction.getType().toString().toLowerCase(),
                    transaction.getAmount(),
                    transaction.getAccount()
            );
        }catch (MailException e){
            System.err.println("Error sending email: " + e.getMessage());
        }
    }

    private String createMessageContent(Transaction transaction) {
        return String.format(
                "Dear %s %s,\n\nA %s transaction of %s has been processed on your account.\nNew balance: %s\n\nBest regards,\nYour Bank",
                transaction.getCustomer().getFirstName(),
                transaction.getCustomer().getLastName(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getCustomer().getBalance()
        );
    }
}
