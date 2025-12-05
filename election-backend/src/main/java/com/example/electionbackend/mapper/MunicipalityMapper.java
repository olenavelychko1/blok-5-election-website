package com.example.electionbackend.mapper;

import com.example.electionbackend.dto.MunicipalityDTO;
import com.example.electionbackend.dto.MunicipalityPollingStationDTO;
import com.example.electionbackend.dto.PollingStationDTO;
import com.example.electionbackend.dto.RegionDTO;
import com.example.electionbackend.model.Election;
import com.example.electionbackend.model.Metadata;
import com.example.electionbackend.model.Municipality;
import com.example.electionbackend.type.RegionType;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

/**
 * Mapper class for converting Municipality entities to various DTOs.
 */
@Component
public class MunicipalityMapper {

    public final MetadataMapper metadataMapper;
    private final PollingStationMapper pollingStationMapper;
    private final PartyVoteMapper partyVoteMapper;

    /**
     * Constructor for the MunicipalityMapper class.
     *
     * @param metadataMapper the MetadataMapper instance used for mapping metadata.
     */
    public MunicipalityMapper(MetadataMapper metadataMapper,
                              PollingStationMapper pollingStationMapper,
                              PartyVoteMapper partyVoteMapper) {
        this.metadataMapper = metadataMapper;
        this.pollingStationMapper = pollingStationMapper;
        this.partyVoteMapper = partyVoteMapper;
    }

    /**
     * Converts a MunicipalityDTO object into a Municipality entity.
     *
     * @param dto the MunicipalityDTO object to be converted
     * @return the corresponding Municipality entity
     */
    public Municipality toEntity(MunicipalityDTO dto, Election election) {
        Municipality municipality = new Municipality(dto.getName());

        if (dto.getMetadata() != null) {
            Metadata metadata = metadataMapper.toEntity(dto.getMetadata(), RegionType.MUNICIPALITY, String.valueOf(municipality.getId()));
            municipality.setMetadata(metadata);
        }

        // Map the party votes
        if (dto.getPartyVoteList() != null) {
            dto.getPartyVoteList()
                    .forEach(partyVote -> municipality.addPartyVote(
                            partyVoteMapper.toEntity(partyVote, RegionType.MUNICIPALITY, municipality.getId(), election)
                    ));
        }

        // Map the polling stations
        if (dto.getPollingStationList() != null) {
            dto.getPollingStationList()
                    .forEach(pollingStation -> municipality.addPollingStation(pollingStationMapper.toEntity(pollingStation, election)));
        }

        return municipality;
    }

    /**
     * Converts a Municipality entity into its corresponding MunicipalityDTO.
     *
     * @param entity the Municipality entity to be converted
     * @return the corresponding MunicipalityDTO
     */
    public MunicipalityDTO toDTO(Municipality entity) {
        return new MunicipalityDTO(entity.getId(), entity.getName());
    }

    /**
     * Converts a Municipality entity to a RegionDTO.
     *
     * @param m - the Municipality entity
     * @return - the corresponding RegionDTO
     */
    public RegionDTO toRegionDTO(Municipality m) {
        if (m == null) return null;
        return new RegionDTO(m.getId(), m.getName());
    }

    /**
     * Converts a Municipality entity to a MunicipalityPollingStationDTO.
     *
     * @param m - the Municipality entity
     * @return - the corresponding MunicipalityPollingStationDTO
     */
    public  MunicipalityPollingStationDTO toMunicipalityPollingStationDTO(Municipality m) {
        if (m == null) return null;
        List<PollingStationDTO> pollingStations = m.getPollingStationList()
                .stream()
                .map(ps -> new PollingStationDTO(ps.getId(), ps.getPollingStationId(), ps.getName()))
                .sorted(Comparator.comparing(PollingStationDTO::getName, String.CASE_INSENSITIVE_ORDER))
                .toList();
        return new MunicipalityPollingStationDTO(m.getId(), m.getName(), pollingStations);
    }
}