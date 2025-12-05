import {IPollingStationService} from "@/interfaces/IPollingStationService";
import {IRegion} from "@/interfaces/IRegion";
import {IPageResponse} from "@/interfaces/IPageResponse";

/**
 * Service for fetching polling station data from the backend API.
 */
export class PollingStationService implements IPollingStationService {
    private url: string = import.meta.env.VITE_API_URL as string;

    /**
     * Fetches all polling stations from the backend API.
     * @returns A promise that resolves to an array of IRegion objects.
     */
    public async getAll(): Promise<IPageResponse<IRegion[]>> {
        const response: Response = await fetch(`${this.url}/pollingStations?size=11000`, {
            method: 'GET',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json'
            }
        });
        if (!response.ok) {
            throw new Error('Failed to fetch polling stations');
        }
        return await response.json();
    }

    public async getById(id: number): Promise<IRegion | null> {
        return Promise.resolve(undefined);
    }

}