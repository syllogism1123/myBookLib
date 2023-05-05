package com.xin.bookbackend.model.book;

import java.util.List;

public record BookDTO(String id, String googleBookId, String title, List<String> authors, String publisher,
                      String publishedDate, String description, Double averageRating, String imageUrl, String userId) {

}

