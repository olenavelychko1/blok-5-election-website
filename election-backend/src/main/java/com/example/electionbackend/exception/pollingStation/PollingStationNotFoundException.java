package com.example.electionbackend.exception.pollingStation;

public class PollingStationNotFoundException extends RuntimeException {
    public PollingStationNotFoundException(int id) {
        super("Polling Station with " + id + " not found.");
    }
}
