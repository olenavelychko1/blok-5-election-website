package com.example.electionbackend.exception.post;

/**
 * Exception thrown when a search query is invalid.
 */
public class InvalidSearchQueryException extends RuntimeException {
    public InvalidSearchQueryException(String message) {
        super("Invalid search query: " + message);
    }
}
