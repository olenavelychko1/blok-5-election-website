package com.example.electionbackend.repository;

import com.example.electionbackend.model.Election;
import com.example.electionbackend.repository.interfaces.ElectionRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository interface for managing Election entities.
 */
@Repository
public class ElectionJPARepository implements ElectionRepository {
    @PersistenceContext
    EntityManager entityManager;

    /**
     * Retrieves an Election by its identifier.
     *
     * @param id the unique identifier of the election
     * @return the Election instance, or null if not found
     */
    @Override
    public Election get(String id) {
        return entityManager.find(Election.class, id);
    }

    /**
     * Retrieves all Election records.
     *
     * @return a list of all Election entities
     */
    @Override
    public List<Election> getAll() {
        return entityManager.createQuery("SELECT e FROM Election e", Election.class).getResultList();
    }

    /**
     * Saves a new Election or updates an existing one.
     *
     * @param election the Election entity to store
     * @return the persisted Election instance
     */
    @Override
    @Transactional
    public Election save(Election election) {
        if (election.getId() == null || entityManager.find(Election.class, election.getId()) == null) {
            entityManager.persist(election);
            return election;
        } else {
            return entityManager.merge(election);
        }
    }
}
