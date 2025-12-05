import {Pageable} from "@/interfaces/IPageable";

/** Spring Data pageable + custom filters */
export interface PartyQuery extends Pageable {
    electionId: string;
}