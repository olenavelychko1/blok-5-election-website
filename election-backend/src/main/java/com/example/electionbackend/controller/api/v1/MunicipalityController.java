package com.example.electionbackend.controller.api.v1;

import com.example.electionbackend.dto.PageResponse;
import com.example.electionbackend.dto.MunicipalityPollingStationDTO;
import com.example.electionbackend.interfaces.IMunicipalityService;
import com.example.electionbackend.model.Municipality;
import com.example.electionbackend.dto.RegionDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for handling Municipality related endpoints.
 */
@RestController
@RequestMapping("/v1/municipalities")
public class MunicipalityController {
    private final IMunicipalityService municipalityService;

    /**
     * Constructor for MunicipalityController.
     *
     * @param municipalityService - the municipality service
     */
    public MunicipalityController(IMunicipalityService municipalityService) {
        this.municipalityService = municipalityService;
    }


    /**
     * Get Municipality by ID
     *
     * @param id the ID of the Municipality
     * @return the MunicipalityDTO, containing ID and name
     */

    @Operation(summary = "Get a Municipality by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Municipality",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegionDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Municipality not found",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<RegionDTO> getById(@PathVariable int id) {
            RegionDTO m = municipalityService.getById(id);
            return ResponseEntity.ok(m);
    }

    /**
     * Get all Municipalities
     *
     * @param pageable - pagination information
     * @return a list of MunicipalityDTOs, each containing ID and name
     */
    @Operation(summary = "Get all the municipalities")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all the municipalities",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegionDTO.class))}),
            @ApiResponse(responseCode = "404", description = "No municipalities found",
                    content = @Content)})
    @GetMapping
    public ResponseEntity<PageResponse<RegionDTO>> getAll(
            @PageableDefault(
                    page = 0,
                    size = 500,
                    sort = "name",
                    direction = Sort.Direction.ASC
            ) Pageable pageable, @RequestParam(defaultValue = "2025") int year
    ) {
        Page<RegionDTO> municipalities = municipalityService.getAll(pageable, year);
        return ResponseEntity.ok(new PageResponse<>(municipalities));
    }


    /**
     * Get all Polling Stations by Municipality ID
     *
     * @param id the ID of the Municipality
     * @return a list of MunicipalityPollingStationDTOs, each containing ID, name, and polling stations
     */
    @Operation(summary = "Get the municipality with all the polling stations by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the polling stations associated with the Municipality",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = MunicipalityPollingStationDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No polling stations found for the Municipality",
                    content = @Content)})
    @GetMapping("/{id}/pollingStations")
    public ResponseEntity<MunicipalityPollingStationDTO> getAllPollingStationsByMunicipalityId(@PathVariable int id) {
            MunicipalityPollingStationDTO m = municipalityService.getPollingStationsById(id);
            if (m == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(m);
    }

    /**
     * Create a new Municipality
     *
     * @param municipality - the Municipality to create
     * @return the created Municipality
     */
    @Operation(summary = "Create a new municipality")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Municipality created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Municipality.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)})
    @PostMapping
    public ResponseEntity<Municipality> create(@RequestBody Municipality municipality) {
        try {
            Municipality createdMunicipality = municipalityService.create(municipality);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(createdMunicipality);

        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Update an existing Municipality
     *
     * @param id           - the ID of the Municipality to update
     * @param municipality - the Municipality to update
     * @return the updated Municipality
     */
    @Operation(summary = "Update an existing municipality")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Municipality updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Municipality.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<Municipality> update(
            @PathVariable int id,
            @RequestBody Municipality municipality) {
            municipality.setId(id);
            Municipality updatedMunicipality = municipalityService.update(municipality);
            return ResponseEntity.ok(updatedMunicipality);
    }

    /**
     * Delete a Municipality by ID
     *
     * @param id - the ID of the Municipality to delete
     * @return ResponseEntity with appropriate status
     */
    @Operation(summary = "Delete a municipality by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Municipality deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Municipality not found",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
            municipalityService.delete(id);
            return ResponseEntity.noContent().build();
    }
}

