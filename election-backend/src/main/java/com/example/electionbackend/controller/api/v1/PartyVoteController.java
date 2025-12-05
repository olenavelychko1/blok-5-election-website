package com.example.electionbackend.controller.api.v1;

import com.example.electionbackend.dto.PartyVoteDTO;
import com.example.electionbackend.dto.PartyVoteDetailDTO;
import com.example.electionbackend.model.PartyVote;
import com.example.electionbackend.service.PartyVoteService;
import com.example.electionbackend.type.RegionType;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for handling Party Vote related endpoints.
 */
@RestController
@RequestMapping("/v1/partyVotes")
public class PartyVoteController {
    private final PartyVoteService partyVoteService;

    /**
     * Constructor for PartyVoteController.
     *
     * @param partyVoteService - the party vote service
     */
    public PartyVoteController(PartyVoteService partyVoteService) {
        this.partyVoteService = partyVoteService;
    }

    /**
     * Get Party Votes by Party ID
     *
     * @param partyId - the ID of the Party
     * @return - the list of PartyVote associated with the Party ID
     */
    @Operation(summary = "Get the party votes by party id")
    @GetMapping("/party/{partyId}")
    public ResponseEntity<List<PartyVoteDetailDTO>> getPartyVotesByPartyId(
            @PathVariable int partyId,
            @PageableDefault(
                    page = 0,
                    size = 500,
                    sort = "votes",
                    direction = Sort.Direction.DESC
            ) Pageable pageable) {
        List<PartyVoteDetailDTO> votes = partyVoteService.getByPartyId(partyId, pageable);
        return ResponseEntity.ok(votes);
    }

    /**
     * Get Party Votes by Region Type
     *
     * @param regionType - the type of the Region
     * @return - the list of PartyVote associated with the Region Type
     */
    @Operation(summary = "Get the party votes by region type")
    @GetMapping("/region/{regionType}")
    public ResponseEntity<List<PartyVoteDetailDTO>> getPartyVotesByRegionType(
            @PathVariable RegionType regionType,
            @PageableDefault(
                    page = 0,
                    size = 500,
                    sort = "votes",
                    direction = Sort.Direction.DESC
            ) Pageable pageable) {
        List<PartyVoteDetailDTO> votes = partyVoteService.getByRegionType(regionType, pageable);
        return ResponseEntity.ok(votes);
    }

    /**
     * Get Party Votes by Region Type and ID
     *
     * @param regionType - the type of the Region (e.g., MUNICIPALITY, NATIONAL)
     * @param regionId   - the ID of the Region
     * @return - the list of PartyVote associated with the Region Type and ID
     */
    @Operation(summary = "Get the party votes by region type and region id")
    @GetMapping("/region/{regionType}/{regionId}")
    public ResponseEntity<List<PartyVoteDetailDTO>> getPartyVotesByRegionTypeAndId(
            @PathVariable RegionType regionType,
            @PathVariable String regionId,
            @PageableDefault(
                    page = 0,
                    size = 500,
                    sort = "votes",
                    direction = Sort.Direction.DESC
            ) Pageable pageable) {
        List<PartyVoteDetailDTO> votes = partyVoteService.getByRegionTypeAndId(regionType, regionId, pageable);
        return ResponseEntity.ok(votes);
    }

    /**
     * Get Party Votes by Region Type, ID and Party ID
     *
     * @param regionType - the type of the Region (e.g., MUNICIPALITY, NATIONAL)
     * @param regionId   - the ID of the Region
     * @param partyId    - the ID of the Party
     * @return - the list of PartyVote associated with the Region Type, ID and Party
     */
    @GetMapping("/region/{regionType}/{regionId}/party/{partyId}")
    public ResponseEntity<List<PartyVoteDetailDTO>> getPartyVotesByRegionTypeAndIdAndPartyId(
            @PathVariable RegionType regionType,
            @PathVariable String regionId,
            @PathVariable int partyId,
            @PageableDefault(
                    page = 0,
                    size = 500,
                    sort = "votes",
                    direction = Sort.Direction.DESC
            ) Pageable pageable) {
        List<PartyVoteDetailDTO> votes = partyVoteService.getByRegionTypeAndIdAndPartyId(
                regionType, regionId, partyId, pageable);
        return ResponseEntity.ok(votes);
    }

    /**
     * Create a new PartyVote
     *
     * @param partyVote - the PartyVote entity to be created
     * @return - the created PartyVote entity
     */
    @Operation(summary = "Create a new party vote")
    @PostMapping
    public ResponseEntity<PartyVote> create(PartyVote partyVote) {
        try {
            PartyVote createdVote = partyVoteService.create(partyVote);
            return ResponseEntity.ok(createdVote);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Update an existing PartyVote
     *
     * @param id        - the ID of the PartyVote to be updated
     * @param partyVote - the PartyVote entity to be updated
     * @return - the updated PartyVote entity
     */
    @Operation(summary = "Update an existing party vote")
    @PutMapping("/{id}")
    public ResponseEntity<PartyVote> update(
            @PathVariable int id,
            @RequestBody PartyVote partyVote) {
        try {
            partyVote.setId(id);
            PartyVote updatedPartyVote = partyVoteService.update(partyVote);
            return ResponseEntity.ok(updatedPartyVote);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Delete a PartyVote by its ID
     *
     * @param id - the ID of the PartyVote to be deleted
     * @return - ResponseEntity with appropriate status
     */
    @Operation(summary = "Delete a municipality by its id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        try {
            partyVoteService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
