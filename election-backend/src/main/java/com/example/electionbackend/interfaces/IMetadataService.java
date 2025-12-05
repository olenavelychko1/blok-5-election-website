package com.example.electionbackend.interfaces;

import com.example.electionbackend.model.Metadata;
import com.example.electionbackend.type.RegionType;

import java.util.List;

/**
 * Interface for Metadata service operations.
 */
public interface IMetadataService {
    List<Metadata> getByRegionTypeAndId(RegionType regionType, String regionId);
    List<Metadata> getByRegionType(RegionType regionType);
}
