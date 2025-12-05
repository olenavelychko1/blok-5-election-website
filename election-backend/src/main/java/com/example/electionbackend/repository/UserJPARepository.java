package com.example.electionbackend.repository;

import com.example.electionbackend.model.User;
import com.example.electionbackend.repository.interfaces.UserRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository implementation for User entity.
 */
@Repository
public class UserJPARepository implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Saves a User entity to the database. If the user does not have an ID yet,
     * it will be persisted; otherwise, it will be updated.
     *
     * @param user the User entity to save
     * @return the saved User entity
     */
    @Override
    public User save(User user) {
        if (user.getId() == 0) {
            entityManager.persist(user);
            return user;
        } else {
            return entityManager.merge(user);
        }
    }

    /**
     * Finds a User by its email address.
     *
     * @param email the email to search for
     * @return the User with the given email
     */
    @Override
    public User findByEmail(String email) {
        List<User> results = entityManager.createQuery(
                        "SELECT u FROM User u WHERE u.email = :email", User.class)
                .setParameter("email", email)
                .getResultList();

        return results.isEmpty() ? null : results.getFirst();
    }

    /**
     * Finds a User by its username.
     *
     * @param username the username to search for
     * @return the User with the given username
     */
    @Override
    public User findByUsername(String username) {
        List<User> results = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                .setParameter("username", username)
                .getResultList();

        return results.isEmpty() ? null : results.getFirst();
    }

    /**
     * Retrieves all User entities from the database.
     *
     * @return a list of all users
     */
    @Override
    public List<User> findAll() {
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    /**
     * Finds a User by its ID.
     *
     * @param id the ID of the user
     * @return the User with the given ID, or null if not found
     */
    @Override
    public User findById(int id) {
        return entityManager.find(User.class, id);
    }

    /**
     * Deletes a User entity from the database. If the entity is not managed,
     * it will first be merged before deletion.
     *
     * @param user the User entity to delete
     */
    @Override
    public void delete(User user) {
        if (entityManager.contains(user)) {
            entityManager.remove(user);
        } else {
            entityManager.remove(entityManager.merge(user));
        }
    }
}
