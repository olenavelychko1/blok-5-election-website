package com.example.electionbackend.controller.api.v1;

import com.example.electionbackend.dto.RegionDTO;
import com.example.electionbackend.dto.RegionPartyVoteDTO;
import com.example.electionbackend.interfaces.IConstituencyService;
import com.example.electionbackend.mapper.ConstituencyMapper;
import com.example.electionbackend.model.Constituency;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling Constituency related endpoints.
 */
@RestController
@RequestMapping("/v1/constituencies")
public class ConstituencyController {

    private final IConstituencyService constituencyService;

    /**
     * Constructor for ConstituencyController.
     *
     * @param constituencyService - the constituency service
     */
    public ConstituencyController(IConstituencyService constituencyService) {
        this.constituencyService = constituencyService;
    }

    /**
     * Get Constituency by ID
     *
     * @param id the ID of the Constituency
     * @return ResponseEntity containing the {@link RegionDTO} (region's ID and name) if found, 404 if not found
     */
    @Operation(summary = "Get a Constituency by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Constituency",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegionDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Constituency not found",
                    content = @Content)})
    @GetMapping("/{id}")
    public ResponseEntity<RegionDTO> getById(@PathVariable int id) {
        try {
            RegionDTO c = constituencyService.getById(id);
            if (c == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(c);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Retrieves all Constituencies with pagination.
     *
     * @param page page number (default 1)
     * @param size number of items per page (default 100)
     * @return ResponseEntity containing list of {@link RegionDTO}s, 404 if none found
     */
    @Operation(summary = "Get all the constituencies")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found all the constituencies",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegionDTO.class))}),
            @ApiResponse(responseCode = "404", description = "No constituencies found",
                    content = @Content)})
    @GetMapping
    public ResponseEntity<List<RegionDTO>> getAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "100") int size,
            @RequestParam(defaultValue = "2025") int year
    ) {
        try {
            List<RegionDTO> constituencies = constituencyService.getAllConstituencies(page, size, year);
            if (constituencies == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(constituencies);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Get all Municipalities by Constituency ID
     *
     * @param id the ID of the Constituency
     * @return a list of RegionDTOs, each containing ID and name of a Municipality
     */
    @Operation(summary = "Get all the municipalities that belong to a specific constituency by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the municipalities associated with the Constituency",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegionDTO.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid id supplied",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "No municipalities found for the Constituency",
                    content = @Content)
    })
    @GetMapping("/{id}/municipalities")
    public ResponseEntity<List<RegionDTO>> getAllMunicipalitiesById(@PathVariable int id) {
        try {
            List<RegionDTO> municipalities = constituencyService.getAllMunicipalitiesById(id);
            if (municipalities == null || municipalities.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(municipalities);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Creates a new Constituency.
     *
     * @param constituency the Constituency to create
     * @return ResponseEntity containing {@link RegionDTO} of the created Constituency with 201 Created
     */
    @Operation(summary = "Create a new constituency")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Constituency created",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Constituency.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)})
    @PostMapping
    public ResponseEntity<RegionDTO> create(@RequestBody Constituency constituency) {
        try {
            Constituency created = constituencyService.createConstituency(constituency);
            RegionDTO dto = ConstituencyMapper.toRegionDTO(created);
            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Updates an existing Constituency.
     *
     * @param id           the ID of the Constituency to update
     * @param constituency the Constituency to update with
     * @return ResponseEntity containing {@link RegionDTO} of the updated Constituency, 404 if not found
     */
    @Operation(summary = "Update an existing constituency")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Constituency updated",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Constituency.class))}),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content)
    })
    @PutMapping("/{id}")
    public ResponseEntity<RegionDTO> update(
            @PathVariable int id,
            @RequestBody Constituency constituency) {
        try {
            constituency.setId(id);
            Constituency updated = constituencyService.updateConstituency(constituency);
            RegionDTO dto = ConstituencyMapper.toRegionDTO(updated);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Deletes a Constituency by its ID.
     *
     * @param id the ID of the Constituency to delete
     * @return ResponseEntity with 204 No Content if deleted, 404 if not found
     */
    @Operation(summary = "Delete a constituency by its id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Constituency deleted",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Constituency not found",
                    content = @Content)
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try {
            constituencyService.deleteConstituency(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}

