package com.xin.bookbackend.controller;

import com.xin.bookbackend.model.request.ChangePasswordRequest;
import com.xin.bookbackend.model.request.LoginRequest;
import com.xin.bookbackend.model.user.MongoUser;
import com.xin.bookbackend.model.user.MongoUserDTO;
import com.xin.bookbackend.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://my-booklibrary.fly.dev"}, allowCredentials = "true")
@RequestMapping("/api/users")

public class UserController {
    private final UserService userService;
    private final PasswordEncoder encoder;

    public UserController(UserService userService, PasswordEncoder encoder) {
        this.userService = userService;
        this.encoder = encoder;
    }


    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginForm) {
        String username = loginForm.username();
        String password = loginForm.password();

        if (userService.findUserByUsername(username) != null) {
            MongoUser user = userService.findUserByUsername(username);
            if (encoder.matches(password, user.password())) {
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/me")
    public String getMe() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    @PostMapping("/signup")
    public ResponseEntity<MongoUserDTO> createUser(@RequestBody MongoUserDTO mongoUserDTO) {
        MongoUser mongoUser = userService.createMongoUser(mongoUserDTO);
        return new ResponseEntity<>(userService.convertMongoUserToMongoUserDTO(mongoUser), HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public ResponseEntity<MongoUserDTO> loadMongoUserByName(@PathVariable String username) {
        MongoUser mongoUser = userService.findUserByUsername(username);
        return new ResponseEntity<>(userService.convertMongoUserToMongoUserDTO(mongoUser), HttpStatus.OK);
    }

    @PutMapping("/{username}")
    public ResponseEntity<MongoUserDTO> updateMongoUser(@PathVariable String username, @RequestBody MongoUserDTO mongoUserDTO) {
        MongoUser mongoUser = userService.updateMongoUser(username, mongoUserDTO);
        return new ResponseEntity<>(userService.convertMongoUserToMongoUserDTO(mongoUser), HttpStatus.OK);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<MongoUserDTO> changePassword(@RequestBody ChangePasswordRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        MongoUser mongoUser = userService.changePassword(username, request);
        return new ResponseEntity<>(userService.convertMongoUserToMongoUserDTO(mongoUser), HttpStatus.OK);
    }


    @PostMapping("/logout")
    public void logout(HttpSession httpSession) {
        httpSession.invalidate();
        SecurityContextHolder.clearContext();
    }

}
