package com.example.electionbackend.service;

import com.example.electionbackend.model.Party;
import com.example.electionbackend.repository.interfaces.PartyRepository;
import com.example.electionbackend.service.interfaces.IPartyService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;

/**
 * Service class for managing Party-related operations.
 */
@Service
public class PartyService implements IPartyService {
    private final PartyRepository partyRepository;

    /**
     * Constructor for PartyService.
     *
     * @param partyRepository - the party repository
     */
    public PartyService(PartyRepository partyRepository) {
        this.partyRepository = partyRepository;
    }

    /**
     * Get a party by ID.
     *
     * @param id - the ID of the party to retrieve
     * @return - the party entity, or null if not found
     */
    @Transactional
    public Party get(int id) {
        return partyRepository.find(id);
    }
    /**
     * Save a party entity.
     *
     * @param party - the party entity to save
     * @return - the saved party entity
     */
    @Transactional
    public Party save(Party party) {
        return partyRepository.save(party);
    }

    /**
     * Get all parties with candidates using pageable + optional election filter.
     *
     * @param pageable   pagination and sorting information
     * @param electionId optional election filter
     * @return a page of parties
     */
    @Transactional(readOnly = true)
    public List<Party> getAllWithCandidates(Pageable pageable, String electionId) {
        List<Party> parties = partyRepository.getAllWithCandidates();

        return parties.stream()
                .filter(party -> party.getElection().getId().equals(electionId))
                .sorted(buildPartyComparator(pageable.getSort()))
                .skip((long) (pageable.getPageNumber() - 1) * pageable.getPageSize())
                .limit(pageable.getPageSize())
                .toList();
    }

    /**
     * Retrieves the count of parties associated with a specific election ID.
     *
     * @param electionId the ID of the election to filter the parties
     * @return the number of parties associated with the given election ID
     */
    @Transactional
    public Number getCountByElectionId(String electionId) {
        List<Party> partyList =  partyRepository.getAll();

        return partyList.stream()
                .filter(party -> party.getElection().getId().equals(electionId))
                .count();
    }

    /**
     * Builds a Comparator<Party> based on Spring's Sort definition.
     * Uses reflection to read the property named in Sort.Order.
     */
    private Comparator<Party> buildPartyComparator(Sort sort) {
        Comparator<Party> comparator = null;

        for (Sort.Order order : sort) {
            Comparator<Party> orderComparator = Comparator.comparing(
                    party -> getComparableFieldValue(party, order.getProperty()),
                    Comparator.nullsLast(Comparator.naturalOrder())
            );

            if (order.isDescending()) {
                orderComparator = orderComparator.reversed();
            }

            comparator = (comparator == null)
                    ? orderComparator
                    : comparator.thenComparing(orderComparator);
        }

        return comparator != null ? comparator : (p1, p2) -> 0;
    }

    /**
     * Reads a Comparable field from Party by name using reflection.
     */
    @SuppressWarnings("unchecked")
    private Comparable<Object> getComparableFieldValue(Party party, String fieldName) {
        try {
            Field field = Party.class.getDeclaredField(fieldName);
            field.setAccessible(true);
            return (Comparable<Object>) field.get(party);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException("Cannot sort by field: " + fieldName, e);
        }
    }
}
