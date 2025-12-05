package com.example.electionbackend.exception.municipality;

/**
 * Exception thrown when a Municipality with the specified ID is not found.
 */
public class MunicipalityNotFoundException extends RuntimeException {
    public MunicipalityNotFoundException(int id) {
        super("Municipality with ID " + id + " not found.");
    }
}
