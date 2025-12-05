package com.example.electionbackend.mapper;

import com.example.electionbackend.dto.ElectionDTO;
import com.example.electionbackend.dto.PartyDTO;
import com.example.electionbackend.model.Election;
import com.example.electionbackend.model.Party;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * The election mapper class
 */
@Component
public class ElectionMapper {

    private final StateMapper stateMapper;
    private final PartyMapper partyMapper;

    /**
     * Constructor method
     * @param stateMapper The state mapper
     * @param partyMapper The party mapper
     */
    public ElectionMapper(StateMapper stateMapper, PartyMapper partyMapper) {
        this.stateMapper = stateMapper;
        this.partyMapper = partyMapper;
    }

    /**
     * Converts an ElectionDTO object into an Election entity.
     *
     * @param dto the ElectionDTO object to be converted. If null, the method returns null.
     * @return an Election entity containing data mapped from the provided ElectionDTO.
     */
    public Election toEntity(ElectionDTO dto) {
        if (dto == null) {
            return null;
        }

        Election entity = new Election();
        entity.setId(dto.getId());

        // Map the parties
        List<Party> parties = dto.getPartyList()
                .stream()
                .map(partyMapper::toEntity)
                .toList();

        // Set the parties
        entity.setPartyList(parties);

        // Map the state
        entity.setState(stateMapper.toEntity(dto.getState(), entity));

        return entity;
    }

    /**
     * Converts an Election entity into an ElectionDTO object.
     *
     * @param entity the Election entity to be converted. If null, the method returns null.
     * @return an ElectionDTO object containing data mapped from the provided Election entity.
     */
    public ElectionDTO toDTO(Election entity) {
        if (entity == null) return null;

        ElectionDTO dto = new ElectionDTO();
        dto.setId(entity.getId());

        // Map the state
        dto.setState(stateMapper.toDTO(entity.getState()));

        // Map the parties
        List<PartyDTO> partyDTOs = entity.getPartyList()
                .stream()
                .map(partyMapper::toDTO)
                .toList();

        // Set the parties
        dto.setPartyList(partyDTOs);
        return dto;
    }
}
