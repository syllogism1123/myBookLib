package com.xin.bookbackend.model;

public record MongoUserDTO(String id, String username, String password, String firstname, String lastname) {
    public MongoUserDTO(String username, String password, String firstname, String lastname) {
        this(null, username, password, firstname, lastname);
    }

    public MongoUserDTO() {
        this(null, null, null, null, null);
    }
}


