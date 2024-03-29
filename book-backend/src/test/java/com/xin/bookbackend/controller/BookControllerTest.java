package com.xin.bookbackend.controller;

import com.xin.bookbackend.model.book.Book;
import com.xin.bookbackend.model.user.MongoUser;
import com.xin.bookbackend.repository.BookRepository;
import com.xin.bookbackend.repository.MongoUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class BookControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
    private JacksonTester<Book> json;
    @MockBean
    private MongoUserRepository userRepository;
    @MockBean
    private BookRepository bookRepository;
    private Book book;
    private String id;
    private MongoUser user;

    @BeforeEach
    void setup() {
        id = UUID.randomUUID().toString();
        String userId = UUID.randomUUID().toString();
        user = new MongoUser(userId, "user", "password", "firstname", "lastname", "email@email.com");
        userRepository.save(user);
        book = new Book(id, "5eDWcLzdAcYC",
                "Java von Kopf bis Fuß",
                List.of("Kathy Sierra", "Bert Bates"),
                "O'Reilly Germany",
                "2006", 4.5, userId);
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void getAllBooks() throws Exception {
        when(userRepository.findMongoUserByUsername("user")).thenReturn(Optional.ofNullable(user));

        mvc.perform(get("/api/books").
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk()).
                andExpect(content().json("""
                        [ ]
                        """));
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void getBookById() throws Exception {
        when(userRepository.findMongoUserByUsername("user")).thenReturn(Optional.ofNullable(user));
        when(bookRepository.findById(id)).thenReturn(Optional.ofNullable(book));

        mvc.perform(get("/api/books/" + id).
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk())
                .andExpect(content().
                        json("""
                                {
                                 "googleBookId": "5eDWcLzdAcYC",
                                 "title": "Java von Kopf bis Fuß",
                                 "authors": [
                                        "Kathy Sierra",
                                        "Bert Bates"
                                    ],
                                 "publisher": "O'Reilly Germany",
                                 "publishedDate": "2006",
                                 "averageRating": 4.5
                                 }
                                 """));
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void addBook() throws Exception {
        when(userRepository.findMongoUserByUsername("user")).thenReturn(Optional.ofNullable(user));

        mvc.perform(post("/api/books").
                        contentType(MediaType.APPLICATION_JSON).
                        content(json.write(book).getJson()).with(csrf())).
                andExpect(status().isCreated());
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void updateBookById() throws Exception {
        when(userRepository.findMongoUserByUsername("user")).thenReturn(Optional.ofNullable(user));
        when(bookRepository.findById(id)).thenReturn(Optional.ofNullable(book));

        mvc.perform(put("/api/books/" + id).
                        contentType(MediaType.APPLICATION_JSON).
                        content("""
                                {
                                "googleBookId": "5eDWcLzdAcYC",
                                "title": "Java von Kopf bis Fuß",
                                "authors": [
                                       "Kathy Sierra",
                                       "Bert Bates"
                                   ],
                                "publisher": "O'Reilly Germany",
                                "publishedDate": "2006",
                                "averageRating": 4.5
                                }
                                """).with(csrf())).
                andExpect(status().isOk());
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void updateBookById_IdNotFound() throws Exception {
        when(userRepository.findMongoUserByUsername("user")).thenReturn(Optional.ofNullable(user));
        when(bookRepository.findById(id)).thenReturn(Optional.empty());

        mvc.perform(put("/api/books/" + id).
                        contentType(MediaType.APPLICATION_JSON).
                        with(csrf())).
                andExpect(status().isBadRequest());
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void deleteBookById() throws Exception {
        when(userRepository.findMongoUserByUsername("user")).thenReturn(Optional.ofNullable(user));
        when(bookRepository.findById(id)).thenReturn(Optional.ofNullable(book));

        mvc.perform(delete("/api/books/" + id).
                        contentType(MediaType.APPLICATION_JSON).with(csrf())).
                andExpect(status().isOk());
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void search() throws Exception {
        when(userRepository.findMongoUserByUsername("user")).thenReturn(Optional.ofNullable(user));
        String query = "java";

        mvc.perform(get("/api/books/search?query=" + query).
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk());
    }

    @Test
    @DirtiesContext
    @WithMockUser
    void findBookByGoogleBookId() throws Exception {
        when(userRepository.findMongoUserByUsername("user")).thenReturn(Optional.ofNullable(user));
        String googleBookId = "5eDWcLzdAcYC";

        mvc.perform(get("/api/books/search/" + googleBookId).
                        contentType(MediaType.APPLICATION_JSON)).
                andExpect(status().isOk())
                .andExpect(content().
                        json("""
                                {
                                 "googleBookId": "5eDWcLzdAcYC",
                                 "title": "Java von Kopf bis Fuß",
                                 "authors": [
                                        "Kathy Sierra",
                                        "Bert Bates"
                                    ],
                                 "publisher": "O'Reilly Germany",
                                 "publishedDate": "2006",
                                 "averageRating": 4.5
                                 }
                                 """)).
                andExpect(jsonPath("$.googleBookId").isNotEmpty());
    }
}