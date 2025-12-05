import {RegionType} from "@/enums/RegionType";
import {IMetadata} from "@/interfaces/IMetadata";

/**
 * Interface for metadata service.
 */
export interface IMetadataService {
    getByRegionTypeAndId(regionType: RegionType, regionId: string): Promise<IMetadata | null>;
}