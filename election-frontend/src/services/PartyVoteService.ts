import {IPartyVoteService} from "@/interfaces/IPartyVoteService";
import {IPartyVote} from "@/interfaces/IPartyVote";
import {RegionType} from "@/enums/RegionType";

/**
 * Service for fetching party vote data from the backend API.
 */
export class PartyVoteService implements IPartyVoteService {
    private url: string = import.meta.env.VITE_API_URL as string;

    public async getByPartyId(partyId: number): Promise<IPartyVote[]> {
        return Promise.resolve(undefined);
    }

    /**
     * Fetches party votes by region type and ID from the backend API.
     * @param regionType - The type of the region (e.g., MUNICIPALITY, COUNTY).
     * @param id - The ID of the region.
     * @returns A promise that resolves to an array of IPartyVote objects.
     */
    public async getByRegionTypeAndId(regionType: RegionType, id: number): Promise<IPartyVote[]> {
        const response: Response = await fetch(`${this.url}/partyVotes/region/${regionType}/${id}`, {
            method: 'GET',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json'
            }
        })

        if (!response.ok) {
            throw new Error('Failed to fetch party votes by region type and party ID');
        }

        return await response.json();
    }

    /**
     * Fetches party votes by region type, ID, and party ID from the backend API.
     * @param regionType
     * @param id
     * @param partyId
     */
    public async getByRegionTypeAndIdAndPartyId(regionType: RegionType, id: number, partyId: number): Promise<IPartyVote[]> {
        const response: Response = await fetch(`${this.url}/partyVotes/region/${regionType}/${id}/party/${partyId}`, {
            method: 'GET',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json'
            }
        })

        if (!response.ok) {
            return [];
        }
        return await response.json();
    }

    public async getByRegionType(regionType: RegionType): Promise<IPartyVote[]> {
        return Promise.resolve([]);
    }

}