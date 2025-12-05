package com.example.electionbackend.repository;

import com.example.electionbackend.model.PollingStation;
import com.example.electionbackend.repository.interfaces.PollingStationRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository implementation for PollingStation entity.
 */
@Repository
public class PollingStationJPARepository implements PollingStationRepository {
    @PersistenceContext
    EntityManager entityManager;

    /**
     * Finds a PollingStation by its ID.
     * @param id - the ID of the PollingStation
     * @return - the PollingStation entity
     */
    @Transactional
    @Override
    public PollingStation findById(int id) {
        return entityManager.find(PollingStation.class, id);
    }

    /**
     * Retrieves all PollingStation records.
     * @return - list of all PollingStation entities
     */
    @Transactional
    @Override
    public List<PollingStation> findAll(int year) {
        String electionId = "TK" + year;
        return entityManager.createQuery(
                "SELECT p FROM PollingStation p " +
                        "JOIN p.municipality m " +
                        "JOIN m.constituency c " +
                        "JOIN c.state s " +
                        "JOIN s.election e " +
                        "WHERE e.id = :electionId",
                        PollingStation.class)
                .setParameter("electionId", electionId)
                .getResultList();
    }

    /**
     * Saves or updates a PollingStation entity.
     * @param pollingStation - the PollingStation entity to be saved or updated
     * @return - the saved or updated PollingStation entity
     */
    @Transactional
    @Override
    public PollingStation save(PollingStation pollingStation) {
        if (pollingStation.getId() == 0) {
            entityManager.persist(pollingStation);
            return pollingStation;
        } else {
            return entityManager.merge(pollingStation);
        }
    }

    /**
     * Deletes a PollingStation entity.
     * @param pollingStation - the PollingStation entity to be deleted
     * @return - the deleted PollingStation entity
     */
    @Transactional
    @Override
    public PollingStation delete(PollingStation pollingStation) {
        entityManager.remove(pollingStation);
        return pollingStation;
    }

    /**
     * Deletes a PollingStation by its ID.
     * @param id - the ID of the PollingStation to be deleted
     * @return - the deleted PollingStation entity
     */
    @Transactional
    @Override
    public PollingStation deleteById(int id) {
        PollingStation pollingStation = findById(id);
        if (pollingStation != null) {
            delete(pollingStation);
        }
        return pollingStation;
    }
}
