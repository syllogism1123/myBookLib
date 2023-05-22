package com.xin.bookbackend.controller;

import com.xin.bookbackend.model.request.AuthenticationRequest;
import com.xin.bookbackend.model.request.AuthenticationResponse;
import com.xin.bookbackend.model.request.ChangePasswordRequest;
import com.xin.bookbackend.model.user.MongoUser;
import com.xin.bookbackend.model.user.MongoUserDTO;
import com.xin.bookbackend.security.JwtService;
import com.xin.bookbackend.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:3000", "https://my-booklibrary.fly.dev"}, allowCredentials = "true")
@RequestMapping("/api/users")

public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserController(UserService userService, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> auth(@RequestBody AuthenticationRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.username(), request.password()
        ));
        var user = userService.findUserByUsername(request.username());
        var jwtToken = jwtService.generateToken(user.username());
        return new ResponseEntity<>(AuthenticationResponse.builder().token(jwtToken).build(), HttpStatus.OK);
    }

    @PostMapping("/signup")
    public ResponseEntity<AuthenticationResponse> createUser(@RequestBody MongoUserDTO mongoUserDTO) {
        MongoUser user = userService.createMongoUser(mongoUserDTO);
        var jwtToken = jwtService.generateToken(user.username());
        return new ResponseEntity<>(AuthenticationResponse.builder().token(jwtToken).build(), HttpStatus.CREATED);
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
