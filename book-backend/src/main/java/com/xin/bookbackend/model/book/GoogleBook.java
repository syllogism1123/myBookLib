package com.xin.bookbackend.model.book;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.List;
import java.util.Random;

public record GoogleBook(String id, VolumeInfo volumeInfo) {

    public Book toBook() throws NoSuchAlgorithmException {
        String id = this.id();
        String tittle = volumeInfo.title();
        List<String> authors = volumeInfo.authors();
        String publisher = volumeInfo.publisher();
        String publishedDate = volumeInfo.publishedDate();
        String description = volumeInfo.description();
        Random r= SecureRandom.getInstanceStrong();
        Double averageRating = volumeInfo.averageRating() == null ? (r.nextDouble() * 5)
                : volumeInfo.averageRating();
        String imageUrl = volumeInfo.imageLinks() != null ? volumeInfo.imageLinks().thumbnail() : null;
        return new Book(id, tittle, authors, publisher, publishedDate, description, averageRating, imageUrl);
    }
}
