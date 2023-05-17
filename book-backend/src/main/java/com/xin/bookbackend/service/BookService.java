package com.xin.bookbackend.service;

import com.xin.bookbackend.model.book.Book;
import com.xin.bookbackend.model.book.GoogleBook;
import com.xin.bookbackend.model.book.GoogleBookResponse;
import com.xin.bookbackend.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class BookService {
    private final WebClient webClient;
    private final BookRepository bookRepos;
    @Value("${google.api.key}")
    private String apiKey;
    private static final Logger log = LoggerFactory.getLogger(BookService.class);

    public BookService(BookRepository bookRepository) {
        this.webClient = WebClient.builder()
                .baseUrl("https://www.googleapis.com/books/v1/volumes")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultUriVariables(Collections.singletonMap("url", "https://www.googleapis.com"))
                .defaultUriVariables(Collections.singletonMap("key", apiKey))
                .build();
        this.bookRepos = bookRepository;
    }

    public List<Book> searchBooks(String query) {
        ResponseEntity<GoogleBookResponse> responseEntity = webClient.get().
                uri("?q=" + query + "&maxResults=30").
                retrieve().
                toEntity(GoogleBookResponse.class).
                block();

        var body = Objects.requireNonNull(responseEntity).getBody();
        return Objects.requireNonNull(body).items().stream().map(GoogleBook::toBook).toList();
    }


    public Book getBookByGoogleBookId(String googleBookId) {
        ResponseEntity<GoogleBook> responseEntity = webClient.get().
                uri("/" + googleBookId).
                retrieve().
                toEntity(GoogleBook.class).
                block();
        var body = Objects.requireNonNull(responseEntity).getBody();
        return Objects.requireNonNull(body).toBook();
    }


    public List<Book> getAllBooksByUserId(String id) {
        return bookRepos.findByUserId(id);
    }


    public Book addBook(Book book, String userId) {
        Book bookToSave = book.withUserId(userId);
        if (getAllBooksByUserId(userId).stream().
                anyMatch(b -> b.googleBookId().equals(bookToSave.googleBookId()))) {
            log.warn("This Book is already in your Library!");
            throw new IllegalArgumentException("This Book is already in your Library!");
        }
        return bookRepos.save(bookToSave);
    }

    public Book getBookById(String id) {
        return bookRepos.findById(id).orElseThrow();
    }

    public Book updateBookById(String id, Book newBook) {
        if (getBookById(id) != null) {
            newBook = newBook.withId(id);
            return bookRepos.save(newBook);
        } else {
            throw new NoSuchElementException("Book not found!");
        }
    }

    public void deleteBookByIdAndUserId(String id, String userId) {
        bookRepos.deleteByIdAndUserId(id, userId);
    }


}
