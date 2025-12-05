package com.example.electionbackend.repository;

import com.example.electionbackend.model.Municipality;
import com.example.electionbackend.repository.interfaces.MunicipalityRepository;
import com.example.electionbackend.type.RegionType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository implementation for Municipality entity.
 */
@Repository
public class MunicipalityJPARepository implements MunicipalityRepository {
    @PersistenceContext
    EntityManager entityManager;

    /**
     * Finds a Municipality by its ID.
     *
     * @param id - the ID of the Municipality
     * @return - the Municipality entity
     */
    @Transactional
    @Override
    public Municipality findById(int id) {
        return entityManager.find(Municipality.class, id);
    }

    /**
     * Retrieves all Municipality records.
     *
     * @return - list of all Municipality entities
     */
    @Transactional
    @Override
    public List<Municipality> findAll(int year) {
        String electionId = "TK" + year;
        System.out.println("ELECTION ID: " + electionId);
        return entityManager.createQuery(
                "SELECT m FROM Municipality m " +
                        "JOIN m.constituency c " +
                        "JOIN c.state s " +
                        "JOIN s.election e " +
                        "WHERE e.id = :electionId",
                        Municipality.class)
                .setParameter("electionId", electionId)
                .getResultList();
    }

    /**
     * Saves or updates a Municipality entity.
     *
     * @param municipality - the Municipality entity to be saved or updated
     * @return - the saved or updated Municipality entity
     */
    @Transactional
    @Override
    public Municipality save(Municipality municipality) {
        if (municipality.getId() == 0) {
            entityManager.persist(municipality);
            return municipality;
        } else {
            return entityManager.merge(municipality);
        }
    }

    /**
     * Deletes a Municipality entity.
     *
     * @param municipality - the Municipality entity to be deleted
     * @return - the deleted Municipality entity
     */
    @Transactional
    @Override
    public Municipality delete(Municipality municipality) {
        entityManager.remove(municipality);
        return municipality;
    }

    /**
     * Deletes a Municipality by its ID.
     *
     * @param id - the ID of the Municipality to be deleted
     * @return - the deleted Municipality entity
     */
    @Transactional
    @Override
    public Municipality deleteById(int id) {
        Municipality municipality = findById(id);
        if (municipality != null) {
            delete(municipality);
        }
        return municipality;
    }
}
