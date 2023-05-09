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

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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
        MongoUserDTO mongoUserDTO = new MongoUserDTO(UUID.randomUUID().toString(), "username", "password", "firstname", "lastname", "email@email.com");
        String encodedPassword = encoder.encode(mongoUserDTO.password());
        MongoUser encodedUser = new MongoUser(mongoUserDTO.username(), encodedPassword, mongoUserDTO.firstname(), mongoUserDTO.lastname(), mongoUserDTO.email());
        when(mongoUserRepository.findMongoUserByUsername(mongoUserDTO.username())).thenReturn(Optional.empty());

        userService.createMongoUser(mongoUserDTO);

        verify(mongoUserRepository).save(encodedUser);

    }

    @Test
    void createMongoUser_failed() {
        MongoUserDTO mongoUserDTO = new MongoUserDTO(UUID.randomUUID().toString(), "username", "password", "firstname", "lastname", "email@email.com");
        String encodedPassword = encoder.encode(mongoUserDTO.password());
        MongoUser encodedUser = new MongoUser(mongoUserDTO.username(), encodedPassword, mongoUserDTO.firstname(), mongoUserDTO.lastname(), mongoUserDTO.email());
        when(mongoUserRepository.findMongoUserByUsername(mongoUserDTO.username())).thenReturn(Optional.of(encodedUser));

        assertThrows(IllegalArgumentException.class, () -> userService.createMongoUser(mongoUserDTO));
    }

    @Test
    void findUserByUsername() {
        MongoUser mongoUser = new MongoUser(UUID.randomUUID().toString(), "username", "password", "firstname", "lastname", "email@email.com");
        when(mongoUserRepository.findMongoUserByUsername(mongoUser.username())).thenReturn(Optional.of(mongoUser));

        userService.findUserByUsername(mongoUser.username());

        verify(mongoUserRepository).findMongoUserByUsername(mongoUser.username());
    }


    @Test
    void testUpdateUser_Successfully() {
        String id = UUID.randomUUID().toString();
        MongoUser mongoUser = new MongoUser(id, "username", "password", "firstname", "lastname", "email@email.com");
        when(mongoUserRepository.findMongoUserByUsername(mongoUser.username())).thenReturn(Optional.of(mongoUser));

        MongoUserDTO updateMongoUserDTO = new MongoUserDTO(id, mongoUser.username(), mongoUser.password(), mongoUser.firstname(), mongoUser.lastname(), "newemail@email.com");
        MongoUser updateMongoUser = new MongoUser(id, mongoUser.username(), mongoUser.password(), mongoUser.firstname(), mongoUser.lastname(), "newemail@email.com");
        userService.updateMongoUser(mongoUser.username(), updateMongoUserDTO);

        verify(mongoUserRepository, times(2)).findMongoUserByUsername(mongoUser.username());
        verify(mongoUserRepository).save(updateMongoUser);
    }

    @Test
    void testUpdateUser_failed() {
        String id = UUID.randomUUID().toString();
        String username = "username";
        MongoUser mongoUser = new MongoUser(id, username, "password", "firstname", "lastname", "email@email.com");
        MongoUserDTO updateMongoUserDTO = new MongoUserDTO(id, mongoUser.username(), mongoUser.password(), mongoUser.firstname(), mongoUser.lastname(), "newemail@email.com");

        when(mongoUserRepository.findMongoUserByUsername(mongoUser.username())).thenReturn(Optional.empty());

        assertThrows(NoSuchElementException.class, () -> userService.updateMongoUser(username, updateMongoUserDTO));

    }


    @Test
    void convertMongoUserToMongoUserDTOTest() {

        MongoUser mongoUser = new MongoUser("userId", "username", "password", "firstname", "lastname", "email");

        MongoUserDTO mongoUserDTO = userService.convertMongoUserToMongoUserDTO(mongoUser);


        assertEquals(mongoUser.username(), mongoUserDTO.username());
        assertEquals(mongoUser.password(), mongoUserDTO.password());
        assertEquals(mongoUser.firstname(), mongoUserDTO.firstname());
        assertEquals(mongoUser.lastname(), mongoUserDTO.lastname());
        assertEquals(mongoUser.email(), mongoUserDTO.email());
    }

    @Test
    void testChangePassword() {
        String username = "username";
        String userId = "userId";
        String oldPassword = "password";
        String newPassword = "newPassword";
        MongoUser mongoUser = new MongoUser(userId, username, encoder.encode(oldPassword),
                "firstname", "lastname", "email");

        when(mongoUserRepository.findMongoUserByUsername(username)).thenReturn(Optional.of(mongoUser));
        when(encoder.matches(oldPassword, mongoUser.password())).thenReturn(true);


        MongoUser updatedUser = mongoUser.withPassword(newPassword);
        userService.changePassword(username, oldPassword, newPassword);

        verify(mongoUserRepository).save(updatedUser);

    }


}

