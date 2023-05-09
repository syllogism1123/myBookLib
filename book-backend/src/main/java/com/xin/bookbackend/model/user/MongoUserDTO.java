package com.xin.bookbackend.model.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record MongoUserDTO(
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
) {
    public MongoUserDTO(String username, String password, String firstname, String lastname, String email) {
        this(null, username, password, firstname, lastname, email);
    }

    public MongoUserDTO() {
        this(null, null, null, null, null, null);
    }
}


