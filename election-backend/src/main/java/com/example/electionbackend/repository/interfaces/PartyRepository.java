package com.example.electionbackend.repository.interfaces;
import com.example.electionbackend.model.Party;

import java.util.List;

/**
 * Repository interface for managing Party entities.
 */
public interface PartyRepository {
    public Party find(int id);
    public Party save(Party party);
    public List<Party> getAll();
    public List<Party> getAllWithCandidates();
}
