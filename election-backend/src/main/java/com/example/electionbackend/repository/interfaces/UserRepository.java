package com.example.electionbackend.repository.interfaces;

import com.example.electionbackend.model.User;

import java.util.List;

/**
 * Repository interface defining the operations for managing User entities.
 */
public interface UserRepository {
    User save(User user);
    User findByEmail(String email);
    User findByUsername(String username);
    List<User> findAll();
    User findById(int id);
    void delete(User user);
}
