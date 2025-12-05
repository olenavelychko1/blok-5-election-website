package com.example.electionbackend.exception.post;

/**
 * Thrown when a post contains invalid or missing data.
 */
public class InvalidPostException extends RuntimeException {
    public InvalidPostException(String message) {
        super(message);
    }
}
