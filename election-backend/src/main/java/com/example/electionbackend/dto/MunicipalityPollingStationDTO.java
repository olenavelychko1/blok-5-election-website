package com.example.electionbackend.dto;

import java.util.List;

/**
 * Data Transfer Object representing a Municipality along with its associated Polling Stations.
 */
public class MunicipalityPollingStationDTO extends RegionDTO {
    private List<PollingStationDTO> pollingStationList;

    public MunicipalityPollingStationDTO(int id, String name, List<PollingStationDTO> pollingStationList) {
        super(id, name);
        this.pollingStationList = pollingStationList;
    }

    public List<PollingStationDTO> getPollingStationList() {
        return pollingStationList;
    }

    public void setPollingStationList(List<PollingStationDTO> pollingStationList) {
        this.pollingStationList = pollingStationList;
    }

    public void addPollingStation(PollingStationDTO pollingStationDTO) {
        if (!this.pollingStationList.contains(pollingStationDTO)) {
            this.pollingStationList.add(pollingStationDTO);
        }
    }
}
