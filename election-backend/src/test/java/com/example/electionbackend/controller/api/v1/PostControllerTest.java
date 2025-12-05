package com.example.electionbackend.controller.api.v1;

import com.example.electionbackend.dto.PostDetailDTO;
import com.example.electionbackend.exception.post.InvalidSearchQueryException;
import com.example.electionbackend.interfaces.IPostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for PostController.
 */
@WebMvcTest(PostController.class)
public class PostControllerTest {
    // MockMvc to simulate HTTP requests
    @Autowired
    private MockMvc mockMvc;

    // Mocked PostService
    @MockitoBean
    private IPostService postService;

    // Sample posts for testing
    private List<PostDetailDTO> posts;

    // Set up sample data before each test
    @BeforeEach
    void setUp() {
        posts = List.of(
                new PostDetailDTO(1, 1, "Title 1", "Content 1", "2025-11-22", "Author 1", 5, 100),
                new PostDetailDTO(2, 2, "Title 2", "Content 2", "2025-11-23", "Author 2", 3, 50),
                new PostDetailDTO(3, 3, "Title 3", "Content 3", "2025-11-24", "Author 3", 7, 75)
        );
    }

    /**
     * Verifies that getting all posts returns HTTP 200 with the correct data.
     * @throws Exception if an error occurs during the test
     */
    @Test
    void shouldReturn200_whenGetAllPosts() throws Exception {
        // Arrange
        Page<PostDetailDTO> page = new PageImpl<>(posts);
        when(postService.getAll(any(Pageable.class), isNull())).thenReturn(page);

        // Act & Assert
        mockMvc.perform(get("/v1/posts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[1].id").value(2))
                .andExpect(jsonPath("$.content[2].id").value(3));
    }

    /**
     * Verifies that a too long search query returns HTTP 400.
     * @throws Exception if an error occurs during the test
     */
    @Test
    void shouldReturn400_whenQueryTooLong() throws Exception {
        // Arrange
        String longQuery = "a".repeat(201);

        when(postService.getAll(any(Pageable.class), eq(longQuery)))
                .thenThrow(new InvalidSearchQueryException("Query too long"));

        // Act & Assert
        mockMvc.perform(get("/v1/posts")
                        .param("query", longQuery))
                .andExpect(status().isBadRequest());
    }

    /**
     * Verifies that getting posts with a search query returns HTTP 200 with the correct data.
     * @throws Exception if an error occurs during the test
     */
    @Test
    void shouldReturn200_whenGetAllPostsWithQuery() throws Exception {
        // Arrange
        String query = "Title 1";
        Page<PostDetailDTO> page = new PageImpl<>(List.of(posts.getFirst()));
        when(postService.getAll(any(Pageable.class), eq(query))).thenReturn(page);

        // Act & Assert
        mockMvc.perform(get("/v1/posts")
                        .param("query", query))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content[0].id").value(1))
                .andExpect(jsonPath("$.content[0].title").value("Title 1"));
    }

    /**
     * Verifies that getting posts with a non-matching search query returns HTTP 200 with an empty list.
     * @throws Exception if an error occurs during the test
     */
    @Test
    void shouldReturnEmptyList_whenQueryNoMatch() throws Exception {
        // Arrange
        String query = "NonExistingTitle";
        Page<PostDetailDTO> emptyPage = Page.empty();
        when(postService.getAll(any(Pageable.class), eq(query))).thenReturn(emptyPage);

        // Act & Assert
        mockMvc.perform(get("/v1/posts")
                        .param("query", query))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isEmpty());
    }

    /**
     * Verifies that getting all posts returns HTTP 200 with an empty list when no posts exist.
     * @throws Exception if an error occurs during the test
     */
    @Test
    void shouldReturnEmptyList_whenNoPostsExist() throws Exception {
        // Arrange
        Page<PostDetailDTO> emptyPage = Page.empty();
        when(postService.getAll(any(Pageable.class), isNull())).thenReturn(emptyPage);

        // Act & Assert
        mockMvc.perform(get("/v1/posts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.content").isEmpty());
    }

}
