package com.xin.bookbackend.security;

import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Field;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class JwtServiceTest {
    @InjectMocks
    private JwtService jwtService;
    @Mock
    private UserDetails userDetails;
    private String testUsername;
    private String token;

    @BeforeEach
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        testUsername = "user1";
        token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImlhdCI6MTY4NDk1MTgxNywiZXhwIjoxNjg0OTUyNDE3fQ.Zpm-UMf1IjEiR1nYN3_vq_OeJYw1Ljp1yAx42VUZ0ro";
        Field jwtSecretKeyField = JwtService.class.getDeclaredField("jwtSecretKey");
        jwtSecretKeyField.setAccessible(true);
        jwtSecretKeyField.set(jwtService, "36763979244226452948404D6351665468576D5A7134743777217A25432A462D");
    }

    @Test
    void extractUsername() {
        String extractedUsername = jwtService.extractUsername(token);
        assertEquals(testUsername, extractedUsername);
    }

    @Test
    void extractExpiration() {
        Date extractedExpiration = jwtService.extractExpiration(token);
        assertNotNull(extractedExpiration);
    }

    @Test
    void extractClaim() {
        String extractedClaim = jwtService.extractClaim(token, Claims::getSubject);
        assertEquals(testUsername, extractedClaim);
    }

    @Test
    void generateToken() {
        when(userDetails.getUsername()).thenReturn(testUsername);
        String generatedToken = jwtService.generateToken(testUsername);
        assertNotNull(generatedToken);
    }

    @Test
    void validateToken() {
        when(userDetails.getUsername()).thenReturn(testUsername);
        String generatedToken = jwtService.generateToken(testUsername);
        Boolean isValid = jwtService.validateToken(generatedToken, userDetails);
        assertTrue(isValid);
    }
}
