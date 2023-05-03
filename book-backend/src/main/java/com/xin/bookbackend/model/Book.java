package com.xin.bookbackend.model;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.List;

@Document
public record Book(@MongoId String id, String googleBookId, String title, List<String> authors, String publisher,
                   String publishedDate, String description, String imageUrl, String userId) {

    public Book(String googleBookId, String title, List<String> authors, String publisher,
                String publishedDate, String description, String imageUrl) {
        this(null, googleBookId, title, authors, publisher, publishedDate, description, imageUrl, null);
    }

    public Book(String googleBookId, String title, List<String> authors, String publisher,
                String publishedDate, String userId) {
        this(null, googleBookId, title, authors, publisher, publishedDate, null, null, userId);
    }


    public Book(String id, String googleBookId, String title, List<String> authors, String publisher,
                String publishedDate, String userId) {
        this(id, googleBookId, title, authors, publisher, publishedDate, null, null, userId);
    }


    public Book withUserId(String userId) {
        return new Book(null, googleBookId, title, authors, publisher, publishedDate, description, imageUrl, userId);
    }

}

