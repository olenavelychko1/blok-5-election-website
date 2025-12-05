package com.example.electionbackend.service;

import com.example.electionbackend.dto.PartyVoteDetailDTO;
import com.example.electionbackend.dto.RegionDTO;
import com.example.electionbackend.exception.votes.PartyVoteNotFoundException;
import com.example.electionbackend.interfaces.IConstituencyService;
import com.example.electionbackend.interfaces.IMunicipalityService;
import com.example.electionbackend.interfaces.IPartyVoteService;
import com.example.electionbackend.interfaces.IPollingStationService;
import com.example.electionbackend.model.Party;
import com.example.electionbackend.model.PartyVote;
import com.example.electionbackend.repository.interfaces.PartyVoteRepository;
import com.example.electionbackend.type.RegionType;
import com.example.electionbackend.util.PaginationUtils;
import com.example.electionbackend.util.SortUtils;
import jakarta.transaction.Transactional;
import com.example.electionbackend.mapper.PartyVoteMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Service class for managing PartyVote-related operations.
 */
@Service
public class PartyVoteService implements IPartyVoteService {
    private final PartyVoteRepository partyVoteRepository;
    private final IMunicipalityService municipalityService;
    private final IPollingStationService pollingStationService;
    private final IConstituencyService constituencyService;
    private final PartyService partyService;
    private final PartyVoteMapper partyVoteMapper;
    private final ElectionService electionService;

    public PartyVoteService(PartyVoteRepository partyVoteRepository,
                            MunicipalityService municipalityService,
                            PollingStationService pollingStationService,
                            PartyService partyService,
                            PartyVoteMapper partyVoteMapper,
                            ConstituencyService constituencyService, ElectionService electionService) {
        this.partyVoteRepository = partyVoteRepository;
        this.municipalityService = municipalityService;
        this.pollingStationService = pollingStationService;
        this.partyService = partyService;
        this.partyVoteMapper = partyVoteMapper;
        this.constituencyService = constituencyService;
        this.electionService = electionService;
    }

    /**
     * Get Party Votes by Party ID, get all the votes on all levels (NATIONAL, MUNICIPALITY, POLLING_STATION, etc.)
     * Convert each PartyVote to PartyVoteDTO including region and party names
     *
     * @param partyId - the ID of the Party
     * @return - the list of PartyVoteDTO associated with the Party ID
     */
    @Transactional
    public List<PartyVoteDetailDTO> getByPartyId(int partyId, Pageable pageable) {
        List<PartyVote> votes = partyVoteRepository.findByPartyId(partyId);

        if (votes == null || votes.isEmpty()) {
            throw new PartyVoteNotFoundException("With party ID " + partyId);
        }

        // Paginate and sort the list
        List<PartyVote> pagedList = PaginationUtils.paginate(votes, pageable);
        List<PartyVote> sortedList = SortUtils.sortPartyVotes(pagedList, pageable.getSort());

        return convertToPartyVoteDetailDTOs(sortedList);
    }

    /**
     * Get Party Votes by Region Type, get all the votes for the specified region type
     * Convert each PartyVote to PartyVoteDTO including region and party names
     *
     * @param regionType - the type of the Region
     * @return - the list of PartyVoteDTO associated with the Region Type
     */
    @Transactional
    public List<PartyVoteDetailDTO> getByRegionType(RegionType regionType, Pageable pageable) {
        List<PartyVote> votes = partyVoteRepository.findByType(regionType);

        if (votes == null || votes.isEmpty()) {
            throw new PartyVoteNotFoundException("With region type " + regionType);
        }

        // Paginate and sort the list
        List<PartyVote> pagedList = PaginationUtils.paginate(votes, pageable);
        List<PartyVote> sortedList = SortUtils.sortPartyVotes(pagedList, pageable.getSort());

        return convertToPartyVoteDetailDTOs(sortedList);
    }

