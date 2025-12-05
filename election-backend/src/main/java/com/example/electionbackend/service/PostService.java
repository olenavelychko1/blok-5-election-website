package com.example.electionbackend.service;

import com.example.electionbackend.dto.PostDTO;
import com.example.electionbackend.dto.PostDetailDTO;
import com.example.electionbackend.exception.UserNotFoundException;
import com.example.electionbackend.exception.post.InvalidPostException;
import com.example.electionbackend.exception.post.InvalidSearchQueryException;
import com.example.electionbackend.exception.post.PostNotFoundException;
import com.example.electionbackend.interfaces.IPostService;
import com.example.electionbackend.mapper.PostMapper;
import com.example.electionbackend.model.Post;
import com.example.electionbackend.model.User;
import com.example.electionbackend.repository.interfaces.PostRepository;
import com.example.electionbackend.util.PaginationUtils;
import com.example.electionbackend.repository.interfaces.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * Service for managing posts.
 */
@Service
public class PostService implements IPostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    /**
     * Cache to store PostDetailDTOs for O(1) retrieval by ID.
     */
    private final Map<Integer, PostDetailDTO> postCache = new ConcurrentHashMap<>();

    public PostService(PostRepository postRepository, UserRepository userRepository,
                       PostMapper postMapper) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.postMapper = postMapper;
    }

    /**
     * Retrieves a detailed view of a post by its ID.
     * Checks the cache first before querying the repository.
     *
     * @param id The unique identifier of the post to retrieve.
     * @return A PostDetailDTO containing post data and aggregated details.
     * @throws PostNotFoundException If no post exists with the given ID.
     */
    @Override
    public PostDetailDTO getById(int id) {
        // Check cache first O(1) lookup
        if (postCache.containsKey(id)) {
            return postCache.get(id);
        }

        Post post = postRepository.findById(id);
        if (post == null) {
            throw new PostNotFoundException(id);
        }

        // TODO: implement real data
        int commentCount = 100;
        int likeCount = 5;

        PostDetailDTO dto = postMapper.toPostDetailDTO(post, commentCount, likeCount);

        postCache.put(id, dto);

        return dto;
    }

    /**
     * Get all posts with optional search query, pagination, and sorting.
     *
     * @param pageable - pagination and sorting information
     * @param query    - optional search query
     * @return - paged list of PostDetailDTOs
     */
    @Transactional
    @Override
    public Page<PostDetailDTO> getAll(Pageable pageable, String query) {
        validateQuery(query);

        // Search query always goes to the db
        if (query != null && !query.isBlank()) {
            return getFilteredPostsFromDatabase(query, pageable);
        }

        // No search, use cache (if empty, load from db)
        List<PostDetailDTO> allDtos = loadAllIntoCacheIfEmpty();

        // Sorting the DTOs
        allDtos = sortDtos(allDtos, pageable.getSort());

        // Pagination
        List<PostDetailDTO> pagedList = PaginationUtils.paginate(allDtos, pageable);

        return new PageImpl<>(pagedList, pageable, allDtos.size());
    }

    /**
     * Helper method to load all posts into the cache if it's empty.
     * Lazy-loads from the database only if the cache is empty.
     *
     * @return - list of all PostDetailDTOs
     */
    private List<PostDetailDTO> loadAllIntoCacheIfEmpty() {
        // If not empty, return from cache
        if (!postCache.isEmpty()) {
            return postCache.values().stream().toList();
        }

        // Else load from db
        List<Post> posts = postRepository.findAll();

        // Populate cache
        posts.stream()
                .map(post -> postMapper.toPostDetailDTO(post, 100, 5))
                .forEach(dto -> postCache.put(dto.getId(), dto));


        // Return the cash
        return postCache.values().stream().toList();

    }

    /**
     * Helper method to get filtered posts from the database based on the search query.
     *
     * @param query    - The search query
     * @param pageable - Pagination information
     * @return - Paged list of PostDetailDTOs matching the query
     */
    private Page<PostDetailDTO> getFilteredPostsFromDatabase(String query, Pageable pageable) {
        List<Post> posts = postRepository.findByQuery(query);

        List<PostDetailDTO> dtos = posts.stream()
                .map(post -> postMapper.toPostDetailDTO(
                        post,
                        100, // TODO: actual comment count
                        5   // TODO: actual likes count
                )).toList();

        List<PostDetailDTO> sorted = sortDtos(dtos, pageable.getSort());
        List<PostDetailDTO> paged = PaginationUtils.paginate(sorted, pageable);

        return new PageImpl<>(paged, pageable, dtos.size());
    }

    /**
     * Helper method to validate the search query.
     * Throws InvalidSearchQueryException if the query exceeds 200 characters.
     *
     * @param query - The search query to validate
     */
    private void validateQuery(String query) {
        if (query != null && query.length() > 200) {
            throw new InvalidSearchQueryException("Maximum length of 200 characters exceeded.");
        }
    }

    /**
     * Helper method to sort the list of posts based on the provided Sort object.
     *
     * @param dtos - List of PostDetailDTOs to sort
     * @param sort - Sort object containing sorting information
     * @return - Sorted list of PostDetailDTOs
     */
    private List<PostDetailDTO> sortDtos(List<PostDetailDTO> dtos, Sort sort) {
        // Gets the sort order for createdAt from the Sort object
        Sort.Order order = sort.getOrderFor("createdAt");
        boolean ascending = order != null && order.isAscending();

        // Compare based on createdAt
        Comparator<PostDetailDTO> comparator = Comparator.comparing(PostDetailDTO::getCreatedAt);

        // Reverse if descending
        if (!ascending) {
            comparator = comparator.reversed();
        }

        return dtos.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    /**
     * Creates a new post based on the provided PostDTO.
     *
     * Validates the input, loads the user, saves the post,
     * and returns the saved post as a DTO.
     *
     * @param dto the incoming post data
     * @return the created post
     */
    @Override
    @Transactional
    public PostDTO create(PostDTO dto) {

        int userId = dto.getUserId();

        if (dto.getTitle() == null || dto.getTitle().isBlank()) {
            throw new InvalidPostException("Title cannot be empty.");
        }

        if (dto.getContent() == null || dto.getContent().isBlank()) {
            throw new InvalidPostException("Content cannot be empty.");
        }

        User user = userRepository.findById(userId);
        if (user == null) {
            throw new UserNotFoundException(userId);
        }

        Post post = postMapper.toEntity(dto);
        post.setCreatedAt(LocalDateTime.now());
        post.setUser(user);

        Post saved = postRepository.save(post);

        postCache.clear();

        return postMapper.toDTO(saved);
    }

    @Override
    public Post update(Post postDTO) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
