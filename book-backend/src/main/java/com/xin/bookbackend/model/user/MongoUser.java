package com.xin.bookbackend.model.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

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

) {
    public MongoUser(String username, String password, String firstname, String lastname, String email) {
        this(null, username, password, firstname, lastname, email);
    }

}
