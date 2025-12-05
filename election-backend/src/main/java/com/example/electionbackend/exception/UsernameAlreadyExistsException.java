package com.example.electionbackend.exception;

/**
 * Exception thrown when a user tries to register with a username that is already taken by another user in the database.
 */
public class UsernameAlreadyExistsException extends RuntimeException {
    public UsernameAlreadyExistsException(String username) {
        super("Username already exists: " + username);
    }
}
