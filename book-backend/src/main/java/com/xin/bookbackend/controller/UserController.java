package com.xin.bookbackend.controller;

import com.xin.bookbackend.model.request.ChangePasswordRequest;
import com.xin.bookbackend.model.user.MongoUser;
import com.xin.bookbackend.model.user.MongoUserDTO;
import com.xin.bookbackend.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://mybooklibraryapp.fly.dev/"}, allowCredentials = "true")
@RequestMapping("/api/users")

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public String login() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
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
    public ResponseEntity<MongoUser> loadMongoUserByName(@PathVariable String username) {
        return new ResponseEntity<>(userService.findUserByUsername(username), HttpStatus.OK);
    }

    @PutMapping("/{username}")
    public ResponseEntity<MongoUser> updateMongoUser(@PathVariable String username, @RequestBody MongoUserDTO mongoUserDTO) {
        return new ResponseEntity<>(userService.updateMongoUser(username, mongoUserDTO), HttpStatus.OK);
    }

    @PostMapping("/changePassword")
    public ResponseEntity<MongoUser> changePassword(@RequestBody ChangePasswordRequest request) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return new ResponseEntity<>(userService.changePassword(username, request), HttpStatus.OK);
    }


    @PostMapping("/logout")
    public void logout(HttpSession httpSession) {
        httpSession.invalidate();
        SecurityContextHolder.clearContext();
    }

}
