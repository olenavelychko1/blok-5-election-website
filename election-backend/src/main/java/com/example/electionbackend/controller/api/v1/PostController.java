package com.example.electionbackend.controller.api.v1;

import com.example.electionbackend.dto.PageResponse;
import com.example.electionbackend.dto.PostDTO;
import com.example.electionbackend.dto.PostDetailDTO;
import com.example.electionbackend.interfaces.IPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for managing posts.
 */
@RestController
@RequestMapping("/v1/posts")
public class PostController {
    private final IPostService postService;

    /**
     * Constructor for PostController.
     *
     * @param postService - the post service
     */
    public PostController(IPostService postService) {
        this.postService = postService;
    }

    /**
     * Get all posts
     *
     * @return list of PostDetailDTOs
     */
    @Operation(summary = "Get all posts")
    @GetMapping
    public ResponseEntity<PageResponse<PostDetailDTO>> getAll(
            @PageableDefault(
                    sort = "createdAt",
                    direction = Sort.Direction.DESC) Pageable pageable,
            @RequestParam(required = false) String query) {
        Page<PostDetailDTO> posts = postService.getAll(pageable, query);
        return ResponseEntity.ok(new PageResponse<>(posts));
    }

    /**
     * Creates a new post.
     *
     * @param dto The DTO containing the data for the new post.
     * @return A ResponseEntity with the created PostDTO and HTTP 201 status.
     */
    @Operation(
            summary = "Create a new post",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Post successfully created",
                            content = @Content(schema = @Schema(implementation = PostDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid request body"
                    )
            }
    )
    @PostMapping
    public ResponseEntity<PostDTO> create(@RequestBody PostDTO dto) {
        PostDTO createdPost = postService.create(dto);
        return ResponseEntity.status(201).body(createdPost);
    }

    /**
     * Retrieves a post by its unique identifier.
     *
     * @param id The ID of the post to retrieve.
     * @return A ResponseEntity containing the detailed post information and HTTP 200 status.
     */
    @Operation(
            summary = "Get post by ID",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Post found",
                            content = @Content(schema = @Schema(implementation = PostDetailDTO.class))
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Post not found"
                    )
            }
    )
    @GetMapping("/{id}")
    public ResponseEntity<PostDetailDTO> getById(@PathVariable int id) {
        PostDetailDTO post = postService.getById(id);
        return ResponseEntity.ok(post);
    }

}
