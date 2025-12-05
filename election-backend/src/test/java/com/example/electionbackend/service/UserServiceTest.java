package com.example.electionbackend.service;

import com.example.electionbackend.dto.UserDTO;
import com.example.electionbackend.dto.UserResponseDTO;
import com.example.electionbackend.exception.EmailAlreadyExistsException;
import com.example.electionbackend.exception.UserNotFoundException;
import com.example.electionbackend.exception.UsernameAlreadyExistsException;
import com.example.electionbackend.mapper.UserMapper;
import com.example.electionbackend.model.User;
import com.example.electionbackend.repository.interfaces.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    /**
     * Verifies that a user is successfully created when the data is valid.
     */
    @Test
    void shouldCreateUser_whenDataIsValid() {
        UserDTO dto = new UserDTO("Johnny", "john@example.com", "password123");
        User user = new User("Johnny", "john@example.com", "hashedPassword");
        user.setId(1);
        UserResponseDTO response = new UserResponseDTO(1, "Johnny", "john@example.com");

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(null);
        when(userRepository.findByUsername(dto.getUsername())).thenReturn(null);
        when(userMapper.toEntity(eq(dto))).thenReturn(user);
        when(userRepository.save(eq(user))).thenReturn(user);
        when(userMapper.toResponseDTO(user)).thenReturn(response);

        UserResponseDTO result = userService.create(dto);

        assertEquals(1, result.getId());
        assertEquals("Johnny", result.getUsername());
        verify(userRepository).save(eq(user));
    }

    /**
     * Verifies that creating a user throws EmailAlreadyExistsException when the email is already taken.
     */
    @Test
    void shouldThrowException_whenEmailAlreadyExists() {
        UserDTO dto = new UserDTO("Johnny", "john@example.com", "password123");

        when(userRepository.findByEmail(dto.getEmail())).thenReturn(new User());

        assertThrows(EmailAlreadyExistsException.class, () -> userService.create(dto));
    }

    /**
     * Verifies that creating a user throws UsernameAlreadyExistsException when the username is already taken.
     */
    @Test
    void shouldThrowException_whenUsernameAlreadyExists() {
        UserDTO dto = new UserDTO("Johnny", "john@example.com", "password123");

        when(userRepository.findByUsername(dto.getUsername())).thenReturn(new User());

        assertThrows(UsernameAlreadyExistsException.class, () -> userService.create(dto));
    }

    /**
     * Verifies that getAllUsers returns a list of UserResponseDTO objects when users exist.
     */
    @Test
    void shouldReturnUserList_whenUsersExist() {
        User u1 = new User("Johnny", "john@example.com", "pw");
        u1.setId(1);

        User u2 = new User("Anna", "anna@example.com", "pw");
        u2.setId(2);

        when(userRepository.findAll()).thenReturn(List.of(u1, u2));
        when(userMapper.toResponseDTO(any(User.class)))
                .thenAnswer(inv -> {
                    User u = inv.getArgument(0);
                    return new UserResponseDTO(u.getId(), u.getUsername(), u.getEmail());
                });

        List<UserResponseDTO> result = userService.getAllUsers();

        assertEquals(2, result.size());
        assertEquals("Johnny", result.get(0).getUsername());
        assertEquals("Anna", result.get(1).getUsername());
    }

    /**
     * Verifies that getAllUsers returns an empty list when no users exist.
     */
    @Test
    void shouldReturnEmptyList_whenNoUsersExist() {
        when(userRepository.findAll()).thenReturn(Collections.emptyList());

        List<UserResponseDTO> result = userService.getAllUsers();

        assertTrue(result.isEmpty());
    }

    /**
     * Verifies that delete removes the user when the user exists.
     */
    @Test
    void shouldDeleteUser_whenUserExists() {
        User user = new User("Johnny", "john@example.com", "pw");

        when(userRepository.findById(1)).thenReturn(user);

        userService.delete(1);

        verify(userRepository).delete(user);
    }

    /**
     * Verifies that delete throws UserNotFoundException when the user does not exist.
     */
    @Test
    void shouldThrowException_whenDeletingNonExistingUser() {
        when(userRepository.findById(1)).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> userService.delete(1));
    }

    /**
     * Verifies that findById returns a UserResponseDTO when the user exists.
     */
    @Test
    void shouldReturnUserResponseDTO_whenUserExists() {
        User user = new User("Johnny", "john@example.com", "pw");
        user.setId(1);

        UserResponseDTO dto = new UserResponseDTO(1, "Johnny", "john@example.com");

        when(userRepository.findById(1)).thenReturn(user);
        when(userMapper.toResponseDTO(user)).thenReturn(dto);

        UserResponseDTO result = userService.findById(1);

        assertEquals("Johnny", result.getUsername());
    }

    /**
     * Verifies that findById throws UserNotFoundException when the user does not exist.
     */
    @Test
    void shouldThrowException_whenUserDoesNotExist() {
        when(userRepository.findById(1)).thenReturn(null);

        assertThrows(UserNotFoundException.class, () -> userService.findById(1));
    }
}
