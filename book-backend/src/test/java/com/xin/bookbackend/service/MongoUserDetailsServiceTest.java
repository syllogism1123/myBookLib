package com.xin.bookbackend.service;

import com.xin.bookbackend.model.MongoUser;
import com.xin.bookbackend.repo.MongoUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MongoUserDetailsServiceTest {

    private MongoUserDetailsService detailsService;
    @Mock
    private MongoUserRepository mongoUserRepository;

    @BeforeEach
    void setup() {
        detailsService = new MongoUserDetailsService(mongoUserRepository);
    }


    @Test
    void loadUserByUsername_Successful() {

        MongoUser mongoUser = new MongoUser(UUID.randomUUID().toString(),
                "username", "password", "firstname", "lastname");
        when(mongoUserRepository.findMongoUserByUsername(mongoUser.username())).thenReturn(Optional.of(mongoUser));

        detailsService.loadUserByUsername(mongoUser.username());

        verify(mongoUserRepository).findMongoUserByUsername(mongoUser.username());

    }

    @Test
    void testLoadUserByUsername_failed() {

        when(mongoUserRepository.findMongoUserByUsername("username")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> detailsService.loadUserByUsername("username"));
    }

}