package com.example.electionbackend.mapper;

import com.example.electionbackend.dto.ConstituencyDTO;
import com.example.electionbackend.dto.RegionDTO;

import com.example.electionbackend.model.Constituency;
import com.example.electionbackend.model.Election;
import com.example.electionbackend.model.Metadata;
import com.example.electionbackend.type.RegionType;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting Constituency entities to various DTOs.
 */
@Component
public class ConstituencyMapper {

    private final MunicipalityMapper municipalityMapper;
    private final MetadataMapper metadataMapper;
    private final PartyVoteMapper partyVoteMapper;


    /**
     * Constructor method
     *
     * @param municipalityMapper MunicipalityMapper
     * @param metadataMapper MetadataMapper
     */
    public ConstituencyMapper(MunicipalityMapper municipalityMapper,
                              MetadataMapper metadataMapper,
                              PartyVoteMapper partyVoteMapper) {
        this.municipalityMapper = municipalityMapper;
        this.metadataMapper = metadataMapper;
        this.partyVoteMapper = partyVoteMapper;
    }

    /**
     * Converts a {@link ConstituencyDTO} object to a {@link Constituency} entity.
     *
     * @param dto the {@link ConstituencyDTO} object containing the data to map to an entity
     * @return a {@link Constituency} entity based on the provided {@link ConstituencyDTO}
     */
    public Constituency toEntity(ConstituencyDTO dto, Election election) {
        Constituency entity = new Constituency(dto.getName());

        if (dto.getMetadata() != null) {
            Metadata metadata = metadataMapper.toEntity(dto.getMetadata(), RegionType.CONSTITUENCY, String.valueOf(entity.getId()));
            entity.setMetadata(metadata);
        }

        // Map the party votes
        if (dto.getPartyVoteList() != null) {
            dto.getPartyVoteList()
                    .forEach(partyVote -> entity.addPartyVote(
                            partyVoteMapper.toEntity(partyVote, RegionType.CONSTITUENCY, entity.getId(), election)
                    ));
        }

        // Map the municipalities
        dto.getMunicipalityList()
                .forEach(m -> entity.addMunicipality(municipalityMapper.toEntity(m, election)));

        return entity;
    }

    /**
     * Converts a {@link Constituency} entity into a {@link ConstituencyDTO}.
     *
     * @param entity the {@link Constituency} entity to be converted
     * @return a {@link ConstituencyDTO} containing the data from the provided entity
     */
    public ConstituencyDTO toDTO(Constituency entity) {
        ConstituencyDTO dto = new ConstituencyDTO(entity.getId(), entity.getName());

        // Map the municipalities
        entity.getMunicipalityList()
                .forEach(m -> dto.addMunicipality(municipalityMapper.toDTO(m)));

        return dto;
    }

    /**
     * Converts a Constituency entity to a RegionDTO.
     *
     * @param c - the Constituency entity
     * @return - the corresponding RegionDTO
     */
    public static RegionDTO toRegionDTO(Constituency c) {
        if (c == null) return null;
        return new RegionDTO(c.getId(), c.getName());
    }
}