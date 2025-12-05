package com.example.electionbackend.exception.constituency;

/**
 * Exception thrown when a Constituency with the specified ID is not found.
 */
public class ConstituencyNotFoundException extends RuntimeException {
    public ConstituencyNotFoundException(int id) {
        super("Constituency with ID " + id + " not found.");
    }
}
