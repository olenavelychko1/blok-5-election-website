package com.example.electionbackend.mapper;

import com.example.electionbackend.dto.StateDTO;
import com.example.electionbackend.model.Election;
import com.example.electionbackend.model.Metadata;
import com.example.electionbackend.model.State;
import com.example.electionbackend.type.RegionType;
import org.springframework.stereotype.Component;

/**
 * The state mapper class
 */
@Component
public class StateMapper {

    private final ConstituencyMapper constituencyMapper;
    private final PartyVoteMapper partyVoteMapper;
    private final MetadataMapper metadataMapper;

    /**
     * Constructor method
     * @param constituencyMapper ConstituencyMapper
     */
    public StateMapper(ConstituencyMapper constituencyMapper, PartyVoteMapper partyVoteMapper, MetadataMapper metadataMapper) {
        this.constituencyMapper = constituencyMapper;
        this.partyVoteMapper = partyVoteMapper;
        this.metadataMapper = metadataMapper;
    }

    /**
     * Maps a {@link StateDTO} object to a {@link State} entity.
     *
     * @param dto the {@link StateDTO} object containing the data to map to a {@link State} entity
     * @return a {@link State} entity based on the provided {@link StateDTO},
     *         or null if the input {@link StateDTO} is null
     */
    public State toEntity(StateDTO dto, Election election) {
        if (dto == null) {
            return null;
        }

        // Create a new state entity
        State entity = new State();
        entity.setName(dto.getName());

        // Map the party votes for national level
        if (dto.getPartyVoteList() != null) {
            dto.getPartyVoteList()
                    .forEach(partyVote -> entity.addPartyVote(
                            partyVoteMapper.toEntity(partyVote, RegionType.NATIONAL, entity.getId(), election)
                    ));
        }

        // Map the party votes and metadata for the national level
        if (dto.getMetadata() != null) {
            Metadata metadata = metadataMapper.toEntity(dto.getMetadata(), RegionType.NATIONAL, String.valueOf(entity.getId()));
            entity.setMetadata(metadata);
        }

        // Map the constituencies
        dto.getConstituencyList()
                .forEach(c -> entity.addConstituency(constituencyMapper.toEntity(c, election)));

        return entity;
    }

    /**
     * Converts a {@link State} entity into a {@link StateDTO}.
     *
     * @param entity the {@link State} entity to be converted
     * @return a {@link StateDTO} containing the data from the provided {@link State} entity,
     *         or null if the input {@link State} entity is null
     */
    public StateDTO toDTO(State entity) {
        if (entity == null) {
            return null;
        }

        StateDTO dto = new StateDTO(entity.getName());

        // Map the constituencies
        entity.getConstituencyList()
                .forEach(c -> dto.addConstituency(constituencyMapper.toDTO(c)));

        return dto;
    }
}

