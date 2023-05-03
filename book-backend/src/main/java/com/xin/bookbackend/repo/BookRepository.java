package com.xin.bookbackend.repo;

import com.xin.bookbackend.model.Book;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends MongoRepository<Book, String> {
    List<Book> findByUserId(String userId);

   Optional<Book> findFirstByGoogleBookId(String googleBookId);

    void deleteByIdAndUserId(String id, String userId);
}
