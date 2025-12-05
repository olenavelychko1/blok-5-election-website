package com.example.electionbackend.service;

import com.example.electionbackend.dto.ElectionDTO;
import com.example.electionbackend.mapper.ElectionMapper;
import com.example.electionbackend.model.Election;
import com.example.electionbackend.repository.ElectionJPARepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

/**
 * The seeder service class
 */
@Service
public class SeederService {

    private final ElectionJPARepository electionRepository;
    private final ElectionMapper electionMapper;

    /**
     * Constructor method
     * @param electionRepository The election repository
     * @param electionMapper The election mapper
     */
    public SeederService(ElectionJPARepository electionRepository, ElectionMapper electionMapper) {
        this.electionRepository = electionRepository;
        this.electionMapper = electionMapper;
    }

    /**
     * Saves an election entity to the database.
     * @param electionDTO The election DTO to save
     * @return The saved election entity
     */
    @Transactional
    public Election persistElection(ElectionDTO electionDTO) {
        Election mappedEntity = electionMapper.toEntity(electionDTO);
        return electionRepository.save(mappedEntity);
    }
}
