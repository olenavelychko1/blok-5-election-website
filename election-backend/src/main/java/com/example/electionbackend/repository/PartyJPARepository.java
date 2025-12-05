package com.example.electionbackend.repository;

import com.example.electionbackend.model.Party;
import com.example.electionbackend.repository.interfaces.PartyRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 */
@Repository
@Transactional
public class PartyJPARepository implements PartyRepository {
    @PersistenceContext
    EntityManager entityManager;

    /**
     * Finds a Party by its ID.
     *
     * @param id - the ID of the Party
     * @return - the Party entity
     */
    public Party find(int id) {
        return entityManager.find(Party.class, id);
    }

    /**
     * Saves a Party entity.
     * @param party - the Party entity to save
     * @return - the saved Party entity
     */
    public Party save(Party party) {
        entityManager.persist(party);
        return party;
    }

    /**
     * Gets all parties.
     *
     * @return - a list of parties
     */
    public List<Party> getAll() {
        return entityManager.createQuery("SELECT p FROM Party p", Party.class).getResultList();
    }


    /**
     * Gets all parties with candidates.
     *
     * @return - a list of parties
     */
    public List<Party> getAllWithCandidates() {
        final String jpql =
                "SELECT DISTINCT p FROM Party p " +
                        "LEFT JOIN FETCH p.candidates";

        return entityManager
                .createQuery(jpql, Party.class)
                .getResultList();
    }
}
