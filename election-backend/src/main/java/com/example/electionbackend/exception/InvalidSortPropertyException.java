package com.example.electionbackend.exception;

/**
 * Exception thrown when an invalid sort property is provided.
 */
public class InvalidSortPropertyException extends RuntimeException {
    public InvalidSortPropertyException(String property) {
        super("Invalid sort property: " + property);
    }
}
