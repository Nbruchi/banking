package com.bruce.banking.services;

import com.bruce.banking.dtos.CustomerUpdateDTO;
import com.bruce.banking.models.Customer;
import com.bruce.banking.repositories.CustomerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }

    public Optional<Customer> getCustomerById(Long id) {
        return customerRepository.findById(id);
    }

    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Optional<Customer> updateCustomer(CustomerUpdateDTO dto, Long id) {
        return customerRepository.findById(id).map(customer -> {
           if (dto.getFirstName() != null){
               customer.setFirstName(dto.getFirstName());
           }

           if (dto.getLastName() != null){
               customer.setLastName(dto.getLastName());
           }

           if (dto.getEmail() != null){
               customer.setEmail(dto.getEmail());
           }

           if (dto.getAccount() != null){
               customer.setAccount(dto.getAccount());
           }

           if (dto.getBalance() != null){
               customer.setBalance(dto.getBalance());
           }

           if (dto.getDob() != null){
               customer.setDob(dto.getDob());
           }

           if(dto.getMobile() != null){
               customer.setMobile(dto.getMobile());
           }

           customer.setLastUpdatedAt(LocalDateTime.now());
            return customerRepository.save(customer);
        });
    }
}
