package com.example.electionbackend.dto;

/**
 * DTO returned to the client after creating or retrieving a user.
 * Contains only safe, non-sensitive user data.
 */
public class UserResponseDTO {

    private int id;
    private String username;
    private String email;

    /**
     * Default constructor.
     */
    public UserResponseDTO() {}

    /**
     * Constructor
     *
     * @param id       the generated user ID
     * @param username the username of the user
     * @param email    the email of the user
     */
    public UserResponseDTO(int id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }

    // --- Getters and Setters ---

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
