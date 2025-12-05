package com.example.electionbackend.repository;

import com.example.electionbackend.model.Metadata;
import com.example.electionbackend.repository.interfaces.MetadataRepository;
import com.example.electionbackend.type.RegionType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository implementation for Metadata entity.
 */
@Repository
public class MetadataJPARepository implements MetadataRepository {
    @PersistenceContext
    EntityManager entityManager;

    /**
     * Find Metadata by Region Type and ID.
     * @param regionType - the type of the region
     * @param id - the ID of the region
     * @return - list of Metadata matching the criteria
     */
    @Transactional
    @Override
    public List<Metadata> findByRegionTypeAndId(RegionType regionType, String id) {
        return entityManager.createQuery(
                        "SELECT m FROM Metadata m WHERE m.regionType = :regionType AND m.regionId = :id", Metadata.class)
                .setParameter("regionType", regionType)
                .setParameter("id", id)
                .getResultList();
    }

    /**
     * Find Metadata by Region Type
     * @param regionType - the type of the region
     * @return - list of Metadata matching the criteria
     */
    @Transactional
    @Override
    public List<Metadata> findByRegionType(RegionType regionType) {
        return entityManager.createQuery(
                        "SELECT m FROM Metadata m WHERE m.regionType = :regionType", Metadata.class)
                .setParameter("regionType", regionType)
                .getResultList();
    }
}
