package com.xin.bookbackend.model.user;

import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class MongoUserTest {

    @Test
    void testMongoUserConstructorAndGetters() {
        String id = "123";
        String username = "testUser";
        String password = "testPassword";
        String firstname = "John";
        String lastname = "Doe";
        String email = "test@example.com";

        MongoUser user = new MongoUser(id, username, password, firstname, lastname, email);

        assertEquals(id, user.id());
        assertEquals(username, user.username());
        assertEquals(password, user.getPassword());
        assertEquals(firstname, user.firstname());
        assertEquals(lastname, user.lastname());
        assertEquals(email, user.email());
    }

    @Test
    void testMongoUserWithPassword() {
        String id = "123";
        String username = "testUser";
        String password = "testPassword";
        String firstname = "John";
        String lastname = "Doe";
        String email = "test@example.com";
        String newPassword = "newPassword";

        MongoUser user = new MongoUser(id, username, password, firstname, lastname, email);
        MongoUser updatedUser = user.withPassword(newPassword);


        assertEquals(username, updatedUser.getUsername());
        assertEquals(newPassword, updatedUser.getPassword());
        assertEquals(firstname, updatedUser.firstname());
        assertEquals(lastname, updatedUser.lastname());
        assertEquals(email, updatedUser.email());
    }

    @Test
    void testMongoUserWithId() {
        String id = "123";
        String username = "testUser";
        String password = "testPassword";
        String firstname = "John";
        String lastname = "Doe";
        String email = "test@example.com";
        String newId = "456";

        MongoUser user = new MongoUser(id, username, password, firstname, lastname, email);
        MongoUser updatedUser = user.withId(newId);

        assertEquals(newId, updatedUser.id());
        assertEquals(username, updatedUser.getUsername());
        assertEquals(password, updatedUser.getPassword());
        assertEquals(firstname, updatedUser.firstname());
        assertEquals(lastname, updatedUser.lastname());
        assertEquals(email, updatedUser.email());
    }

    @Test
    void testMongoUserAuthorities() {
        MongoUser user = new MongoUser("testUser", "testPassword", "John", "Doe", "test@example.com");
        Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

        assertNotNull(authorities);
        assertTrue(authorities.isEmpty());
    }

    @Test
    void testMongoUserAccountStatusMethods() {
        MongoUser user = new MongoUser("testUser", "testPassword", "John", "Doe", "test@example.com");

        assertTrue(user.isAccountNonExpired());
        assertTrue(user.isAccountNonLocked());
        assertTrue(user.isCredentialsNonExpired());
        assertTrue(user.isEnabled());
    }
}
