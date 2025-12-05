import {IPartyVote} from "@/interfaces/IPartyVote";

/**
 * Interface representing a top party with its vote details, leader, and place.
 */
export interface ITopParty {
  party: IPartyVote;
  leader: string;
  place: number;
}