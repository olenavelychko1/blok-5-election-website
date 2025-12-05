package com.example.electionbackend.service;

import com.example.electionbackend.dto.RegionDTO;
import com.example.electionbackend.dto.MunicipalityPollingStationDTO;
import com.example.electionbackend.exception.municipality.MunicipalityNotFoundException;
import com.example.electionbackend.interfaces.IMunicipalityService;
import com.example.electionbackend.model.Municipality;
import com.example.electionbackend.repository.interfaces.MunicipalityRepository;
import com.example.electionbackend.util.PaginationUtils;
import com.example.electionbackend.util.SortUtils;
import jakarta.transaction.Transactional;
import com.example.electionbackend.mapper.MunicipalityMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service class for managing Municipality-related operations.
 */
@Service
public class MunicipalityService implements IMunicipalityService {
    private final MunicipalityRepository municipalityRepository;
    private final MunicipalityMapper municipalityMapper;


    /**
     * Cache to store RegionDTOs for O(1) retrieval by election year and municipality ID.
     * Used when getting all municipalities for a specific election year.
     * Cache per election: election year -> (municipalityId -> RegionDTO)
     */
    private final Map<Integer, Map<Integer, RegionDTO>> municipalityCacheByElection = new ConcurrentHashMap<>();

    /**
     * Cache to store RegionDTOs for O(1) retrieval by ID.
     * Used when getting a municipality by ID.
     * Cache: municipalityId -> RegionDTO
     */
    private final Map<Integer, RegionDTO> municipalityCache = new ConcurrentHashMap<>();


    /**
     * Constructor for MunicipalityService.
     *
     * @param municipalityRepository - the municipality repository
     */
    public MunicipalityService(MunicipalityRepository municipalityRepository,
                               MunicipalityMapper municipalityMapper) {
        this.municipalityRepository = municipalityRepository;
        this.municipalityMapper = municipalityMapper;
    }

    /**
     * Get Municipality by ID
     *
     * @param id - the ID of the Municipality
     * @return - the MunicipalityDTO, containing ID and name
     */
    @Transactional
    public RegionDTO getById(int id) {
        // O(1) lookup using the cache
        if (municipalityCache.containsKey(id)) {
            return municipalityCache.get(id);
        }

        // If not in cache, load from repository
        Municipality m = municipalityRepository.findById(id);
        if (m == null) {
            throw new MunicipalityNotFoundException(id);
        }

        // Map the Municipality to RegionDTO
        RegionDTO regionDTO = municipalityMapper.toRegionDTO(m);

        // Update both caches
        municipalityCache.put(regionDTO.getId(), regionDTO);
        int year = Integer.parseInt(m.getConstituency().getState().getElection().getId().substring(2));
        municipalityCacheByElection
                .computeIfAbsent(year, k -> new ConcurrentHashMap<>())
                .put(regionDTO.getId(), regionDTO);

        return regionDTO;
    }

    /**
     * Get all Municipalities
     *
     * @return - a list of MunicipalityDTOs, each containing ID and name
     */
    @Transactional
    public Page<RegionDTO> getAll(Pageable pageable, int year) {
        List<RegionDTO> municipalities = loadAllIntoCacheIfEmpty(year);

        // Sort the muncipalities based on pageable sort
        if (pageable.getSort().isSorted()) {
            municipalities = SortUtils.sortRegion(municipalities, pageable.getSort());
        }

        // Paginate the list of RegionDTOs
        List<RegionDTO> pagedList = PaginationUtils.paginate(municipalities, pageable);

        return new PageImpl<>(pagedList, pageable, municipalities.size());
    }

    /**
     * Helper method to load all municipalities into the cache if it's empty.
     *
     * @return - List of RegionDTOs
     */
    private List<RegionDTO> loadAllIntoCacheIfEmpty(int year) {
        // Check if cache for the election already exists
        if (municipalityCacheByElection.containsKey(year)) {
            return municipalityCacheByElection.get(year).values().stream().toList();
        }

        // If not, load from repository and populate cache
        List<Municipality> municipalities = municipalityRepository.findAll(year);

        Map<Integer, RegionDTO> electionCache = new ConcurrentHashMap<>();
        municipalities.stream()
                .map(municipalityMapper::toRegionDTO)
                .forEach(municipality -> {
                    // Populate both caches
                    electionCache.put(municipality.getId(), municipality);
                    municipalityCache.put(municipality.getId(), municipality);
                });

        municipalityCacheByElection.put(year, electionCache);
        return electionCache.values().stream().toList();
    }

    /**
     * Get Municipality Polling Stations by ID
     *
     * @param id - the ID of the Municipality
     * @return - the MunicipalityPollingStationDTO, containing municipality ID, municipality name, and polling stations
     */
    @Transactional
    public MunicipalityPollingStationDTO getPollingStationsById(int id) {
        Municipality m = municipalityRepository.findById(id);

        if (m == null) {
            throw new MunicipalityNotFoundException(id);
        }

        return municipalityMapper.toMunicipalityPollingStationDTO(m);
    }

    /**
     * Create a new Municipality
     *
     * @param municipality - the Municipality to create
     * @return - the created Municipality
     */
    @Transactional
    public Municipality create(Municipality municipality) {
        Municipality saved = municipalityRepository.save(municipality);

        RegionDTO regionDTO = municipalityMapper.toRegionDTO(saved);
        String year = saved.getConstituency().getState().getElection().getId().substring(2);

        // Update both caches
        municipalityCache.put(regionDTO.getId(), regionDTO);
        municipalityCacheByElection
                .computeIfAbsent(Integer.valueOf(year), k -> new ConcurrentHashMap<>())
                .put(regionDTO.getId(), regionDTO);

        return saved;
    }

    /**
     * Update an existing Municipality
     *
     * @param municipality - the Municipality to update
     * @return - the updated Municipality
     */
    @Transactional
    public Municipality update(Municipality municipality) {
        if (municipalityRepository.findById(municipality.getId()) == null) {
            throw new MunicipalityNotFoundException(municipality.getId());
        }

        Municipality updated = municipalityRepository.save(municipality);
        RegionDTO regionDTO = municipalityMapper.toRegionDTO(updated);
        String year = updated.getConstituency().getState().getElection().getId().substring(2);

        // Update both caches
        municipalityCache.put(regionDTO.getId(), regionDTO);
        municipalityCacheByElection
                .computeIfAbsent(Integer.valueOf(year), k -> new ConcurrentHashMap<>())
                .put(regionDTO.getId(), regionDTO);

        return updated;
    }

    /**
     * Delete a Municipality by ID
     *
     * @param id - the ID of the Municipality to delete
     */
    @Transactional
    public void delete(int id) {
        if (municipalityRepository.findById(id) == null) {
            throw new MunicipalityNotFoundException(id);
        }

        municipalityRepository.deleteById(id);

        // Remove from both caches
        municipalityCache.remove(id);
        municipalityCacheByElection.values().forEach(cache -> cache.remove(id));
    }
}