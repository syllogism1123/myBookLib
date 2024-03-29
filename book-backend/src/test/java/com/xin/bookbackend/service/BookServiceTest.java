package com.xin.bookbackend.service;

import com.xin.bookbackend.model.book.Book;
import com.xin.bookbackend.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

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
                "2006", 4.5, userId)));
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
                "2006", 4.5, userId);
        when(bookRepo.findById(id)).thenReturn(Optional.of(book));

        Book actual = service.getBookById(id);

        verify(bookRepo).findById(id);
        assertEquals(book, actual);
    }

    @Test
    void addBook_Successfully() {
        String userId = UUID.randomUUID().toString();
        Book book = new Book("5eDWcLzdAcYC",
                "Java von Kopf bis Fuß",
                List.of("Kathy Sierra", "Bert Bates"),
                "O'Reilly Germany",
                "2006", 4.5, userId);

        service.addBook(book, userId);

        verify(bookRepo).save(book);
    }

    @Test
    void addBook_failed() {
        String userId = UUID.randomUUID().toString();
        Book book = new Book("5eDWcLzdAcYC",
                "Java von Kopf bis Fuß",
                List.of("Kathy Sierra", "Bert Bates"),
                "O'Reilly Germany",
                "2006", 4.5, userId);

        when(bookRepo.findByUserId(userId)).thenReturn(List.of(book));


        assertThrows(IllegalArgumentException.class, () ->
                service.addBook(book, userId));
    }


    @Test
    void updateBookById_Successfully() {
        String id = UUID.randomUUID().toString();

        String userId = UUID.randomUUID().toString();
        Book book = new Book("5eDWcLzdAcYC",
                "Java von Kopf bis Fuß",
                List.of("Kathy Sierra", "Bert Bates"),
                "O'Reilly Germany",
                "2006", 4.5, userId);


        String newUserId = UUID.randomUUID().toString();
        Book updatedBook = new Book("5eDWcLzdAcYC",
                "Java von Kopf bis Fuß",
                List.of("Kathy Sierra", "Bert Bates"),
                "O'Reilly Germany",
                "2006", 4.5, newUserId);

        when(bookRepo.findById(id)).thenReturn(Optional.of(book));

        service.updateBookById(id, updatedBook);

        verify(bookRepo).findById(id);
        updatedBook = updatedBook.withId(id);
        verify(bookRepo).save(updatedBook);
    }

    @Test
    void updateBookById_failed() {
        String id = UUID.randomUUID().toString();

        String userId = UUID.randomUUID().toString();
        Book book = new Book("5eDWcLzdAcYC",
                "Java von Kopf bis Fuß",
                List.of("Kathy Sierra", "Bert Bates"),
                "O'Reilly Germany",
                "2006", 4.5, userId);


        when(bookRepo.findById(id)).thenReturn(Optional.empty());


        assertThrows(NoSuchElementException.class, () -> service.updateBookById(id, book));
    }


    @Test
    void deleteBookByIdAndUserId() {
        String id = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();

        service.deleteBookByIdAndUserId(id, userId);

        verify(bookRepo).deleteByIdAndUserId(id, userId);

    }

    @Test
    void searchBooks() {
        String query = "java";

        List<Book> books = service.searchBooks(query);

        assertEquals(30, books.size());
    }


    @Test
    void getBookByGoogleBookId() {
        String googleBookId = "5eDWcLzdAcYC";
        Book expected = new Book("5eDWcLzdAcYC",
                "Java von Kopf bis Fuß",
                List.of("Kathy Sierra", "Bert Bates"),
                "O'Reilly Germany",
                "2006", null, 4.5, null);

        Book actual = service.getBookByGoogleBookId(googleBookId);

        assertEquals(expected.authors(), actual.authors());
        assertEquals(expected.title(), actual.title());
        assertEquals(expected.publishedDate(), actual.publishedDate());
        assertEquals(expected.publisher(), actual.publisher());
    }
}
