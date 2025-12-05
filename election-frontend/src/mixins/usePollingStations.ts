import {IMunicipalityService} from "@/interfaces/IMunicipalityService";
import {MunicipalityService} from "@/services/MunicipalityService";
import {IRegion} from "@/interfaces/IRegion";
import {IPollingStationService} from "@/interfaces/IPollingStationService";
import {PollingStationService} from "@/services/PollingStationService";

const service: IPollingStationService = new PollingStationService();

/**
 * Mixin for fetching polling stations.
 */
export default {
    data() {
        return {
            pollingStations: [] as IRegion[],
            pollingStationCount: 0,
            loadingPollingStations: false,
            error: null,
        }
    },

    methods: {
        /**
         * Fetches all polling stations.
         */
        async fetchPollingStations() {
            this.loadingPollingStations = true;
            this.error = null;
            try {
                const response = await service.getAll();
                this.pollingStations = response.content;
                this.pollingStationCount = response.totalElements;
            } catch (err) {
                this.error = err?.message ?? 'An error occurred while fetching polling stations.';
            } finally {
                this.loadingPollingStations = false;
            }
        }
    },

    /**
     * Fetch polling stations when the component is created.
     */
    created() {
        this.fetchPollingStations();
    }
}