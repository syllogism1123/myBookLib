package com.xin.bookbackend.model;

import java.util.List;

public record VolumeInfo(String title,
                         List<String> authors,
                         String publisher,
                         String publishedDate,
                         String description,
                         ImageLinks imageLinks) {
}
