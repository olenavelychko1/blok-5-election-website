package com.example.electionbackend.interfaces;

import com.example.electionbackend.dto.PostDTO;
import com.example.electionbackend.dto.PostDetailDTO;
import com.example.electionbackend.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service interface for managing Post entities.
 */
public interface IPostService {
    PostDetailDTO getById(int id);
    Page<PostDetailDTO> getAll(Pageable pageable, String query);
    PostDTO create(PostDTO postDTO);
    Post update(Post postDTO);
    void delete(int id);
}
