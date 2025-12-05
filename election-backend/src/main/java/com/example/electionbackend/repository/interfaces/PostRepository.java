package com.example.electionbackend.repository.interfaces;

import com.example.electionbackend.model.Post;

import java.util.List;

/**
 * Repository interface for managing Post entities.
 */
public interface PostRepository {
    Post findById(int id);
    Post save(Post post);
    Post delete(Post post);
    Post deleteById(int id);
    List<Post> findAll();
    List<Post> findByQuery(String query);
}
