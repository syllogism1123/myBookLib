package com.xin.bookbackend.service;

import com.xin.bookbackend.model.user.MongoUser;
import com.xin.bookbackend.model.user.MongoUserDTO;
import com.xin.bookbackend.repo.MongoUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private UserService userService;
    @Mock
    private MongoUserRepository mongoUserRepository;
    @Mock
    private PasswordEncoder encoder;

    @BeforeEach
    void setup() {
        userService = new UserService(mongoUserRepository, encoder);
    }

    @Test
    void createMongoUser_Successful() {
        MongoUserDTO mongoUserDTO = new MongoUserDTO(UUID.randomUUID().toString(),
                "username", "password", "firstname", "lastname");
        String encodedPassword = encoder.encode(mongoUserDTO.password());
        MongoUser encodedUser = new MongoUser(mongoUserDTO.username(),
                encodedPassword, mongoUserDTO.firstname(), mongoUserDTO.lastname(), mongoUserDTO.email());
        when(mongoUserRepository.findMongoUserByUsername(mongoUserDTO.username())).thenReturn(Optional.empty());

        userService.createMongoUser(mongoUserDTO);

        verify(mongoUserRepository).save(encodedUser);

    }

    @Test
    void createMongoUser_failed() {
        MongoUserDTO mongoUserDTO = new MongoUserDTO(UUID.randomUUID().toString(),
                "username", "password", "firstname", "lastname");
        String encodedPassword = encoder.encode(mongoUserDTO.password());
        MongoUser encodedUser = new MongoUser(mongoUserDTO.username(),
                encodedPassword, mongoUserDTO.firstname(), mongoUserDTO.lastname(), mongoUserDTO.email());
        when(mongoUserRepository.findMongoUserByUsername(mongoUserDTO.username())).thenReturn(Optional.of(encodedUser));

        assertThrows(IllegalArgumentException.class, () -> userService.createMongoUser(mongoUserDTO));
    }

    @Test
    void findUserByUsername() {
        MongoUser mongoUser = new MongoUser(UUID.randomUUID().toString(),
                "username", "password", "firstname", "lastname");
        when(mongoUserRepository.findMongoUserByUsername(mongoUser.username())).thenReturn(Optional.of(mongoUser));

        userService.findUserByUsername(mongoUser.username());

        verify(mongoUserRepository).findMongoUserByUsername(mongoUser.username());
    }
}