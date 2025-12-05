package com.example.electionbackend.controller.api.v1;

import com.example.electionbackend.dto.UserDTO;
import com.example.electionbackend.dto.UserResponseDTO;
import com.example.electionbackend.interfaces.IUserService;
import com.example.electionbackend.model.User;
import com.example.electionbackend.request.LoginRequest;
import com.example.electionbackend.service.interfaces.IAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling User-related API endpoints.
 */
@RestController
@RequestMapping("/v1/users")
public class UserController {

    private final IUserService userService;
    private final IAuthService authService;

    /**
     * Constructs the UserController with dependency injection.
     *
     * @param userService the service handling user-related operations
     * @param authService the service handling authentication
     */
    public UserController(IUserService userService, IAuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    /**
     * Creates a new user.
     *
     * @param userDTO validated DTO containing the user's registration data
     * @return the created user including generated ID
     */
    @Operation(summary = "Create a new user",
            description = "Registers a new user and returns the created user with its generated ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User successfully created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or user already exists",
                    content = @Content)
    })
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        UserResponseDTO createdUser = userService.create(userDTO);
        return ResponseEntity.status(201).body(createdUser);
    }


    /**
     * Retrieves all registered users.
     *
     * @return list of UserDTO objects
     */
    @Operation(summary = "Get all users",
            description = "Returns a list of all registered users.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    })
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /**
     * Retrieves a user by its ID.
     *
     * @param id the user ID
     * @return the userDTO if found
     */
    @Operation(summary = "Get a user by ID",
            description = "Retrieves a user by its unique ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User retrieved successfully",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content)
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable int id) {
        return ResponseEntity.ok(userService.findById(id));
    }

    /**
     * Deletes a user by ID.
     *
     * @param id the ID of the user to delete
     * @return empty 204 No Content response
     */
    @Operation(summary = "Delete a user",
            description = "Deletes a user with the specified ID.")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable int id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Handles user login by validating credentials and creating a session.
     *
     * @param request the login request containing email and password
     * @param session the HTTP session to store the authenticated user information
     * @return a ResponseEntity containing the authenticated user's details
     */
    @Operation(summary = "Logging in a user",
            description = "logs in a user and returns the user.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User successfully logged in.",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input or user doesn't exist.",
                    content = @Content)
    })
    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody LoginRequest request, HttpSession session) {
        User user = this.authService.login(request.getEmail(), request.getPassword());

        session.setAttribute("user", user);

        return ResponseEntity.ok(user);
    }
}
