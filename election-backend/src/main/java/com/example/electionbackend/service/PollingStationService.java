package com.example.electionbackend.service;

import com.example.electionbackend.dto.RegionDTO;
import com.example.electionbackend.exception.pollingStation.PollingStationNotFoundException;
import com.example.electionbackend.interfaces.IPollingStationService;
import com.example.electionbackend.model.PollingStation;
import com.example.electionbackend.repository.interfaces.PollingStationRepository;
import com.example.electionbackend.util.PaginationUtils;
import com.example.electionbackend.util.SortUtils;
import jakarta.transaction.Transactional;
import com.example.electionbackend.mapper.PollingStationMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Service class for managing PollingStation-related operations.
 */
@Service
public class PollingStationService implements IPollingStationService {
    private final PollingStationRepository pollingStationRepository;
    private final PollingStationMapper pollingStationMapper;

    /**
     * Cache to store RegionDTOs for O(1) retrieval by election year and polling station ID.
     * Used when getting all polling stations for a specific election year.
     * Cache per election: election year -> (municipalityId -> RegionDTO)
     */
    private final Map<Integer, Map<Integer, RegionDTO>> pollingStationCacheByElection = new ConcurrentHashMap<>();

    /**
     * Cache to store RegionDTOs for O(1) retrieval by ID.
     * Used when getting a municipality by ID.
     * Cache: pollingStation ID -> RegionDTO
     */
    private final Map<Integer, RegionDTO> pollingStationCache = new ConcurrentHashMap<>();

    /**
     * Constructor for PollingStationService.
     */
    public PollingStationService(PollingStationRepository pollingStationRepository,
                                 PollingStationMapper pollingStationMapper) {
        this.pollingStationMapper = pollingStationMapper;
        this.pollingStationRepository = pollingStationRepository;
    }

    /**
     * Get Polling Station by ID
     *
     * @param id - the ID of the Polling Station
     * @return - the RegionDTO, containing ID and name
     */
    @Transactional
    public RegionDTO getById(int id) {
        // Check cache first O(1) lookup
        if (pollingStationCache.containsKey(id)) {
            return pollingStationCache.get(id);
        }

        // If not in cache, retrieve from repository and map to DTO
        PollingStation pollingStation = pollingStationRepository.findById(id);
        if (pollingStation == null) {
            throw new PollingStationNotFoundException(id);
        }

        // Map the polling station to RegionDTO
        RegionDTO regionDTO = pollingStationMapper.toRegionDTO(pollingStation);

        // Update both caches
        pollingStationCache.put(id, regionDTO);
        String year = pollingStation.getMunicipality().getConstituency().getState().getElection().getId().substring(2);
        pollingStationCacheByElection
                .computeIfAbsent(Integer.valueOf(year), k -> new ConcurrentHashMap<>())
                .put(regionDTO.getId(), regionDTO);

        return regionDTO;
    }

    /**
     * Get all Polling Stations
     *
     * @return - list of RegionDTOs, each containing ID and name of a Polling Station
     */
    @Transactional
    public Page<RegionDTO> getAll(Pageable pageable, int year) {
        List<RegionDTO> pollingStations = loadAllIntoCacheIfEmpty(year);

        // Sort the polling stations based on the pageable's sort
        if (pageable.getSort().isSorted()) {
            pollingStations = SortUtils.sortRegion(pollingStations, pageable.getSort());
        }

        // Paginate the sorted list
        List<RegionDTO> pagedList = PaginationUtils.paginate(pollingStations, pageable);

        return new PageImpl<>(pagedList, pageable, pollingStations.size());
    }

    /**
     * Load all Polling Stations into cache if the cache is empty
     *
     * @return - list of RegionDTOs from the cache
     */
    private List<RegionDTO> loadAllIntoCacheIfEmpty(int year) {
        if (pollingStationCacheByElection.containsKey(year)) {
            return pollingStationCacheByElection.get(year).values().stream().toList();
        }

        List<PollingStation> pollingStations = pollingStationRepository.findAll(year);

        Map<Integer, RegionDTO> electionCache = new ConcurrentHashMap<>();
        pollingStations.stream()
                .map(pollingStationMapper::toRegionDTO)
                .forEach(ps -> {
                    // Populate both caches
                    electionCache.put(ps.getId(), ps);
                    pollingStationCache.put(ps.getId(), ps);
                });

        pollingStationCacheByElection.put(year, electionCache);
        return electionCache.values().stream().toList();
    }

    /**
     * Create a new Polling Station
     *
     * @param pollingStation - the Polling Station entity to create
     * @return the created Polling Station entity
     */
    @Transactional
    public PollingStation create(PollingStation pollingStation) {
        PollingStation saved = pollingStationRepository.save(pollingStation);

        // Add to cache
        RegionDTO regionDTO = pollingStationMapper.toRegionDTO(saved);
        String year = saved.getMunicipality().getConstituency().getState().getElection().getId().substring(2);

        // Update both caches
        pollingStationCache.put(regionDTO.getId(), regionDTO);
        pollingStationCacheByElection
                .computeIfAbsent(Integer.valueOf(year), k -> new ConcurrentHashMap<>())
                .put(regionDTO.getId(), regionDTO);

        return saved;
    }

    /**
     * Update an existing Polling Station
     *
     * @param pollingStation - the Polling Station entity to update
     * @return the updated Polling Station entity
     */
    @Transactional
    public PollingStation update(PollingStation pollingStation) {
        if (pollingStationRepository.findById(pollingStation.getId()) == null) {
            throw new PollingStationNotFoundException(pollingStation.getId());
        }

        PollingStation updated = pollingStationRepository.save(pollingStation);
        String year = updated.getMunicipality().getConstituency().getState().getElection().getId().substring(2);
        RegionDTO regionDTO = pollingStationMapper.toRegionDTO(updated);

        // Update both caches
        pollingStationCache.put(updated.getId(), regionDTO);
        pollingStationCacheByElection
                .computeIfAbsent(Integer.valueOf(year), k -> new ConcurrentHashMap<>())
                .put(regionDTO.getId(), regionDTO);

        return updated;
    }

    /**
     * Delete a Polling Station by its ID
     *
     * @param id - the ID of the Polling Station to be deleted
     */
    @Transactional
    public void delete(int id) {
        if (pollingStationRepository.findById(id) == null) {
            throw new PollingStationNotFoundException(id);
        }

        pollingStationRepository.deleteById(id);

        // Remove from both caches
        pollingStationCache.remove(id);
        pollingStationCacheByElection.values().forEach(cache -> cache.remove(id));
    }
}