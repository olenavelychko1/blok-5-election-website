package com.example.electionbackend.repository;

import com.example.electionbackend.model.Post;
import com.example.electionbackend.model.User;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(PostJPARepository.class)
public class PostJPARepositoryTest {
    @Autowired
    private PostJPARepository postJPARepository;

    @Autowired
    EntityManager entityManager;

    private User user;

    /**
     * Sets up test data before each test case
     */
    @BeforeEach
    void setup() {
        // Clear existing data
        entityManager.createQuery("delete from Post").executeUpdate();

        user = new User("mai", "mai@hva.nl", "lol123");
        entityManager.persist(user);
        entityManager.flush();
    }

    /**
     * Verifies that findAll returns all posts.
     */
    @Test
    void findAll_shouldReturnAllPosts() {
        // Arrange
        Post post1 = new Post(0, "Title 1", "Content 1", LocalDateTime.now());
        post1.setUser(user);
        Post post2 = new Post(0, "Title 2", "Content 2", LocalDateTime.now());
        post2.setUser(user);

        entityManager.persist(post1);
        entityManager.persist(post2);
        entityManager.flush();

        // Act
        List<Post> result = postJPARepository.findAll();

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(p -> p.getTitle().equals("Title 1")));
        assertTrue(result.stream().anyMatch(p -> p.getTitle().equals("Title 2")));
    }

    /**
     * Verifies that findAll returns an empty list when no posts exist.
     */
    @Test
    void findAll_shouldReturnEmptyList_whenNoPostsExist() {
        // Act
        List<Post> result = postJPARepository.findAll();

        // Assert
        assertTrue(result.isEmpty());
    }

    /**
     * Verifies that findByQuery returns matching posts when the query matches.
     */
    @Test
    void findByQuery_shouldReturnMatchingPosts_whenQueryMatches() {
        // Arrange
        Post post1 = new Post(0, "Java", "Content 1", LocalDateTime.now());
        post1.setUser(user);
        Post post2 = new Post(0, "Typescript", "Content 2", LocalDateTime.now());
        post2.setUser(user);
        Post post3 = new Post(0, "Javascript", "Content 3", LocalDateTime.now());
        post3.setUser(user);

        entityManager.persist(post1);
        entityManager.persist(post2);
        entityManager.persist(post3);
        entityManager.flush();

        // Act
        List<Post> result = postJPARepository.findByQuery("java");

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(p -> p.getTitle().equals("Java")));
        assertTrue(result.stream().anyMatch(p -> p.getTitle().equals("Javascript")));
    }
}
