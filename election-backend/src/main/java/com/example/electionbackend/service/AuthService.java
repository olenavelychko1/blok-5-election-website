package com.example.electionbackend.service;

import com.example.electionbackend.model.User;
import com.example.electionbackend.repository.interfaces.UserRepository;
import com.example.electionbackend.service.interfaces.IAuthService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for handling authentication logic.
 * Provides methods to authenticate users based on their credentials.
 */
@Service
public class AuthService implements IAuthService {

    private final UserRepository userRepository;

    /**
     * Constructs an instance of AuthService with the provided UserRepository.
     *
     * @param userRepository the repository used for accessing and managing User entities
     */
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Authenticates a user by their email and raw password.
     * Ensures the provided password matches the stored password using bcrypt hashing.
     *
     * @param email       the email address of the user attempting to log in
     * @param rawPassword the raw (unencrypted) password provided by the user
     * @return the authenticated {@link User} if the credentials are valid
     * @throws RuntimeException if the email or password is invalid
     */
    public User login(String email, String rawPassword) {
        User user = userRepository.findByEmail(email);

        if (!BCrypt.checkpw(rawPassword, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return user;
    }
}

