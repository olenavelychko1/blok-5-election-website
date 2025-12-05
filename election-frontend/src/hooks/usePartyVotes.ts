import {ref, Ref} from "vue";
import {IPartyVote} from "@/interfaces/IPartyVote";
import {IPartyVoteService} from "@/interfaces/IPartyVoteService";
import {PartyVoteService} from "@/services/PartyVoteService";
import {RegionType} from "@/enums/RegionType";

/**
 * Custom hook to fetch and manage party votes data by region type and ID.
 * @param regionType - The type of the region.
 * @param id - The ID of the region.
 */
export async function usePartyVotesByRegionAndId(regionType: RegionType, id: number): Promise<{ partyVotes: Ref<IPartyVote[]> }> {
    const partyVotes: Ref<IPartyVote[]> = ref<IPartyVote[]>([]);
    const partyVoteService: IPartyVoteService = new PartyVoteService();
    partyVotes.value = await partyVoteService.getByRegionTypeAndId(regionType, id);
    return {partyVotes};
}

/**
 * Custom hook to fetch and manage party votes data by region type, ID, and party ID.
 * @param regionType - The type of the region.
 * @param id - The ID of the region.
 * @param partyId - The ID of the party.
 */
export async function usePartyVotesByRegionAndIdAndPartyId(regionType: RegionType, id: number, partyId: number): Promise<{ partyVotes: Ref<IPartyVote[]> }> {
    const partyVotes: Ref<IPartyVote[]> = ref<IPartyVote[]>([]);
    const partyVoteService: IPartyVoteService = new PartyVoteService();
    partyVotes.value = await partyVoteService.getByRegionTypeAndIdAndPartyId(regionType, id, partyId);
    return {partyVotes};
}