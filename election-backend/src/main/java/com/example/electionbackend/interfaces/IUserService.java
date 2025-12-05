package com.example.electionbackend.interfaces;

import com.example.electionbackend.dto.UserDTO;
import com.example.electionbackend.dto.UserResponseDTO;

import java.util.List;

/**
 * Service interface that defines the operations for managing User entities
 */
public interface IUserService {
    UserResponseDTO create(UserDTO userDTO);
    void delete(int id);
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO findById(int id);
}
