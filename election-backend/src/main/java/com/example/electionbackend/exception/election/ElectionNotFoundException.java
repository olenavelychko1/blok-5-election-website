package com.example.electionbackend.exception.election;

/**
 * Custom exception thrown when no elections are found.
 */
public class ElectionNotFoundException extends RuntimeException {
    public ElectionNotFoundException(String message) {
        super("Elections not found: " + message);
    }
}
