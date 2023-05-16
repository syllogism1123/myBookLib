package com.xin.bookbackend.controller;

import com.xin.bookbackend.model.book.Book;
import com.xin.bookbackend.model.book.BookDTO;
import com.xin.bookbackend.model.user.MongoUser;
import com.xin.bookbackend.service.BookService;
import com.xin.bookbackend.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = {"http://localhost:3000", "https://my-booklibraryapp.fly.dev/"}, allowCredentials = "true")
public class BookController {
    private final BookService bookService;
    private final UserService userService;

    public BookController(BookService bookService, UserService userService) {
        this.bookService = bookService;
        this.userService = userService;
    }


    @GetMapping("/search")
    public List<BookDTO> search(@RequestParam String query) {
        return convertBookListToBookDTOList(bookService.searchBooks(query));
    }

    @GetMapping("/search/{googleBookId}")
    public BookDTO findBookByGoogleBookId(@PathVariable String googleBookId) {
        return convertBookToBookDTO(bookService.getBookByGoogleBookId(googleBookId));
    }


    @GetMapping()
    public ResponseEntity<List<Book>> getAllBooks() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        MongoUser user = userService.findUserByUsername(username);
        String userId = user.id();
        return new ResponseEntity<>(bookService.getAllBooksByUserId(userId), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<BookDTO> addBook(@RequestBody BookDTO bookDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        MongoUser user = userService.findUserByUsername(username);
        String userId = user.id();
        Book book = convertBookDTOToBook(bookDTO);
        book = book.withUserId(userId);
        bookService.addBook(book, userId);
        return new ResponseEntity<>(bookDTO, HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable String id) {
        return new ResponseEntity<>(bookService.getBookById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookDTO> updateBookById(@PathVariable String id, @RequestBody BookDTO updatedBookDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        MongoUser user = userService.findUserByUsername(username);
        if (bookService.getBookById(id) != null) {
            String userId = user.id();
            Book updatedBook = convertBookDTOToBook(updatedBookDTO);
            updatedBook = updatedBook.withUserId(userId);
            bookService.updateBookById(id, updatedBook);
            return new ResponseEntity<>(updatedBookDTO, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The id in the url does not match the request body's id");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBookById(@PathVariable String id) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        MongoUser user = userService.findUserByUsername(username);
        String userId = user.id();
        bookService.deleteBookByIdAndUserId(id, userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Book convertBookDTOToBook(BookDTO bookDTO) {
        return new Book(bookDTO.googleBookId(), bookDTO.title(), bookDTO.authors(), bookDTO.publisher(),
                bookDTO.publishedDate(), bookDTO.description(), bookDTO.averageRating(), bookDTO.imageUrl());
    }

    private BookDTO convertBookToBookDTO(Book book) {
        return new BookDTO(book.googleBookId(), book.title(), book.authors(), book.publisher(),
                book.publishedDate(), book.description(), book.averageRating(), book.imageUrl());
    }

    private List<BookDTO> convertBookListToBookDTOList(List<Book> bookList) {
        return bookList.stream().map(this::convertBookToBookDTO).toList();
    }

}
