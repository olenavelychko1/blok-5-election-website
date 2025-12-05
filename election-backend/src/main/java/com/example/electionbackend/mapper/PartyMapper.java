package com.example.electionbackend.mapper;

import com.example.electionbackend.dto.PartyDTO;
import com.example.electionbackend.model.Candidate;
import com.example.electionbackend.model.Party;
import com.example.electionbackend.repository.PartyJPARepository;
import com.example.electionbackend.repository.interfaces.PartyRepository;
import com.example.electionbackend.service.PartyService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * The party mapper class
 */
@Component
public class PartyMapper {

    private final CandidateMapper candidateMapper;
    private final PartyService partyService;

    /**
     * The constructor method of the party mapper class
     * @param candidateMapper CandidateMapper
     */
    public PartyMapper(CandidateMapper candidateMapper, PartyService partyService) {
        this.candidateMapper = candidateMapper;
        this.partyService = partyService;
    }

    /**
     * Converts a PartyDTO object to a Party entity.
     *
     * @param dto the PartyDTO object containing the data to be converted
     * @return a Party entity populated with data from the PartyDTO object
     */
    public Party toEntity(PartyDTO dto) {
        Party entity = new Party(dto.getId(), dto.getName(), dto.getSeats());

        // Map the candidates
        List<Candidate> candidates = dto.getCandidates()
                .stream()
                .map(candidateMapper::toEntity)
                .toList();

        // Set the candidates
        entity.setCandidates(candidates);

        // Save the party, this is needed link the generated ID to the party votes
        partyService.save(entity);

        return entity;
    }

    /**
     * Converts a Party entity to a PartyDTO object.
     *
     * @param entity the Party entity to be converted
     * @return a PartyDTO object populated with data from the Party entity
     */
    public PartyDTO toDTO(Party entity) {
        PartyDTO dto = new PartyDTO(entity.getPid(), entity.getName(), new ArrayList<>(), entity.getSeats());

        // Map the candidates
        entity.getCandidates()
                .forEach(c -> dto.addCandidate(candidateMapper.toDTO(c)));

        return dto;
    }
}

