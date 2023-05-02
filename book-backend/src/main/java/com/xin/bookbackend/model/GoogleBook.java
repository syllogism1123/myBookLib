package com.xin.bookbackend.model;

import java.util.List;

public record GoogleBook(String id, VolumeInfo volumeInfo) {

    public Book toBook() {
        String id = this.id();
        String tittle = volumeInfo.title();
        List<String> authors = volumeInfo.authors();
        String publisher = volumeInfo.publisher();
        String publishedDate = volumeInfo.publishedDate();
        String description = volumeInfo.description();
        String imageUrl = volumeInfo.imageLinks() != null ? volumeInfo.imageLinks().thumbnail() : null;
        return new Book(id, tittle, authors, publisher, publishedDate, description, imageUrl);
    }
}
