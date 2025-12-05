import {IMetadataService} from "@/interfaces/IMetadataService";
import {IMetadata} from "@/interfaces/IMetadata";

/**
 * Service for fetching metadata from the backend API.
 */
export class MetadataService implements IMetadataService {
    private url: string = import.meta.env.VITE_API_URL as string;

    /**
     * Fetches metadata for a specific region type and ID from the backend API.
     * @param regionType - The type of the region.
     * @param regionId - The ID of the region.
     */
    public async getByRegionTypeAndId(regionType: string, regionId: string): Promise<IMetadata | null> {
        console.log(`${this.url}/metadata/region/${regionType}/${regionId}`); // TODO remove after testing
        const response: Response = await fetch(`${this.url}/metadata/region/${regionType}/${regionId}`, {
            method: 'GET',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json'
            }
        });
        if (!response.ok) {
            throw new Error('Failed to fetch metadata');
        }
        return await response.json();
    }

    /**
     * Fetches a list of metadata for a specific region type from the backend API.
     * @param regionType - The type of the region.
     */
    public async getByRegionType(regionType: string): Promise<IMetadata | null> {
        const response: Response = await fetch(`${this.url}/metadata/region/${regionType}`, {
            method: 'GET',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json'
            }
        });
        if (!response.ok) {
            throw new Error('Failed to fetch metadata');
        }
        return await response.json();
    }
}