    /**
     * Get Party Votes by Region Type and Region ID, get all the votes for the specified region type and ID
     * Convert each PartyVote to PartyVoteDTO including region and party names
     *
     * @param regionType - the type of the Region
     * @param regionId   - the ID of the Region
     * @return - the list of PartyVoteDTO associated with the Region Type and ID
     */
    @Transactional
    public List<PartyVoteDetailDTO> getByRegionTypeAndId(RegionType regionType, String regionId, Pageable pageable) {
        List<PartyVote> votes = partyVoteRepository.findByTypeAndTypeId(regionType, regionId);

        if (votes == null || votes.isEmpty()) {
            throw new PartyVoteNotFoundException("With region type " + regionType + " and region ID " + regionId);
        }

        // Paginate and sort the list
        List<PartyVote> pagedList = PaginationUtils.paginate(votes, pageable);
        List<PartyVote> sortedList = SortUtils.sortPartyVotes(pagedList, pageable.getSort());

        return convertToPartyVoteDetailDTOs(sortedList);
    }

    /**
     * Get Party Votes by Region Type, Region ID and Party ID
     *
     * @param regionType - the type of the Region
     * @param regionId   - the ID of the Region
     * @param partyId    - the ID of the Party
     * @return - the list of PartyVoteDTO associated with the Region Type, Region ID and Party ID
     */
    @Override
    @Transactional
    public List<PartyVoteDetailDTO> getByRegionTypeAndIdAndPartyId(RegionType regionType, String regionId, int partyId, Pageable pageable) {
        List<PartyVote> votes = partyVoteRepository.findByTypeAndTypeIdAndPartyId(regionType, regionId, partyId);

        if (votes == null || votes.isEmpty()) {
            throw new PartyVoteNotFoundException(
                    "With region type " + regionType + ", region ID " + regionId + " and party ID " + partyId);
        }

        // Paginate and sort the list
        List<PartyVote> pagedList = PaginationUtils.paginate(votes, pageable);
        List<PartyVote> sortedList = SortUtils.sortPartyVotes(pagedList, pageable.getSort());

        return convertToPartyVoteDetailDTOs(sortedList);
    }

    /**
     * Create a new PartyVote
     *
     * @param partyVote - the PartyVote entity to be created
     * @return - the created PartyVote entity
     */
    public PartyVote create(PartyVote partyVote) {
        return partyVoteRepository.save(partyVote);
    }

    /**
     * Update an existing PartyVote
     *
     * @param partyVote - the PartyVote entity to be updated
     * @return - the updated PartyVote entity
     */
    public PartyVote update(PartyVote partyVote) {
        return partyVoteRepository.save(partyVote);
    }

    /**
     * Delete a PartyVote
     *
     * @param id - the ID of the PartyVote to be deleted
     */
    public void delete(int id) {
        partyVoteRepository.deleteById(id);
    }

    /**
     * Helper method to convert a list of PartyVote entities to PartyVoteDTOs
     * Adds region and party names to the DTOs
     *
     * @param votes - the list of PartyVote entities
     * @return - the list of PartyVoteDTOs
     */
    private List<PartyVoteDetailDTO> convertToPartyVoteDetailDTOs(List<PartyVote> votes) {
        List<PartyVoteDetailDTO> partyVoteList = new ArrayList<>();

        for (PartyVote vote : votes) {
            RegionDTO region;
            switch (vote.getRegionType()) {
                case MUNICIPALITY:
                    region = municipalityService.getById(vote.getRegionId());
                    break;
                case POLLING_STATION:
                    region = pollingStationService.getById(vote.getRegionId());
                    break;
                case CONSTITUENCY:
                    region = constituencyService.getById(vote.getRegionId());
                    break;
                case NATIONAL:
                    region = new RegionDTO("Nederland");
                    break;
                default:
                    region = new RegionDTO("Unknown Region");
            }

            Party party = partyService.get(vote.getPartyId());

            partyVoteList.add(partyVoteMapper.toPartyVoteDetailDTO(
                    vote, region.getName(), party.getName(), party.getSeats()
            ));
        }
        return partyVoteList;
    }
}
