import {IRegion} from "@/interfaces/IRegion";
import {IPageResponse} from "@/interfaces/IPageResponse";

/**
 * Interface for municipality service.
 */
export interface IMunicipalityService {
    getAll(): Promise<IPageResponse<IRegion[]>>;
    getById(id: number): Promise<IRegion | null>;
}