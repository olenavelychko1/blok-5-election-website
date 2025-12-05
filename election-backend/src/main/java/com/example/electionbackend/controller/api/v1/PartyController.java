package com.example.electionbackend.controller.api.v1;

import com.example.electionbackend.model.Party;
import com.example.electionbackend.service.PartyService;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * The Party controller
 */
@RestController
@RequestMapping("v1/party")
public class PartyController {

    private final PartyService partyService;

    /**
     * Constructor for PartyController.
     *
     * @param partyService - the party service
     */
    public PartyController(PartyService partyService) {
        this.partyService = partyService;
    }

    /**
     * Get paged parties, optionally filtered by election.
     *
     * @param pageable   Spring Data pagination & sorting params (page, size, sort)
     * @param electionId optional election identifier to filter parties
     * @return a page of parties
     */
    @GetMapping
    @Transactional(readOnly = true)
    public List<Party> getParties(
            Pageable pageable,
            @RequestParam(name = "election_id", required = false) String electionId
    ) {
        return this.partyService.getAllWithCandidates(pageable, electionId);
    }

    /**
     * Retrieves the count of parties associated with a specific election ID.
     *
     * @param electionId the ID of the election to filter the parties
     * @return the number of parties associated with the given election ID
     */
    @GetMapping("/count")
    @Transactional
    public Number getCountByElectionId(@RequestParam String electionId) {
        return this.partyService.getCountByElectionId(electionId);
    }
}
