package com.example.electionbackend.service;

import com.example.electionbackend.dto.RegionDTO;
import com.example.electionbackend.dto.RegionPartyVoteDTO;
import com.example.electionbackend.exception.constituency.ConstituencyNotFoundException;
import com.example.electionbackend.interfaces.IConstituencyService;
import com.example.electionbackend.mapper.ConstituencyMapper;
import com.example.electionbackend.mapper.MunicipalityMapper;
import com.example.electionbackend.model.Constituency;
import com.example.electionbackend.model.Municipality;
import com.example.electionbackend.model.PartyVote;
import com.example.electionbackend.repository.interfaces.ConstituencyRepository;
import com.example.electionbackend.type.RegionType;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

/**
 * Service class for managing Constituency-related operations.
 */
@Service
public class ConstituencyService implements IConstituencyService {
    private final ConstituencyRepository constituencyRepository;
    private final MunicipalityMapper municipalityMapper;

    /**
     * Constructor for ConstituencyService.
     *
     * @param constituencyRepository - the constituency repository
     */
    public ConstituencyService(ConstituencyRepository constituencyRepository,
                               MunicipalityMapper municipalityMapper) {
        this.constituencyRepository = constituencyRepository;
        this.municipalityMapper = municipalityMapper;
    }

    /**
     * Get Constituency by ID.
     *
     * @param id - the ID of the Constituency
     * @return - the ConstituencyDTO, containing ID and name
     */
    @Transactional
    public RegionDTO getById(int id) {
        // TODO add caching
        Constituency c = constituencyRepository.findById(id);

        if (c == null) {
            throw new ConstituencyNotFoundException(id);
        }
        return ConstituencyMapper.toRegionDTO(c);
    }


    /**
     * Get all Constituencies.
     *
     * @param page - the page number for pagination
     * @param size - the number of records per page
     * @return - a list of ConstituencyDTOs, each containing ID and name
     */
    @Transactional
    public List<RegionDTO> getAllConstituencies(int page, int size, int year) {
        List<Constituency> constituencies = constituencyRepository.findAll(page, size, year);
        if (constituencies == null) {
            return null;
        }
        return constituencies
                .stream()
                .map(ConstituencyMapper::toRegionDTO)
                .sorted(Comparator.comparing(RegionDTO::getRegionName, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }

    /**
     * Get all Municipalities that belong to a given Constituency ID.
     *
     * @param id - the ID of the Constituency
     * @return - a list of RegionDTOs representing the Municipalities
     */
    @Transactional
    public List<RegionDTO> getAllMunicipalitiesById(int id) {
        List<Municipality> municipalities = constituencyRepository.findAllMunicipalitiesById(id);
        if (municipalities == null || municipalities.isEmpty()) {
            return null;
        }

        return municipalities
                .stream()
                .map(municipalityMapper::toRegionDTO)
                .sorted(Comparator.comparing(RegionDTO::getRegionName, String.CASE_INSENSITIVE_ORDER))
                .toList();
    }

    /**
     * Create a new Constituency.
     *
     * @param constituency - the Constituency to create
     * @return - the created Constituency
     */
    @Transactional
    public Constituency createConstituency(Constituency constituency) {
        return constituencyRepository.save(constituency);
    }

    /**
     * Update an existing Constituency.
     *
     * @param constituency - the Constituency to update
     * @return - the updated Constituency
     */
    @Transactional
    public Constituency updateConstituency(Constituency constituency) {
        return constituencyRepository.save(constituency);
    }

    /**
     * Delete a Constituency by ID.
     *
     * @param id - the ID of the Constituency to delete
     */
    @Transactional
    public void deleteConstituency(int id) {
        constituencyRepository.deleteById(id);
    }
}

