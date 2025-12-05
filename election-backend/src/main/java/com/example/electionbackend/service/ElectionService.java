package com.example.electionbackend.service;

import com.example.electionbackend.dto.RegionDTO;
import com.example.electionbackend.exception.election.ElectionNotFoundException;
import com.example.electionbackend.interfaces.IElectionService;
import com.example.electionbackend.model.Election;
import com.example.electionbackend.repository.interfaces.ElectionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service implementation for managing Election related operations.
 */
@Service
public class ElectionService implements IElectionService {
    private final ElectionRepository electionRepository;

    /**
     * Constructs an ElectionService with the specified ElectionRepository.
     *
     * @param electionRepository The repository for accessing election data.
     */
    public ElectionService(ElectionRepository electionRepository) {
        this.electionRepository = electionRepository;
    }

    /**
     * Retrieves all elections and converts them to RegionDTOs.
     *
     * @return A list of RegionDTOs representing the elections.
     */
    @Override
    public List<RegionDTO> getAll() {
        List<Election> elections = electionRepository.getAll();
        if (elections == null || elections.isEmpty()) {
            throw new ElectionNotFoundException("No elections found in the database.");
        }
        return elections.stream()
                .map(election -> new RegionDTO(election.getState().getId(), election.getId()))
                .toList();
    }
}
