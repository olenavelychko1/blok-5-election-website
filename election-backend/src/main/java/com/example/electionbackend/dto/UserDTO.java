package com.example.electionbackend.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * The UserDTO class.
 */
public class UserDTO {
    @NotBlank(message = "Username cannot be empty.")
    @Size(min = 5, max = 50, message = "Username must be between 5 and 50 characters.")
    private String username;

    @NotBlank(message = "Email cannot be empty.")
    @Email(message = "Invalid email format.")
    private String email;

    @NotBlank(message = "Password cannot be empty.")
    @Size(min = 8, message = "Password must be at least 8 characters long.")
    private String password;

    public UserDTO() {}

    /**
     * Creates a new User with username, email, and password.
     *
     * @param username the username of the user
     * @param email    the email address of the user
     * @param password the password of the user
     */
    public UserDTO(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    // --- Getters and Setters ---
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
}
