import {IRegion} from "@/interfaces/IRegion";
import {onMounted, Ref, ref} from "vue";
import {IPollingStationService} from "@/interfaces/IPollingStationService";
import {PollingStationService} from "@/services/PollingStationService";
import {IPartyVote} from "@/interfaces/IPartyVote";

/**
 * Custom hook to fetch and manage polling stations data.
 * @returns An object containing a reactive reference to an array of polling stations.
 */
export async function usePollingStations(): Promise<{ pollingStations: Ref<IRegion[]> }> {
    const pollingStations: Ref<IRegion[]> = ref<IRegion[]>([])
    const pollingStationService: IPollingStationService = new PollingStationService();
    pollingStations.value = await pollingStationService.getAll();
    return {pollingStations};
}