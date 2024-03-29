package com.xin.bookbackend.model.book;

import java.util.List;

public record VolumeInfo(String title,
                         List<String> authors,
                         String publisher,
                         String publishedDate,
                         String description,
                         Double averageRating,
                         ImageLinks imageLinks) {
}
