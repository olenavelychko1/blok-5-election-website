import {RegionType} from "@/enums/RegionType";

export interface IMetadata {
    id: number,
    region_type: RegionType,
    region_id: number,
    invalid: number,
    blank: number,
    cast: number,
    total_counted: number,
}