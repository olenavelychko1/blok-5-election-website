package com.example.electionbackend.mapper;

import com.example.electionbackend.dto.PostDTO;
import com.example.electionbackend.dto.PostDetailDTO;
import com.example.electionbackend.model.Post;
import org.springframework.stereotype.Component;

/**
 * The post mapper class
 */
@Component
public class PostMapper {
    /**
     * Converts a PostDTO object to a Post entity.
     *
     * @param dto - the PostDTO instance containing data to be converted to a Post entity
     * @return - a new Post entity populated with data from the given PostDTO
     */
    public Post toEntity(PostDTO dto) {
        Post post = new Post();
        post.setTitle(dto.getTitle());
        post.setContent(dto.getContent());
        return post;
    }

    /**
     * Converts a Post entity to a PostDTO.
     *
     * @param entity - the Post entity to be converted
     * @return - a new PostDTO populated with data from the given entity
     */
    public PostDTO toDTO(Post entity) {
        return new PostDTO(
                entity.getId(),
                entity.getUser().getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getCreatedAt().toString()
        );
    }

    /**
     * Converts a Post entity to a PostDetailDTO.
     * Contains additional information such as author name, comment count, and likes count.
     *
     * @param entity       - the Post entity to be converted
     * @param commentCount - the number of comments on the post
     * @param likesCount   - the number of likes on the post
     * @return - a new PostDetailDTO populated with data from the given entity and additional information
     */
    public PostDetailDTO toPostDetailDTO(Post entity, int commentCount, int likesCount) {
        return new PostDetailDTO(
                entity.getId(),
                entity.getUser().getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getCreatedAt().toString(),
                entity.getUser().getUsername(),
                likesCount,
                commentCount
        );
    }
}
