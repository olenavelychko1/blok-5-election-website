package com.example.electionbackend.exception;

/**
 * Exception thrown when a requested user cannot be found in the database.
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(int id) {
        super("User not found with id: " + id);
    }
}
