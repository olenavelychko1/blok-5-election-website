package com.example.electionbackend.service;

import com.example.electionbackend.dto.PostDetailDTO;
import com.example.electionbackend.exception.post.InvalidSearchQueryException;
import com.example.electionbackend.mapper.PostMapper;
import com.example.electionbackend.model.Post;
import com.example.electionbackend.model.User;
import com.example.electionbackend.repository.interfaces.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {
    @InjectMocks
    private PostService postService;

    @Mock
    private PostRepository postRepository;

    @Mock
    private PostMapper postMapper;

    private User user;
    private Post post;
    private PostDetailDTO postDetailDTO;

    /**
     * Sets up test data before each test case
     */
    @BeforeEach
    void setup() {
        user = new User("mai", "mai@hva.nl", "lol123");
        user.setId(1);

        post = new Post(1, "Title Example", "Content Example", LocalDateTime.now());
        post.setUser(user);

        postDetailDTO = new PostDetailDTO(
                1,
                1,
                "Title Example",
                "Content Example",
                LocalDateTime.now().toString(),
                "mai",
                0,
                0
        );
    }

    /**
     * Verifies that getAll returns a paged list of posts when posts exist
     */
    // java
    @Test
    void getAll_shouldReturnPagedPosts_whenPostsExist() {
        // Arrange
        List<Post> posts = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Post p = new Post(i, "Title " + i, "Content " + i, LocalDateTime.now());
            p.setUser(user);
            posts.add(p);
        }

        Pageable pageable = PageRequest.of(0, 3);
        when(postRepository.findAll()).thenReturn(posts);
        when(postMapper.toPostDetailDTO(any(Post.class), anyInt(), anyInt()))
                .thenAnswer(invocation -> {
                    Post p = invocation.getArgument(0);
                    return new PostDetailDTO(
                            p.getId(),
                            p.getUser().getId(),
                            p.getTitle(),
                            p.getContent(),
                            p.getCreatedAt().toString(),
                            p.getUser().getUsername(),
                            0,
                            0
                    );
                });

        // Act
        Page<PostDetailDTO> result = postService.getAll(pageable, null);

        // Assert
        assertEquals(5, result.getTotalElements());
        assertEquals(3, result.getContent().size());
        verify(postRepository, times(1)).findAll();
    }


    /**
     * Verifies that getAll returns an empty page when no posts exist
     */
    @Test
    void getAll_shouldReturnEmptyPage_whenNoPostsExist() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 3);
        when(postRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        Page<PostDetailDTO> result = postService.getAll(pageable, null);

        // Assert
        assertEquals(0, result.getTotalElements());
        assertEquals(0, result.getContent().size());
        verify(postRepository, times(1)).findAll();
    }

    /**
     * Verifies that getAll searches by query when a query is provided
     */
    @Test
    void getAll_shouldSearchByQuery_whenQueryProvided() {
        // Arrange
        String query = "Title 1";
        List<Post> posts = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Post p = new Post(i, "Title " + i, "Content " + i, LocalDateTime.now());
            p.setUser(user);
            posts.add(p);
        }

        Pageable pageable = PageRequest.of(0, 5);

        when(postRepository.findByQuery(query))
                .thenReturn(new ArrayList<>(List.of(posts.get(0))));
        when(postMapper.toPostDetailDTO(any(Post.class), anyInt(), anyInt()))
                .thenAnswer(invocation -> {
                    Post p = invocation.getArgument(0);
                    return new PostDetailDTO(
                            p.getId(),
                            p.getUser().getId(),
                            p.getTitle(),
                            p.getContent(),
                            p.getCreatedAt().toString(),
                            p.getUser().getUsername(),
                            0,
                            0
                    );
                });

        // Act
        Page<PostDetailDTO> result = postService.getAll(pageable, query);

        // Assert
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());
        assertEquals(query, result.getContent().get(0).getTitle());

        verify(postRepository, times(1)).findByQuery(query);
        verify(postRepository, never()).findAll();
    }

    /**
     * Verifies that getAll throws an exception when the search query is too long
     */
    @Test
    void getAll_shouldThrowException_whenQueryTooLong() {
        // Arrange
        String longQuery = "a".repeat(201);
        Pageable pageable = PageRequest.of(0, 10);

        // Act & Assert
        assertThrows(InvalidSearchQueryException.class, () -> postService.getAll(pageable, longQuery));
        verify(postRepository, never()).findAll();
        verify(postRepository, never()).findByQuery(anyString());
    }

    /**
     * Verifies that getAll sorts posts in ascending order
     */
    @Test
    void getAll_shouldSortPostsAscending_whenSortIsAsc() {
        // Arrange
        List<Post> posts = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        // Post are ordered from oldest to newest
        for (int i = 1; i <= 3; i++) {
            Post p = new Post(i, "Title " + i, "Content " + i, now.minusDays(3 - i));
            p.setUser(user);
            posts.add(p);
        }

        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Order.asc("createdAt")));
        when(postRepository.findAll()).thenReturn(posts);
        when(postMapper.toPostDetailDTO(any(Post.class), anyInt(), anyInt()))
                .thenAnswer(invocation -> {
                    Post p = invocation.getArgument(0);
                    return new PostDetailDTO(
                            p.getId(),
                            p.getUser().getId(),
                            p.getTitle(),
                            p.getContent(),
                            p.getCreatedAt().toString(),
                            p.getUser().getUsername(),
                            0,
                            0
                    );
                });

        // Act
        Page<PostDetailDTO> result = postService.getAll(pageable, null);

        // Assert
        assertEquals(3, result.getContent().size());
        // Old post shoudl be first
        assertEquals(1, result.getContent().get(0).getId());
        verify(postRepository, times(1)).findAll();
    }

    /**
     * Verifies that getAll sorts posts in descending order
     */
    @Test
    void getAll_shouldSortPostsDescending_whenSortIsDesc() {
        // Arrange
        List<Post> posts = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        // Post are ordered from oldest to newest
        for (int i = 1; i <= 3; i++) {
            Post p = new Post(i, "Title " + i, "Content " + i, now.minusDays(3 - i));
            p.setUser(user);
            posts.add(p);
        }
        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Order.desc("createdAt")));
        when(postRepository.findAll()).thenReturn(posts);
        when(postMapper.toPostDetailDTO(any(Post.class), anyInt(), anyInt()))
                .thenAnswer(invocation -> {
                    Post p = invocation.getArgument(0);
                    return new PostDetailDTO(
                            p.getId(),
                            p.getUser().getId(),
                            p.getTitle(),
                            p.getContent(),
                            p.getCreatedAt().toString(),
                            p.getUser().getUsername(),
                            0,
                            0
                    );
                });
        // Act
        Page<PostDetailDTO> result = postService.getAll(pageable, null);
        // Assert
        assertEquals(3, result.getContent().size());
        // Newest post should be first
        assertEquals(3, result.getContent().get(0).getId());
        verify(postRepository, times(1)).findAll();
    }

    /**
     * Verifies that getAll uses cache on second call without query
     */
    @Test
    void getAll_shouldUseCache_whenCalledTwiceWithoutQuery() {
        // Arrange
        List<Post> posts = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            Post p = new Post(i, "Title " + i, "Content " + i, LocalDateTime.now());
            p.setUser(user);
            posts.add(p);
        }

        Pageable pageable = PageRequest.of(0, 5);
        when(postRepository.findAll()).thenReturn(posts);
        when(postMapper.toPostDetailDTO(any(Post.class), anyInt(), anyInt()))
                .thenReturn(postDetailDTO);

        // Act
        postService.getAll(pageable, null);
        postService.getAll(pageable, null); // this one does not call the repository

        // Assert
        verify(postRepository, times(1)).findAll();
    }
}
