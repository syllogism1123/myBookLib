package com.xin.bookbackend.service;


import com.xin.bookbackend.model.user.MongoUser;
import com.xin.bookbackend.repository.MongoUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class MongoUserDetailsService implements UserDetailsService {
    private final MongoUserRepository mongoUserRepository;

    public MongoUserDetailsService(MongoUserRepository mongoUserRepository) {
        this.mongoUserRepository = mongoUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MongoUser mongoUser = mongoUserRepository.findMongoUserByUsername(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("User with name: " + username + " not found!"));
        return new User(mongoUser.username(), mongoUser.password(), Collections.emptyList());

    }

}
