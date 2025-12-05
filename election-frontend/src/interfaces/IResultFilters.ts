import {IRegion} from "@/interfaces/IRegion";

/**
 * Interface for ResultFilters
 *
 * province, authority, station are ids, not names
 */
export interface IResultFilters {
    level: string
    year: IRegion | null
    province: string
    authority: string // municipality
    stationId: string   // the numeric database ID (8662)
    stationCode: string // the "0848::SB1"
}