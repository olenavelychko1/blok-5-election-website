/**
 * Interface for candidates
 */
export interface ICandidate {
    id: number;
    initials: string;
    firstName: string;
    lastName: string;
    gender: string;
    locality: string;
    elected: boolean;
}