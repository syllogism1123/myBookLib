package com.xin.bookbackend.model.book;

import java.util.List;

public record BookDTO(String id, String googleBookId, String title, List<String> authors, String publisher,
                      String publishedDate, String description, Double averageRating, String imageUrl, String userId) {

    public BookDTO(String googleBookId, String title, List<String> authors, String publisher,
                   String publishedDate, String description, Double averageRating, String imageUrl) {
        this(null, googleBookId, title, authors, publisher, publishedDate, description, averageRating, imageUrl, null);
    }

    public BookDTO withUserId(String userId) {
        return new BookDTO(null, googleBookId, title, authors, publisher, publishedDate, description, averageRating, imageUrl, userId);
    }

}

