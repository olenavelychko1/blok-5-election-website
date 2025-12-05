package com.example.electionbackend.exception.votes;

/**
 * Exception thrown when a Party Vote is not found.
 */
public class PartyVoteNotFoundException extends RuntimeException {
    public PartyVoteNotFoundException(String message) {
        super("Party vote(s) not found: " + message);
    }
}
