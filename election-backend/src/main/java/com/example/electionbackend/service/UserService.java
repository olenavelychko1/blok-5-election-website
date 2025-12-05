package com.example.electionbackend.service;

import com.example.electionbackend.dto.UserDTO;
import com.example.electionbackend.dto.UserResponseDTO;
import com.example.electionbackend.exception.*;
import com.example.electionbackend.interfaces.IUserService;
import com.example.electionbackend.mapper.UserMapper;
import com.example.electionbackend.model.User;
import com.example.electionbackend.repository.interfaces.UserRepository;
import jakarta.transaction.Transactional;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing User related operations.
 */
@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    /**
     * Constructs a new UserService with the required dependencies.
     *
     * @param userRepository the user repository used for persistence operations
     * @param userMapper     the mapper used to convert between User and UserDTO
     */
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    /**
     * Creates a new user based on the provided UserDTO.
     *
     * @param userDTO the data used to create the user
     * @return userDTO
     */
    @Override
    @Transactional
    public UserResponseDTO create(UserDTO userDTO) {

        if (userRepository.findByEmail(userDTO.getEmail()) != null) {
            throw new EmailAlreadyExistsException(userDTO.getEmail());
        }

        if (userRepository.findByUsername(userDTO.getUsername()) != null) {
            throw new UsernameAlreadyExistsException(userDTO.getUsername());
        }

        String hashedPassword = BCrypt.hashpw(userDTO.getPassword(), BCrypt.gensalt());
        userDTO.setPassword(hashedPassword);

        User entity = userMapper.toEntity(userDTO);
        User savedUser = userRepository.save(entity);

        return userMapper.toResponseDTO(savedUser);
    }

    /**
     * Retrieves all users and returns them as a list of UserDTOs.
     *
     * @return a list of UserDTOs representing all users
     */
    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toResponseDTO)
                .toList();
    }
    /**
     * Deletes a user with the specified ID.
     * If no user is found, the method currently performs no action.
     *
     * @param id the ID of the user to delete
     */
    @Override
    @Transactional
    public void delete(int id) {
        User user = userRepository.findById(id);

        if (user == null) {
            throw new UserNotFoundException(id);
        }

        userRepository.delete(user);
    }

    /**
     * Finds a user by their ID and returns it as a UserDTO.
     * If no user is found, null is returned.
     *
     * @param id the ID of the user to retrieve
     * @return a UserDTO representing the found user, or null if not found
     */
    @Override
    public UserResponseDTO findById(int id) {
        User user = userRepository.findById(id);

        if (user == null) {
            throw new UserNotFoundException(id);
        }

        return userMapper.toResponseDTO(user);
    }
}
