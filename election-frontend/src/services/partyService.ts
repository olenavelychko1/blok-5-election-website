import axios, { AxiosInstance, AxiosResponse } from "axios";
import {PartyQuery} from "@/interfaces/IPartyQuery";

export default class PartyService {
    private readonly api: AxiosInstance;

    constructor() {
        const base = `${import.meta.env.VITE_BACKEND_URL}:${import.meta.env.VITE_BACKEND_PORT}`;

        this.api = axios.create({
            baseURL: `${base}/api/v1/party`,
            headers: { "Content-Type": "application/json" }
        });
    }

    /**
     * Retrieves paginated party data, including electionId as a filter.
     */
    getParties({
                   page,
                   size,
                   sort,
                   electionId
               }: PartyQuery): Promise<AxiosResponse> {

        console.log(sort);

        const sortEntries = sort.map(
            rule => `${rule.property},${rule.direction}`
        );

        return this.api.get("", {
            params: {
                page,
                size,
                sort: sortEntries,
                electionId
            },
            paramsSerializer: params => {
                const qs = new URLSearchParams();

                qs.append("page", String(params.page));
                qs.append("size", String(params.size));

                if (params.electionId) {
                    qs.append("election_id", params.electionId);
                }

                params.sort.forEach((entry: string) => {
                    qs.append("sort", entry);
                });

                return qs.toString();
            }
        });
    }

    /**
     * Counts all parties for a given election.
     */
    getTotalPartiesCount(electionId: string): Promise<AxiosResponse> {
        return this.api.get("/count", { params: { electionId } });
    }
}
