package com.bruce.banking.services;

import com.bruce.banking.dtos.AuthResponse;
import com.bruce.banking.dtos.LoginRequest;
import com.bruce.banking.dtos.RegisterRequest;
import com.bruce.banking.exceptions.EmailAlreadyExistsException;
import com.bruce.banking.exceptions.InvalidCredentialsException;
import com.bruce.banking.models.Customer;
import com.bruce.banking.repositories.CustomerRepository;
import com.bruce.banking.utils.AccountNumberGenerator;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class AuthService {
    private final JwtService jwtService;
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountNumberGenerator accountNumberGenerator;
    private final AuthenticationManager authManager;

    public AuthService(JwtService jwtService, CustomerRepository customerRepository, PasswordEncoder passwordEncoder, AccountNumberGenerator accountNumberGenerator, AuthenticationManager authManager) {
        this.jwtService = jwtService;
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.accountNumberGenerator = accountNumberGenerator;
        this.authManager = authManager;
    }

    public AuthResponse register(RegisterRequest request){
        if (customerRepository.existsByEmail(request.email())){
            throw new EmailAlreadyExistsException("Email already exists");
        }

        Customer customer = new Customer(
                request.firstName(),
                request.lastName(),
                request.email(),
                request.mobile(),
                request.dob(),
                accountNumberGenerator.generateAccountNumber(),
                BigDecimal.ZERO
        );

        customer.setPassword(passwordEncoder.encode(request.password()));

        customer = customerRepository.save(customer);

        String token = jwtService.generateToken(customer);

        return new AuthResponse(
                token,
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getAccount()
        );
    }

    public AuthResponse login(LoginRequest request){
        try{
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.email(), request.password())
            );

            Customer customer = customerRepository.findByEmail(request.email())
                    .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));

            String token = jwtService.generateToken(customer);

            return new AuthResponse(
                    token,
                    customer.getFirstName(),
                    customer.getLastName(),
                    customer.getEmail(),
                    customer.getAccount()
            );
        }catch (AuthenticationException e){
            throw new InvalidCredentialsException("Invalid credentials");
        }
    }
}
