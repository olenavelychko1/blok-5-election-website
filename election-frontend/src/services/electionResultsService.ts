import http from "./httpClient";
import {IResultFilters} from "@/interfaces/IResultFilters";
import {IRegion} from "@/interfaces/IRegion";
import {IMunicipalityPollingStationRegion} from "@/interfaces/IMunicipalityPollingStationRegion";
import {IPartyVote} from "@/interfaces/IPartyVote";
import {RegionType} from "@/enums/RegionType";
import {IPollingStationRegion} from "@/interfaces/IPollingStationRegion";

/**
 * Service responsible for fetching election results and related region filter data from the backend API.
 *
 * This class builds the correct backend endpoint depending on:
 * - the selected administrative level (e.g. national, constituency, municipality)
 * - an optional region ID
 *
 * If no specific region ID is provided, it will fetch data aggregated by region type
 * (e.g., `/v1/partyVotes/region/CONSTITUENCY` for all constituencies).
 *
 * The service provides methods for both:
 * - fetching election result data (`fetchElectionData`)
 * - fetching available region filter options (`fetchFilterOptions`)
 */
export class ElectionResultsService {
    private url: string = import.meta.env.VITE_API_URL as string;

    /**
     * Fetches election data from the backend.
     *
     * @async
     * @param {string} level - The administrative level to fetch data for.
     *                         Accepts: 'national', 'constituency', 'municipality', or 'polling-station'.
     * @param {string|number|null} id - Optional region ID to fetch specific region data.
     * @returns {Promise<Object>} The election results data returned by the backend.
     * @throws {Error} When fetching fails or the backend returns an error.
     */
    public async fetchElectionData(level: string, id: string | number | null): Promise<IPartyVote[]> {
        try {
            const path = this.buildPath(level, id);
            console.log("Fetching data...", path);

            const response = await http.get<IPartyVote[]>(path);
            // console.log(response); // all parties
            return response;
        }
        catch (error) {
            console.error("failed to fetch metadata:" + error);
            throw new Error("Failed to fetch metadata" + error);
        }
    }

    /**
     * Builds the backend API path based on the selected level and optional region ID.
     *
     * Example outputs:
     * - `/v1/partyVotes/region/MUNICIPALITY` -> aggregated data for all municipalities
     * - `/v1/partyVotes/region/MUNICIPALITY/12` -> data for municipality with ID 12
     *
     * @param {string} level - The selected administrative level.
     * @param {string|number|null} id - The optional region identifier.
     * @returns {string} The complete API path.
     */
    private buildPath(level: string, id: string | number | null): string {
        const regionType = this.mapLevelToRegionType(level);

        if (!id) {
            // No specific region selected -> fetch aggregated data for that region type
            return `/partyVotes/region/${regionType}`;
        } else {
            return `/partyVotes/region/${regionType}/${id}`;
        }
    }

    /**
     * Maps frontend filter levels to backend `RegionType` enum values.
     *
     * @param {string} level - The user-selected level.
     * @returns {string} The corresponding backend region type.
     */
    public mapLevelToRegionType(level: string): RegionType {
        switch (level.toLowerCase()) {
            case 'municipality': return RegionType.MUNICIPALITY;
            case 'constituency': return RegionType.CONSTITUENCY;
            case 'polling station': return RegionType.POLLING_STATION;
            case 'national': return RegionType.NATIONAL;
            default: return RegionType.NATIONAL;
        }
    }

    /**
     * Builds the backend endpoint for fetching region filters list (for the level selector component),
     * depending on the selected level and parent filters.
     *
     * Example outputs:
     * - level = 'constituency' → `/constituencies?size=15000`
     * - level = 'municipality' → `/constituencies/{provinceId}/municipalities`
     * - level = 'polling-station' → `/municipalities/{authorityId}/pollingStations`
     *
     * @param {string} level - The selected administrative level.
     *                         Accepts: 'constituency', 'municipality', 'polling station'.
     * @param {IResultFilters} filters - The filters used to determine parent region relationships.
     *                                   For example, selected province or municipality ID.
     * @returns {string|Error} The constructed API path, or an Error if the level is invalid.
     */
    private buildFilterListPath(level: string, filters: IResultFilters): string | Error {
        const year = filters.year?.name?.replace('TK', '') || '';
        switch (level.toLowerCase()) {
            case 'constituency':
                return `/constituencies?size=15000&year=${year}`;
            // filter municipalities by province (constituency)
            case 'municipality':
                return `/constituencies/${filters.province}/municipalities`;
            // filter polling stations by municipality
            case 'polling station':
                return `/municipalities/${filters.authority}/pollingStations`;
            default:
                return new Error("Invalid level");
        }
    }

    // ------------------ Level Selector ----------------------------

    /**
     * Fetches available filter options (e.g., municipalities, constituencies, stations)
     * depending on the administrative level selected in the filter panel.
     *
     * Automatically applies parent region filters if needed.
     *
     * Example:
     *  - If level = 'municipality', also passes selected constituency ID.
     *  - If level = 'polling-station', also passes selected municipality ID.
     *
     * @param {string} level - The level for which to fetch available regions.
     *                         Accepted values: 'national', 'constituency', 'municipality', 'polling-station'
     * @param {IResultFilters} filters - Optional filters to apply to the request.
     * @returns {Promise<IRegion[]>} List of regions or filter options.
     */
    public async fetchFilterOptions(
        level: string,
        filters: IResultFilters,
    ): Promise<IRegion[] | IPollingStationRegion[]> {
        try {
            const path = this.buildFilterListPath(level, filters);

            if (path instanceof Error) {
                throw path;
            }

            let response = await http.get<IRegion[] | IMunicipalityPollingStationRegion>(path);

            // has to be converted to IRegion[], because the backend returns the MunicipalityPollingStationRegion object
            if (level === 'Polling station') {
                const municipalityResponse = response as IMunicipalityPollingStationRegion;
                // Convert each polling station to IRegion by picking only id & name
                const list: IPollingStationRegion[] = municipalityResponse.pollingStationList.map(ps => ({
                    id: ps.id,
                    pollingStationId: ps.pollingStationId,
                    name: ps.name
                }));
                // console.log(list);
                return list;
            }
            // Example backend response: [{ id: "123", name: "Zaanstad" }]
            return response as IRegion[];
        } catch (error) {
            console.error(`Failed to fetch filter options for ${level}:`, error);
            throw new Error("Failed to fetch filter options");
        }
    }

    /**
     * Fetches available election years from the backend.
     *
     * @returns {Promise<IRegion>} The election years data returned by the backend.
     * @throws {Error} When fetching fails or the backend returns an error.
     */
    public async fetchElectionYears(): Promise<IRegion> {
        try {
            const response: Response = await fetch(`${this.url}/elections`, {
                method: 'GET',
                credentials: 'include',
                headers: {
                    'Content-Type': 'application/json'
                }
            });
            if (!response.ok) {
                throw new Error('Failed to fetch municipalities');
            }
            return await response.json();
        }
        catch (error) {
            throw new Error("Failed to fetch election years" + error);
        }
    }
}
