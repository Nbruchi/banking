package com.bruce.banking.controllers;

import com.bruce.banking.dtos.CustomerUpdateDTO;
import com.bruce.banking.dtos.ErrorResponse;
import com.bruce.banking.models.Customer;
import com.bruce.banking.services.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequestMapping("/customers")
@RestController
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody Customer customer) {
        try{
            Customer savedCustomer = customerService.createCustomer(customer);
            return ResponseEntity.ok(savedCustomer);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllCustomers(){
        try{
            List<Customer> customers = customerService.getAllCustomers();
            return ResponseEntity.ok(customers);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id){
        try{
            Optional<Customer> customer = customerService.getCustomerById(id);
            return ResponseEntity.ok(customer);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable Long id){
        try{
            customerService.deleteCustomerById(id);
            return ResponseEntity.ok("Customer deleted successfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> updateCustomer(@RequestBody CustomerUpdateDTO customer, @PathVariable Long id){
        try{
            Optional<Customer> updatedCustomer = customerService.updateCustomer(customer, id);
            return ResponseEntity.ok(updatedCustomer);
        }catch (Exception e){
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }
}
