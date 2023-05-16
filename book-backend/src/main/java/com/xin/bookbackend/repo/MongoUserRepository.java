package com.xin.bookbackend.repo;

import com.xin.bookbackend.model.user.MongoUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface MongoUserRepository extends MongoRepository<MongoUser, String> {
    Optional<MongoUser> findMongoUserByUsername(String username);
    Optional<MongoUser> findMongoUserByEmail(String email);
}
