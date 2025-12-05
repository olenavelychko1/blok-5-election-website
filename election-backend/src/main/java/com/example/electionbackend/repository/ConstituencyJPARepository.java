package com.example.electionbackend.repository;

import com.example.electionbackend.model.Municipality;
import com.example.electionbackend.repository.interfaces.ConstituencyRepository;


import com.example.electionbackend.model.Constituency;
import com.example.electionbackend.type.RegionType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository implementation for Constituency entity.
 */
@Repository
public class ConstituencyJPARepository implements ConstituencyRepository {

    @PersistenceContext
    EntityManager entityManager;

    /**
     * Finds a Constituency by its ID.
     *
     * @param id - the ID of the Constituency
     * @return - the Constituency entity
     */
    @Transactional
    @Override
    public Constituency findById(int id) {
        return entityManager.find(Constituency.class, id);
    }

    /**
     * Retrieves all Constituency records.
     *
     * @param page - the page number for pagination
     * @param size - the number of records per page
     * @return - list of all Constituency entities
     */
    @Transactional
    @Override
    public List<Constituency> findAll(int page, int size, int year) {
        String electionId = "TK" + year;
        return entityManager.createQuery(
                "SELECT c FROM Constituency c " +
                        "JOIN c.state s " +
                        "JOIN s.election e " +
                        "WHERE e.id = :electionId", Constituency.class)
                .setParameter("electionId", electionId)
                .setFirstResult((page - 1) * size)
                .setMaxResults(size)
                .getResultList();
    }

    /**
     * Finds all Municipalities that belong to a specific Constituency by its ID.
     *
     * @param id - the ID of the Constituency
     * @return - a list of Municipality entities belonging to the Constituency
     */
    @Transactional
    @Override
    public List<Municipality> findAllMunicipalitiesById(int id) {
        return entityManager.createQuery(
                        "SELECT m FROM Municipality m " +
                                "WHERE m.constituency.id = :id", Municipality.class)
                .setParameter("id", id)
                .getResultList();
    }

    /**
     * Saves or updates a Constituency entity.
     *
     * @param constituency - the Constituency entity to be saved or updated
     * @return - the saved or updated Constituency entity
     */
    @Transactional
    @Override
    public Constituency save(Constituency constituency) {
        if (constituency.getId() == 0) {
            entityManager.persist(constituency);
            return constituency;
        } else {
            return entityManager.merge(constituency);
        }
    }

    /**
     * Deletes a Constituency entity.
     *
     * @param constituency - the Constituency entity to be deleted
     * @return - the deleted Constituency entity
     */
    @Transactional
    @Override
    public Constituency delete(Constituency constituency) {
        entityManager.remove(constituency);
        return constituency;
    }

    /**
     * Deletes a Constituency by its ID.
     *
     * @param id - the ID of the Constituency to be deleted
     * @return - the deleted Constituency entity
     */
    @Transactional
    @Override
    public Constituency deleteById(int id) {
        Constituency constituency = findById(id);
        if (constituency != null) {
            delete(constituency);
        }
        return constituency;
    }
}

