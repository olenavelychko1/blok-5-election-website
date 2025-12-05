package com.example.electionbackend.service.interfaces;

import com.example.electionbackend.model.Party;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPartyService {
    public Party get(int id);
    public Party save(Party party);
    public List<Party> getAllWithCandidates(Pageable pageable, String electionId);

}
