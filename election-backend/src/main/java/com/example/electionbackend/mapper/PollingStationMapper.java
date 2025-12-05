package com.example.electionbackend.mapper;

import com.example.electionbackend.dto.PollingStationDTO;
import com.example.electionbackend.dto.RegionDTO;
import com.example.electionbackend.model.Election;
import com.example.electionbackend.model.Metadata;
import com.example.electionbackend.model.PollingStation;
import com.example.electionbackend.type.RegionType;
import org.springframework.stereotype.Component;

/**
 * Mapper class for converting PollingStation entities to various DTOs.
 */
@Component
public class PollingStationMapper {
    public final PartyVoteMapper partyVoteMapper;
    public final MetadataMapper metadataMapper;

    public PollingStationMapper(MetadataMapper metadataMapper,
                                PartyVoteMapper partyVoteMapper) {
        this.metadataMapper = metadataMapper;
        this.partyVoteMapper = partyVoteMapper;
    }

    /**
     * Converts a PollingStationDTO object into a PollingStation entity.
     *
     * @param dto the PollingStationDTO object to be converted
     * @return the corresponding PollingStation entity
     */
    public PollingStation toEntity(PollingStationDTO dto, Election election) {
        PollingStation pollingStation = new PollingStation(dto.getPollingStationId(), dto.getRegionName());

        // Map the party votes
        if (dto.getPartyVoteList() != null) {
            dto.getPartyVoteList()
                    .forEach(partyVote -> pollingStation.addPartyVote(
                            partyVoteMapper.toEntity(partyVote, RegionType.POLLING_STATION, pollingStation.getId(), election)
                    ));
        }

        if (dto.getMetadata() != null) {
            Metadata metadata = metadataMapper.toEntity(dto.getMetadata(), RegionType.POLLING_STATION, String.valueOf(pollingStation.getId()));
            pollingStation.setMetadata(metadata);
        }

        return pollingStation;
    }

    /**
     * Converts a PollingStation entity to a RegionDTO.
     *
     * @param p the PollingStation entity
     * @return the corresponding RegionDTO
     */
    public RegionDTO toRegionDTO(PollingStation p) {
        if (p == null) return null;
        return new RegionDTO(p.getId(), p.getName());
    }
}
