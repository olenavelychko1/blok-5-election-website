import {IPartyVoteService} from "@/interfaces/IPartyVoteService";
import {PartyVoteService} from "@/services/PartyVoteService";
import {IPartyVote} from "@/interfaces/IPartyVote";
import {RegionType} from "@/enums/RegionType";
import {IParty} from "@/interfaces/IParty";

const service: IPartyVoteService = new PartyVoteService();

/**
 * Mixin for fetching party votes based on region type, region id, and party.
 */
export default {
    data() {
        return {
            loadingPartyVotes: false,
            error: null,
            loadingParties: {} as Record<number, boolean> // Track loading state for each party by party ID
        }
    },
    // If there is no inject provided, use default no-op functions
    inject: {
        updatePartyVotesLoadingState: { default: () => () => {} },
        updatePartyVoteErrorState: { default: () => () => {} }
    },

    methods: {
        /**
         * Fetches party votes for a given region type, region ID, and party.
         * @param regionType - The type of the region (e.g., MUNICIPALITY, NATIONAL).
         * @param regionId - The ID of the region.
         * @param party - The party for which to fetch votes.
         */
        async fetchPartyVotes(regionType: RegionType, regionId: number, party: IParty) {
            // Prevent duplicate loading for the same party
            if (this.loadingParties[party.id]) {
                return [];
            }
            this.loadingParties[party.id] = true;
            this.updatePartyVotesLoadingState(true)
            this.updatePartyVoteErrorState(false)
            try {
                let votes: IPartyVote[] = await service.getByRegionTypeAndIdAndPartyId(regionType, regionId, party.id);
                // If no votes are returned, create a default entry with 0 votes
                if (!votes || votes.length === 0) {
                    votes = [{
                        regionType: regionType,
                        regionName: '',
                        regionId: regionId,
                        partyName: party.name,
                        partyId: party.id,
                        votes: 0,
                        seats: 0
                    }];
                }
                return votes;
            } catch (err) {
                this.error = err?.message ?? 'An error occurred while fetching party votes.';
                this.updatePartyVoteErrorState(true)
                return [];
            } finally {
                // Update loading state, and remove party from loadingParties
                this.updatePartyVotesLoadingState(false)
                this.loadingParties[party.id] = false;
            }
        },
        /**
         * Fetches party votes for a given region type and region ID.
         * @param regionType - The type of the region (e.g., MUNICIPALITY, NATIONAL).
         * @param regionId - The ID of the region.
         */
        async fetchPartyVotesByRegionAndId(regionType: RegionType, regionId: number) {
            this.loadingPartyVotes = true;
            this.error = null;
            try {
                let votes: IPartyVote[] = await service.getByRegionTypeAndId(regionType, regionId);
                // If no votes are returned, create a default entry with 0 votes
                if (!votes || votes.length === 0) {
                    votes = [{
                        regionType: regionType,
                        regionName: '',
                        regionId: regionId,
                        partyName: '',
                        partyId: 0,
                        votes: 0,
                        seats: 0
                    }];
                }
                return votes;
            } catch (err) {
                this.error = err?.message ?? 'An error occurred while fetching party votes by region and id.';
                return [];
            } finally {
                this.loadingPartyVotes = false;
            }
        }
    }
}