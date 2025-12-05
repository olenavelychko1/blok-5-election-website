package com.example.electionbackend.mapper;

import com.example.electionbackend.dto.PartyVoteDTO;
import com.example.electionbackend.dto.PartyVoteDetailDTO;
import com.example.electionbackend.model.Election;
import com.example.electionbackend.model.Party;
import com.example.electionbackend.model.PartyVote;
import com.example.electionbackend.type.RegionType;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting Party Vote data into various DTOs.
 */
@Component
public class PartyVoteMapper {
    /**
     * Convert to PartyVote entity
     *
     * @param dto        - the PartyVoteDTO
     * @param regionType - the type of the region
     * @param regionId   - the ID of the region
     * @return - the PartyVote entity
     */
    public PartyVote toEntity(PartyVoteDTO dto, RegionType regionType, int regionId, Election election) {
        Party relatedParty = election.getPartyList().stream()
                .filter(party -> party.getPid() == dto.getPartyId())
                .findFirst()
                .orElseThrow();

        PartyVote entity = new PartyVote(
                regionType,
                regionId,
                dto.getVotes()
        );
        entity.setPartyId(relatedParty.getId());
        return entity;
    }

    /**
     * Convert to PartyVoteDTO
     *
     * @param entity - the PartyVote entity
     * @return - the PartyVoteDTO
     */
    public PartyVoteDTO toDTO(PartyVote entity) {
        PartyVoteDTO dto = new PartyVoteDTO(
                entity.getPartyId(),
                entity.getVotes(),
                entity.getRegionType(),
                entity.getRegionId()
        );
        return dto;
    }

    /**
     * Convert to PartyVoteDetailDTO
     *
     * @param partyVote  - the PartyVote entity
     * @param regionName - the name of the region
     * @param partyName  - the name of the party
     * @return - the PartyVoteDetailDTO
     */
    public PartyVoteDetailDTO toPartyVoteDetailDTO(
            PartyVote partyVote,
            String regionName,
            String partyName,
            int seats
    ) {
        return new PartyVoteDetailDTO(
                partyVote.getPartyId(),
                partyVote.getVotes(),
                partyVote.getRegionType(),
                partyVote.getRegionId(),
                partyName,
                regionName,
                seats
        );
    }
}