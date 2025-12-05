package com.example.electionbackend.exception;


/**
 * Exception thrown when a user attempts to register with an email address that is already present in the database.
 */
public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String email) {
        super("Email already exists: " + email);
    }
}
