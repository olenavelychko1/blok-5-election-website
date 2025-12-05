package com.example.electionbackend.repository;

import com.example.electionbackend.model.PartyVote;
import com.example.electionbackend.repository.interfaces.PartyVoteRepository;
import com.example.electionbackend.type.RegionType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository implementation for PartyVote entity.
 */
@Repository
public class PartyVoteJPARepository implements PartyVoteRepository {
    @PersistenceContext
    EntityManager entityManager;

    /**
     * Finds PartyVote records by Party ID.
     *
     * @param id   - the Party ID
     * @return - list of PartyVote records associated with the given Party ID
     */
    @Transactional
    @Override
    public List<PartyVote> findByPartyId(int id) {
        return entityManager.createQuery(
                        "SELECT pv FROM PartyVote pv WHERE pv.partyId = :partyId", PartyVote.class)
                .setParameter("partyId", id)
                .getResultList();
    }

    /**
     * Finds PartyVote records by Region Type.
     *
     * @param type - the Region Type
     * @return - list of PartyVote records associated with the given Region Type
     */
    @Transactional
    @Override
    public List<PartyVote> findByType(RegionType type) {
        return entityManager.createQuery(
                        "SELECT pv FROM PartyVote pv WHERE pv.regionType = :regionType", PartyVote.class)
                .setParameter("regionType", type)
                .getResultList();
    }

    /**
     * Finds PartyVote records by Region Type and Region ID.
     *
     * @param type - the Region Type
     * @param id   - the Region ID
     * @return - list of PartyVote records associated with the given Region Type and Region ID
     */
    @Transactional
    @Override
    public List<PartyVote> findByTypeAndTypeId(RegionType type, String id) {
        return entityManager.createQuery(
                        "SELECT pv FROM PartyVote pv WHERE pv.regionType = :regionType AND pv.regionId = :regionId", PartyVote.class)
                .setParameter("regionType", type)
                .setParameter("regionId", id)
                .getResultList();
    }

    /**
     * Finds PartyVote records by Region Type, Region ID, and Party ID.
     *
     * @param type    - the Region Type
     * @param id      - the Region ID
     * @param partyId - the Party ID
     * @return - list of PartyVote records associated with the given Region Type, Region ID, and Party ID
     */
    @Override
    public List<PartyVote> findByTypeAndTypeIdAndPartyId(RegionType type, String id, int partyId) {
        return entityManager.createQuery(
                        "SELECT pv FROM PartyVote pv " +
                                "WHERE pv.regionType = :regionType " +
                                "AND pv.regionId = :regionId " +
                                "AND pv.partyId = :partyId", PartyVote.class)
                .setParameter("regionType", type)
                .setParameter("regionId", id)
                .setParameter("partyId", partyId)
                .getResultList();
    }

    /**
     * Saves or updates a PartyVote entity.
     *
     * @param partyVote - the PartyVote entity to be saved or updated
     * @return - the saved or updated PartyVote entity
     */
    @Override
    public PartyVote save(PartyVote partyVote) {
        if (partyVote.getId() == 0) {
            entityManager.persist(partyVote);
            return partyVote;
        } else {
            return entityManager.merge(partyVote);
        }
    }

    /**
     * Deletes a PartyVote entity.
     *
     * @param partyVote - the PartyVote entity to be deleted
     * @return - the deleted PartyVote entity
     */
    @Override
    public PartyVote delete(PartyVote partyVote) {
        entityManager.remove(partyVote);
        return partyVote;
    }

    /**
     * Deletes a PartyVote by its ID.
     *
     * @param id - the ID of the PartyVote to be deleted
     * @return - the deleted PartyVote entity
     */
    @Override
    public PartyVote deleteById(int id) {
        PartyVote partyVote = entityManager.find(PartyVote.class, id);
        if (partyVote != null) {
            entityManager.remove(partyVote);
        }
        return partyVote;
    }
}
