package com.xin.bookbackend.controller;

import com.xin.bookbackend.model.MongoUser;
import com.xin.bookbackend.model.MongoUserDTO;
import com.xin.bookbackend.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
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
        return new ResponseEntity<>(convertMongoUserToMongoUserDTO(mongoUser), HttpStatus.CREATED);
    }

    @GetMapping("/{username}")
    public MongoUserDTO loadMongoUserByName(@PathVariable String username) {
        return convertMongoUserToMongoUserDTO(userService.findUserByUsername(username));
    }


    @PostMapping("/logout")
    public void logout(HttpSession httpSession) {
        httpSession.invalidate();
        SecurityContextHolder.clearContext();
    }


    private MongoUserDTO convertMongoUserToMongoUserDTO(MongoUser mongoUser) {
        MongoUserDTO mongoUserDTO = new MongoUserDTO();
        BeanUtils.copyProperties(mongoUser, mongoUserDTO);
        return mongoUserDTO;
    }

}
