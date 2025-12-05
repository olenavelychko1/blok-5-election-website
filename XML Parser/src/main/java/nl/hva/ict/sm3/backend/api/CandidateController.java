package nl.hva.ict.sm3.backend.api;

import nl.hva.ict.sm3.backend.model.Party;
import nl.hva.ict.sm3.backend.service.CandidateService;
import nl.hva.ict.sm3.backend.utils.types.KieskringType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Candidate controller class
 */
@RestController
@RequestMapping("candidates")
public class CandidateController {
    private final CandidateService candidateService;

    /**
     * The constructor method of this class which sets the candidateController
     * @param candidateService service of the candidate controller
     */
    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    /**
     *
     * @param kiesring District of the election
     * @param year Year of the election
     * @return List<Party> a list of parties with related candidates
     */
    @GetMapping
    public List<Party> getCandidates(@RequestParam(required = false) KieskringType kiesring, @RequestParam(defaultValue = "2023") int year) {
        return candidateService.getCandidates(kiesring, year);
    }
}