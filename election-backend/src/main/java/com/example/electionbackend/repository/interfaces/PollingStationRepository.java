package com.example.electionbackend.repository.interfaces;
import com.example.electionbackend.model.PollingStation;

import java.util.List;

/**
 * Repository interface for managing Polling Station entities.
 */
public interface PollingStationRepository {
    PollingStation findById(int id);
    List<PollingStation> findAll(int year);
    PollingStation save(PollingStation pollingStation);
    PollingStation delete(PollingStation pollingStation);
    PollingStation deleteById(int id);
}
