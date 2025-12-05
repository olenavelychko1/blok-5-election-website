package com.example.electionbackend.interfaces;

import com.example.electionbackend.dto.RegionDTO;

import java.util.List;

/**
 * Service interface for managing Election related operations.
 */
public interface IElectionService {
    List<RegionDTO> getAll();
}
