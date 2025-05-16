package com.bruce.banking.dtos;

public record AuthResponse(
        String token,
        String firstName,
        String lastName,
        String email,
        String account
) {
}
