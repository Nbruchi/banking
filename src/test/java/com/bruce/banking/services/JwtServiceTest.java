package com.bruce.banking.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JwtServiceTest {
    private JwtService jwtService;
    private UserDetails userDetails;
    private static final String SECRET = "404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970";

    @BeforeEach
    void setup(){
        jwtService = new JwtService();
        ReflectionTestUtils.setField(jwtService, "secretKey", SECRET);

        userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("test@example.com");
    }

    @Test
    void generateToken_ShouldCreateValidToken(){
        // When - Generate a token using our mock user
        String token = jwtService.generateToken(userDetails);

        // Then - Verify the token
        assertThat(token).isNotNull();  // Token should not be null
        assertThat(token.split("\\.")).hasSize(3);  // JWT should have 3 parts (header.payload.signature)
        assertThat(jwtService.isTokenValid(token, userDetails)).isTrue();  // Token should be valid for our user
    }

    @Test
    void extractUsername_ShouldReturnCorrectUsername(){
        // When
        String token = jwtService.generateToken(userDetails);
        String extractedUsername = jwtService.extractUsername(token);

        // Then
        assertThat(extractedUsername).isEqualTo("test@example.com");
    }

    @Test
    void isTokenValid_ShouldReturnFalseForInvalidUser(){
        // Given
        String token = jwtService.generateToken(userDetails);
        UserDetails anotherUser = mock(UserDetails.class);
        when(anotherUser.getUsername()).thenReturn("another@example.com");

        //When
        boolean isValid = jwtService.isTokenValid(token,anotherUser);

        //Then
        assertThat(isValid).isFalse();
    }

    @Test
    void isTokenValid_ShouldReturnTrueForValidToken(){
        //When
        String token = jwtService.generateToken(userDetails);

        //Then
        assertThat(jwtService.isTokenValid(token,userDetails)).isTrue();
    }
}