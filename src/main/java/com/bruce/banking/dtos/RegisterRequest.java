package com.bruce.banking.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

import java.time.LocalDate;

public record RegisterRequest (
        @NotBlank String firstName,
        @NotBlank String lastName,
        @NotBlank String email,
        @NotBlank String password,
        @NotNull @Past LocalDate dob,
        @Pattern(regexp = "^(?:\\+250|0)7[238][0-9]{7}$")
    @NotBlank String mobile
){}
