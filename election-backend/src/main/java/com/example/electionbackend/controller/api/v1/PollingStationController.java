package com.example.electionbackend.controller.api.v1;

import com.example.electionbackend.dto.PageResponse;
import com.example.electionbackend.dto.RegionDTO;
import com.example.electionbackend.dto.RegionPartyVoteDTO;
import com.example.electionbackend.interfaces.IPollingStationService;
import com.example.electionbackend.model.PollingStation;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling Polling Station related endpoints.
 */
@RestController
@RequestMapping("/v1/pollingStations")
public class PollingStationController {
    private final IPollingStationService pollingStationService;

    /**
     * Constructor for PollingStationController.
     *
     * @param pollingStationService - the polling station service
     */
    public PollingStationController(IPollingStationService pollingStationService) {
        this.pollingStationService = pollingStationService;
    }

    /**
     * Get Polling Station by ID
     *
     * @param id the ID of the Polling Station
     * @return the RegionDTO, containing ID and name
     */
    @Operation(summary = "Get a polling station by its id")
    @GetMapping("/{id}")
    public ResponseEntity<RegionDTO> getById(@PathVariable int id) {
            RegionDTO ps = pollingStationService.getById(id);
            if (ps == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(ps);
    }

    /**
     * Get all Polling Stations
     *
     * @param pageable - pagination information
     * @return list of RegionDTOs, each containing ID and name of a Polling Station
     */
    @Operation(summary = "Get all polling stations")
    @GetMapping
    public ResponseEntity<PageResponse<RegionDTO>> getAll(
            @PageableDefault(
                    page = 0,
                    size = 100,
                    sort = "name",
                    direction = Sort.Direction.ASC
            ) Pageable pageable, @RequestParam(defaultValue = "2025") int year
    ) {
        Page<RegionDTO> pollingStations = pollingStationService.getAll(pageable, year);
        if (pollingStations == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new PageResponse<>(pollingStations));
    }

    /**
     * Create a new Polling Station
     *
     * @param pollingStation - the Polling Station entity to create
     * @return the created Polling Station entity
     */
    @Operation(summary = "Create a new polling station")
    @PostMapping
    public ResponseEntity<PollingStation> create(@RequestBody PollingStation pollingStation) {
        try {
            PollingStation createdPollingStation = pollingStationService.create(pollingStation);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(createdPollingStation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Update an existing Polling Station
     *
     * @param id             - the ID of the Polling Station to update
     * @param pollingStation - the Polling Station entity to update
     * @return the updated Polling Station entity
     */
    @Operation(summary = "Update an existing polling station")
    @PutMapping("/{id}")
    public ResponseEntity<PollingStation> update(
            @PathVariable int id,
            @RequestBody PollingStation pollingStation) {
        pollingStation.setId(id);
        PollingStation updatedPollingStation = pollingStationService.update(pollingStation);
        return ResponseEntity.ok(updatedPollingStation);
    }

    /**
     * Delete a Polling Station by ID
     *
     * @param id - the ID of the Polling Station to delete
     * @return ResponseEntity with appropriate status code
     */
    @Operation(summary = "Delete a polling station by its id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        pollingStationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

