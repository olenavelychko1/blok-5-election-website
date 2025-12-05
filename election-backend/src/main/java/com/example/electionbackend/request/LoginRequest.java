package com.example.electionbackend.request;

/**
 * Represents a request for login containing user credentials.
 * This class is typically used to transfer login data in HTTP requests.
 */
public class LoginRequest {
    private String email;
    private String password;

    /**
     * Retrieves the email address associated with the login request.
     *
     * @return the email address as a String
     */
    public String getEmail() {
        return email;
    }

    /**
     * Retrieves the password associated with the login request.
     *
     * @return the password as a String
     */
    public String getPassword() {
        return password;
    }
}
