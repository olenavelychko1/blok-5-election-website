import {IRegion} from "@/interfaces/IRegion";
import {IPageResponse} from "@/interfaces/IPageResponse";

/**
 * Interface for polling station service.
 */
export interface IPollingStationService {
    getAll(): Promise<IPageResponse<IRegion[]>>;
    getById(id: number): Promise<IRegion | null>;
}