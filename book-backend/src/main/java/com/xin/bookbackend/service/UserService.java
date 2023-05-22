package com.xin.bookbackend.service;

import com.xin.bookbackend.model.request.ChangePasswordRequest;
import com.xin.bookbackend.model.user.MongoUser;
import com.xin.bookbackend.model.user.MongoUserDTO;
import com.xin.bookbackend.repository.MongoUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserService {
    private final MongoUserRepository mongoUserRepository;
    private final PasswordEncoder encoder;

    public UserService(MongoUserRepository mongoUserRepository, PasswordEncoder encoder) {
        this.mongoUserRepository = mongoUserRepository;
        this.encoder = encoder;
    }

    public MongoUser createMongoUser(MongoUserDTO mongoUserDTO) {

        if (mongoUserRepository.findMongoUserByUsername(mongoUserDTO.username()).isPresent()) {
            throw new IllegalArgumentException("The username already exists.");
        } else {
            if (mongoUserRepository.findMongoUserByEmail(mongoUserDTO.email()).isPresent()) {
                throw new IllegalArgumentException("The email address already exists.");
            }

            String encodedPassword = encoder.encode(mongoUserDTO.password());
            MongoUser encodedUser = new MongoUser(mongoUserDTO.username(),
                    encodedPassword, mongoUserDTO.firstname(), mongoUserDTO.lastname(),
                    mongoUserDTO.email());
            return mongoUserRepository.save(encodedUser);

        }
    }

    public MongoUser findUserByUsername(String username) {
        return mongoUserRepository.findMongoUserByUsername(username).orElseThrow(NoSuchElementException::new);
    }

    public MongoUser updateMongoUser(String username, MongoUserDTO mongoUserDTO) {

        if (findUserByUsername(username) != null) {
            MongoUser user = findUserByUsername(username);
            MongoUser encodedUser = new MongoUser(user.id(), mongoUserDTO.username(), mongoUserDTO.password(), mongoUserDTO.firstname(), mongoUserDTO.lastname(), mongoUserDTO.email());
            return mongoUserRepository.save(encodedUser);

        } else {
            throw new NoSuchElementException("User not found");
        }
    }


    public MongoUser changePassword(String username, ChangePasswordRequest request) {

        if (findUserByUsername(username) != null) {
            MongoUser user = findUserByUsername(username);

            if (encoder.matches(request.oldPassword(), user.password())) {
                MongoUser updatedUser = user.withPassword(encoder.encode(request.newPassword()));
                updatedUser = updatedUser.withId(user.id());
                return mongoUserRepository.save(updatedUser);
            } else {
                throw new IllegalArgumentException("Invalid old password");
            }
        } else {
            throw new NoSuchElementException("User not found");
        }
    }


    public MongoUserDTO convertMongoUserToMongoUserDTO(MongoUser mongoUser) {
        return new MongoUserDTO(
                mongoUser.id(),
                mongoUser.username(),
                mongoUser.password(),
                mongoUser.firstname(),
                mongoUser.lastname(),
                mongoUser.email()
        );
    }
}
