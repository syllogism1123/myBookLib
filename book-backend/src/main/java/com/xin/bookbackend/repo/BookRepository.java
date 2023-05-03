package com.xin.bookbackend.repo;

import com.xin.bookbackend.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findByUserId(String userId);
    void deleteByIdAndUserId(String id, String userId);
}
