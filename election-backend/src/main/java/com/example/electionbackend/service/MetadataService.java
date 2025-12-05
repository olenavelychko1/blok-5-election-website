package com.example.electionbackend.service;

import com.example.electionbackend.interfaces.IMetadataService;
import com.example.electionbackend.model.Metadata;
import com.example.electionbackend.repository.interfaces.MetadataRepository;
import com.example.electionbackend.type.RegionType;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing Metadata entities.
 */
@Service
public class MetadataService implements IMetadataService {
    private final MetadataRepository metadataRepository;

    /**
     * Constructor for MetadataService.
     * @param metadataRepository - the metadata repository
     */
    public MetadataService(MetadataRepository metadataRepository) {
        this.metadataRepository = metadataRepository;
    }

    /**
     * Get Metadata by Region Type and ID
     * @param regionType - the type of the Region
     * @param regionId - the ID of the Region
     * @return - the list of Metadata associated with the Region Type and ID
     */
    @Override
    public List<Metadata> getByRegionTypeAndId(RegionType regionType, String regionId) {
        return metadataRepository.findByRegionTypeAndId(regionType, regionId);
    }

    /**
     * Get Metadata by Region Type
     * @param regionType - the type of the Region
     * @return - the list of Metadata associated with the Region Type
     */
    @Override
    public List<Metadata> getByRegionType(RegionType regionType) {
        return metadataRepository.findByRegionType(regionType);
    }
}
