package com.example.electionbackend.exception.post;

/**
 * Thrown when a post is not found
 */
public class PostNotFoundException extends RuntimeException {
    public PostNotFoundException(int id) {
        super("Post not found with id: " + id);
    }
}