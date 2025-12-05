package com.example.electionbackend.mapper;

import com.example.electionbackend.dto.UserDTO;
import com.example.electionbackend.dto.UserResponseDTO;
import com.example.electionbackend.model.User;
import org.springframework.stereotype.Component;

/**
 * The user mapper class
 */
@Component
public class UserMapper {

    /**
     * Converts a UserDTO to a User entity.
     *
     * @param dto the UserDTO to convert
     * @return a User entity containing the data from the given DTO
     */
    public User toEntity(UserDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }

    /**
     * Maps a User entity to a safe UserResponseDTO.
     */
    public UserResponseDTO toResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
}
