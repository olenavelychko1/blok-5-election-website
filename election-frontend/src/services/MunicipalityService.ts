import {IMunicipalityService} from "@/interfaces/IMunicipalityService";
import {IRegion} from "@/interfaces/IRegion";
import {IPageResponse} from "@/interfaces/IPageResponse";

/**
 * Service for fetching municipality data from the backend API.
 */
export class MunicipalityService implements IMunicipalityService {
    private url: string = import.meta.env.VITE_API_URL as string;

    /**
     * Fetches all municipalities from the backend API.
     * @returns A promise that resolves to an array of IRegion objects.
     */
    public async getAll(): Promise<IPageResponse<IRegion[]>> {
        const response: Response = await fetch(`${this.url}/municipalities?page=0&size=500`, {
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

    public async getById(id: number): Promise<IRegion | null> {
        return null;
    }
}