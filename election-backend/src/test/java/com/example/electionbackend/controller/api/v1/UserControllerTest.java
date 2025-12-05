package com.example.electionbackend.controller.api.v1;

import com.example.electionbackend.dto.UserDTO;
import com.example.electionbackend.dto.UserResponseDTO;
import com.example.electionbackend.exception.EmailAlreadyExistsException;
import com.example.electionbackend.exception.UserNotFoundException;
import com.example.electionbackend.interfaces.IUserService;
import com.example.electionbackend.exception.GlobalExceptionHandler.GlobalExceptionHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import(GlobalExceptionHandler.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IUserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * Verifies that a user is created successfully and returns HTTP 201.
     */
    @Test
    void shouldReturn201_whenUserIsCreated() throws Exception {
        UserDTO dto = new UserDTO("Johnny", "john@example.com", "password123");
        UserResponseDTO response = new UserResponseDTO(1, "Johnny", "john@example.com");

        when(userService.create(any(UserDTO.class))).thenReturn(response);

        mockMvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.username").value("Johnny"));
    }

    /**
     * Verifies that creating a user with an existing email returns HTTP 400.
     */
    @Test
    void shouldReturn400_whenEmailAlreadyExists() throws Exception {
        UserDTO dto = new UserDTO("Johnny", "john@example.com", "password123");

        when(userService.create(any(UserDTO.class)))
                .thenThrow(new EmailAlreadyExistsException(dto.getEmail()));

        mockMvc.perform(post("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest());
    }

    /**
     * Verifies that getting all users returns HTTP 200 with a list.
     */
    @Test
    void shouldReturnUserList_whenUsersExist() throws Exception {
        List<UserResponseDTO> users = List.of(
                new UserResponseDTO(1, "Johnny", "john@example.com"),
                new UserResponseDTO(2, "Annabelle", "anna@example.com")
        );

        when(userService.getAllUsers()).thenReturn(users);

        mockMvc.perform(get("/v1/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    /**
     * Verifies that finding a user by ID returns HTTP 200.
     */
    @Test
    void shouldReturnUser_whenUserExists() throws Exception {
        UserResponseDTO response = new UserResponseDTO(1, "Johnny", "john@example.com");

        when(userService.findById(1)).thenReturn(response);

        mockMvc.perform(get("/v1/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("Johnny"));
    }

    /**
     * Verifies that getting a non-existing user returns HTTP 404.
     */
    @Test
    void shouldReturn404_whenUserDoesNotExist() throws Exception {
        when(userService.findById(1)).thenThrow(new UserNotFoundException(1));

        mockMvc.perform(get("/v1/users/1"))
                .andExpect(status().isNotFound());
    }

    /**
     * Verifies that deleting a user returns HTTP 204.
     */
    @Test
    void shouldReturn204_whenUserIsDeleted() throws Exception {
        mockMvc.perform(delete("/v1/users/1"))
                .andExpect(status().isNoContent());

        verify(userService).delete(1);
    }

    /**
     * Verifies that deleting a non-existing user returns HTTP 404.
     */
    @Test
    void shouldReturn404_whenDeletingNonExistingUser() throws Exception {
        doThrow(new UserNotFoundException(1)).when(userService).delete(1);

        mockMvc.perform(delete("/v1/users/1"))
                .andExpect(status().isNotFound());
    }
}
