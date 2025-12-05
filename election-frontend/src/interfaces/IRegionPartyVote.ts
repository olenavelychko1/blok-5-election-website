import { IRegion } from "./IRegion";
import {IPartyVote} from "@/interfaces/IPartyVote";
import {IMetadata} from "@/interfaces/IMetadata";
// TODO: remove ?
export interface IRegionPartyVote extends IRegion {
    partyVotes: IPartyVote[],
    metadata: IMetadata
}