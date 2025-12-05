import {RegionType} from "@/enums/RegionType";

export interface IPartyVote {
    regionType: RegionType,
    regionName: string,
    regionId: number,
    partyName: string,
    partyId: number,
    votes: number,
    seats: number
}