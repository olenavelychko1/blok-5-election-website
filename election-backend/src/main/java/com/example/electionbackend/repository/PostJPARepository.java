package com.example.electionbackend.repository;

import com.example.electionbackend.model.Post;
import com.example.electionbackend.repository.interfaces.PostRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostJPARepository implements PostRepository {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Post findById(int id) {
        return null;
    }

    @Override
    public Post save(Post post) {
        if (post.getId() == 0) {
            entityManager.persist(post);
            return post;
        } else {
            return entityManager.merge(post);
        }
    }

    @Override
    public Post delete(Post post) {
        return null;
    }

    @Override
    public Post deleteById(int id) {
        return null;
    }

    /**
     * Finds all Post entities.
     *
     * @return - list of all Post entities
     */
    @Override
    @Transactional
    public List<Post> findAll() {
        System.out.println("Finding all posts using JPA EntityManager");
        return entityManager.createQuery("SELECT p FROM Post p", Post.class)
                .getResultList();
    }

    /**
     * Finds Post entities matching the given query in their title.
     *
     * @param query - search query
     * @return - list of Post entities matching the query
     */
    @Override
    @Transactional
    public List<Post> findByQuery(String query) {
        // Escape special characters for LIKE query
        query = query.replace("\\", "\\\\")
                .replace("%", "\\%")
                .replace("_", "\\_");

        System.out.println("Finding posts by query using JPA EntityManager: " + query);

        return entityManager.createQuery(
                        "SELECT p FROM Post p WHERE LOWER(p.title) LIKE LOWER(:query) ESCAPE '\\'", Post.class
                ).setParameter("query", "%" + query + "%")
                .getResultList();
    }
}
