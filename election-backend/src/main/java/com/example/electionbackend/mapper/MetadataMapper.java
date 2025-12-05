package com.example.electionbackend.mapper;

import com.example.electionbackend.dto.MetadataDTO;
import com.example.electionbackend.model.Metadata;
import com.example.electionbackend.type.RegionType;
import org.springframework.stereotype.Component;

@Component
public class MetadataMapper {
    public Metadata toEntity(MetadataDTO dto, RegionType regionType, String regionId) {
        Metadata metadata = new Metadata(
                dto.getTotalCast(),
                dto.getTotalCounted(),
                dto.getInvalid(),
                dto.getBlank()
        );

        metadata.setRegionType(regionType);
        metadata.setRegionId(regionId);

        return metadata;
    }
}
