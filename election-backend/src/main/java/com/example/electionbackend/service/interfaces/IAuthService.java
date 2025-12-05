package com.example.electionbackend.service.interfaces;

import com.example.electionbackend.model.User;

/**
 * Interface representing the authentication service.
 * Provides methods for managing and handling user authentication.
 */
public interface IAuthService {
    User login(String email, String rawPassword);
}
