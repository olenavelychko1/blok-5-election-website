import {IRegion} from "@/interfaces/IRegion";

export interface IPollingStation extends IRegion {
    pollingStationId: string;
    metadata?: any;
    partyVoteList?: any[];
}

export interface IMunicipalityPollingStationRegion extends IRegion {
    pollingStationList: IPollingStation[];
}
