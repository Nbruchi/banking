package com.bruce.banking.repositories;

import com.bruce.banking.models.Customer;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByAccount(String account);

    Optional<Customer> findByEmail(String email);

    boolean existsByEmail(@NotBlank String email);

    boolean existsByAccount(String account);
}
