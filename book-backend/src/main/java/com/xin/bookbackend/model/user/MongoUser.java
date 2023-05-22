package com.xin.bookbackend.model.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Document("mongoUsers")
public record MongoUser(
        @MongoId
        String id,
        @NotBlank
        @Size(min = 3, max = 16)
        String username,
        @NotBlank
        @Size(min = 3, max = 32)
        String password,

        @NotBlank
        @Size(min = 3, max = 16)
        String firstname,
        @NotBlank
        @Size(min = 3, max = 16)
        String lastname,
        @NotBlank
        @Email
        String email

) implements UserDetails {
    public MongoUser(String username, String password, String firstname, String lastname, String email) {
        this(null, username, password, firstname, lastname, email);
    }

    public MongoUser withPassword(String newPassword) {
        return new MongoUser(null, username, newPassword, firstname, lastname, email);
    }

    public MongoUser withId(String id) {
        return new MongoUser(id, username, password, firstname, lastname, email);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
