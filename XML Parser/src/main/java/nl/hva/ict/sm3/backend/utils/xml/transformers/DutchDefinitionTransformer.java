package nl.hva.ict.sm3.backend.utils.xml.transformers;

import nl.hva.ict.sm3.backend.model.Constituency;
import nl.hva.ict.sm3.backend.model.Election;
import nl.hva.ict.sm3.backend.model.Municipality;
import nl.hva.ict.sm3.backend.model.State;
import nl.hva.ict.sm3.backend.utils.types.RegionCategory;
import nl.hva.ict.sm3.backend.utils.xml.DefinitionTransformer;
import nl.hva.ict.sm3.backend.utils.xml.TagAndAttributeNames;

import java.util.Map;

/**
 * Just prints to content of electionData to the standard output.>br/>
 * <b>This class needs heavy modification!</b>
 */
public class DutchDefinitionTransformer implements DefinitionTransformer {
    private final Election election;

    /**
     * Creates a new transformer for handling the structure of the constituencies, municipalities and the parties.
     * It expects an instance of Election that can be used for storing the results.
     * @param election the election in which the votes wil be stored.
     */
    public DutchDefinitionTransformer(Election election) {
        this.election = election;
    }

    /**
     * Registers the constituencies and municipalities.
     * @param electionData the tags and values.
     */
    @Override
    public void registerRegion(Map<String, String> electionData) {
        RegionCategory regionCategory = RegionCategory.valueOf(electionData.get(TagAndAttributeNames.REGION + "-" + TagAndAttributeNames.REGION_CATEGORY));

        switch (regionCategory) {
            case STAAT -> registerState(electionData);
            case KIESKRING -> registerConstituency(electionData);
            case GEMEENTE -> registerMunicipality(electionData);
        }
    }

    /**
     * Registers the state of the election
     * @param electionData the xml election data
     */
    public void registerState(Map<String, String> electionData) {
        String regionName = electionData.get(TagAndAttributeNames.REGION_NAME);

        State state = new State(regionName);
        election.setState(state);
    }

    /**
     * Registers the constituency (Kieskring).
     * @param electionData the xml election data
     */
    public void registerConstituency(Map<String, String> electionData) {
        String regionNumberStr = electionData.get(TagAndAttributeNames.REGION + "-" + TagAndAttributeNames.REGION_NUMBER);
        int regionNumber = Integer.parseInt(regionNumberStr);
        String regionName = electionData.get(TagAndAttributeNames.REGION_NAME);

        Constituency constituency = new Constituency(regionNumber, regionName);
        election.getState().addConstituency(constituency);
    }

    private void registerMunicipality(Map<String, String> electionData) {
        String regionNumberStr = electionData.get(TagAndAttributeNames.REGION + "-" + TagAndAttributeNames.REGION_NUMBER);
        int regionNumber = Integer.parseInt(regionNumberStr);

        String regionParentStr = electionData.get(TagAndAttributeNames.REGION + "-" + TagAndAttributeNames.SUPERIOR_REGION_NUMBER);
        int regionParent = Integer.parseInt(regionParentStr);

        String regionName = electionData.get(TagAndAttributeNames.REGION_NAME);

        Municipality municipality = new Municipality(regionNumber, regionName);
        election.getState().getConstituency(regionParent).addMunicipality(municipality);
    }

    @Override
    public void registerParty(Map<String, String> electionData) {
    }
}
