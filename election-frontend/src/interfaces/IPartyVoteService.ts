import {IPartyVote} from "@/interfaces/IPartyVote";
import {RegionType} from "@/enums/RegionType";

/**
 * Interface for party vote service.
 */
export interface IPartyVoteService {
    getByPartyId(partyId: number): Promise<IPartyVote[]>;
    getByRegionType(regionType: RegionType): Promise<IPartyVote[]>;
    getByRegionTypeAndId(regionType: RegionType, id: number): Promise<IPartyVote[]>;
    getByRegionTypeAndIdAndPartyId(regionType: RegionType, id: number, partyId: number): Promise<IPartyVote[]>;
}