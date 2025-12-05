package nl.hva.ict.sm3.backend.api;

import nl.hva.ict.sm3.backend.model.*;
import nl.hva.ict.sm3.backend.service.ConstituencyService;

import nl.hva.ict.sm3.backend.utils.types.KieskringType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Constituency controller that loads the election data from the backend,
 * like constituency party votes, candidate votes and metadata.
 */
@RestController
@RequestMapping("constituencies")
public class ConstituencyController {
    private final ConstituencyService constituencyService;

    /**
     * Constructor for ConstituencyController
     *
     * @param constituencyService the service that will be used for loading the election data.
     */

    public ConstituencyController(ConstituencyService constituencyService) {
        this.constituencyService = constituencyService;
    }

    /**
     * Retrieves the full Election object for a specific constituency and year.
     * Example: GET /constituencies/name=Amsterdam&year=2023
     *
     * @param name the name of the constituency (nullable, for all constituencies)
     * @param year the election year (defaults to 2023)
     * @return the full Election object
     */
    @GetMapping
    public Election getConstituencyVotes(
            @RequestParam(required = false) KieskringType name,
            @RequestParam(defaultValue = "2023") int year
    ) {
        return constituencyService.getElection(name, year);
    }

    /**
     * Retrieves the list of party votes for a specific constituency and year.
     * Example: GET /constituencies/partyVotes?name=Amsterdam&year=2023
     *
     * @param name the name of the constituency (e.g. "Amsterdam").
     *             If not specified, all constituencies are returned.
     * @param year the election year
     * @return a list of PartyVote objects containing party vote results
     */
    @GetMapping("/partyVotes")
    public List<RegionPartyVoteDTO> getPartyVotes(
            @RequestParam(required = false) KieskringType name,
            @RequestParam(defaultValue = "2023") int year
    ) {
        return constituencyService.getPartyVotes(name, year);
    }

    /**
     * Retrieves the list of candidate votes for a specific constituency and year.
     * Example: GET /constituencies/candidateVotes?name=Amsterdam&year=2023
     *
     * @param name the name of the constituency (e.g. "Amsterdam")
     *             If not specified, all constituencies are returned.
     * @param year the election year
     * @return a list of CandidateVote objects containing candidate vote results
     */
    @GetMapping("/candidateVotes")
    public List<RegionCandidateVoteDTO> getCandidateVotes(
            @RequestParam(required = false) KieskringType name,
            @RequestParam(defaultValue = "2023") int year
    ) {
        return constituencyService.getCandidateVotes(name, year);
    }

    /**
     * Retrieves metadata for a specific constituency and year.
     * Example: GET /constituencies/metadata?name=Amsterdam&year=2023
     *
     * @param name the name of the constituency (e.g. "Amsterdam")
     *             If not specified, all constituencies are returned.
     * @param year the election year
     * @return a list of ElectionMetadata objects containing metadata information
     */
    @GetMapping("/metadata")
    public List<Metadata> getElectionMetadata(
            @RequestParam(required = false) KieskringType name,
            @RequestParam(defaultValue = "2023") int year
    ) {
        return constituencyService.getElectionMetadata(name, year);
    }
}
