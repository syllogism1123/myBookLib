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
        token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMSIsImlhdCI6MTY4NDg3NDk0MCwiZXhwIjoxNjg0ODc1NTQwfQ.rGbpObRPRYS56_p5AmFQpoU_0WqB-bQZj_jR-3KVxsc";
        Field jwtSecretKeyField = JwtService.class.getDeclaredField("jwtSecretKey");
        jwtSecretKeyField.setAccessible(true);
        jwtSecretKeyField.set(jwtService, "2D4A614E645267556A586E3272357538782F413F4428472B4B6250655368566D");
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
