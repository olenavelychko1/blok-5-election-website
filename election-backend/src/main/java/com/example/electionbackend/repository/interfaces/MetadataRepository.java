package com.example.electionbackend.repository.interfaces;

import com.example.electionbackend.model.Metadata;
import com.example.electionbackend.type.RegionType;

import java.util.List;

/**
 * Interface for Metadata repository operations.
 */
public interface MetadataRepository {
    List<Metadata> findByRegionTypeAndId(RegionType regionType, String id);
    List<Metadata> findByRegionType(RegionType regionType);
}
