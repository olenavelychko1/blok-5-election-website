import {ICandidate} from "@/interfaces/ICandidate";

/**
 * Interface for parties
 */
export interface IParty {
    id: number;
    pid: number;
    name: string;
    seats: number;
    candidates: ICandidate[];
}