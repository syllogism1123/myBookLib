package com.xin.bookbackend.service;

import com.xin.bookbackend.model.Book;
import com.xin.bookbackend.repo.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {
    private BookService service;
    @Mock
    private BookRepository bookRepo;

    @BeforeEach
    void setup() {
        service = new BookService(bookRepo);
    }


    @Test
    void getAllBooks() {
        String userId = UUID.randomUUID().toString();
        List<Book> books = new ArrayList<>(Collections.singleton(new Book("5eDWcLzdAcYC",
                "Java von Kopf bis Fuß",
                List.of("Kathy Sierra", "Bert Bates"),
                "O'Reilly Germany",
                "2006", userId)));
        when(bookRepo.findByUserId(userId)).thenReturn(books);


        List<Book> actual = service.getAllBooksByUserId(userId);

        verify(bookRepo).findByUserId(userId);
        assertEquals(books, actual);
    }

    @Test
    void getBookById() {
        String id = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();

        Book book = new Book("5eDWcLzdAcYC",
                "Java von Kopf bis Fuß",
                List.of("Kathy Sierra", "Bert Bates"),
                "O'Reilly Germany",
                "2006", userId);
        when(bookRepo.findById(id)).thenReturn(Optional.of(book));

        Book actual = service.getBookById(id);

        verify(bookRepo).findById(id);
        assertEquals(book, actual);
    }

    @Test
    void addBook() {
        String userId = UUID.randomUUID().toString();
        Book book = new Book("5eDWcLzdAcYC",
                "Java von Kopf bis Fuß",
                List.of("Kathy Sierra", "Bert Bates"),
                "O'Reilly Germany",
                "2006", userId);

        service.addBook(book, userId);

        verify(bookRepo).save(book);
    }

    @Test
    void updateBookById() {
        String id = UUID.randomUUID().toString();

        String userId = UUID.randomUUID().toString();
        Book book = new Book("5eDWcLzdAcYC",
                "Java von Kopf bis Fuß",
                List.of("Kathy Sierra", "Bert Bates"),
                "O'Reilly Germany",
                "2006", userId);


        String newUserId = UUID.randomUUID().toString();
        Book updatedBook = new Book("5eDWcLzdAcYC",
                "Java von Kopf bis Fuß",
                List.of("Kathy Sierra", "Bert Bates"),
                "O'Reilly Germany",
                "2006", newUserId);

        when(bookRepo.findById(id)).thenReturn(Optional.of(book));

        service.updateBookById(id, updatedBook);

        verify(bookRepo).findById(id);
        verify(bookRepo).save(updatedBook);

    }

    @Test
    void deleteBookByIdAndUserId() {
        String id = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();

        service.deleteBookByIdAndUserId(id, userId);

        verify(bookRepo).deleteByIdAndUserId(id, userId);

    }
}
