import {IMunicipalityService} from "@/interfaces/IMunicipalityService";
import {MunicipalityService} from "@/services/MunicipalityService";
import {IRegion} from "@/interfaces/IRegion";

const service: IMunicipalityService = new MunicipalityService();

/**
 * Mixin for fetching municipalities.
 */
export default {
    data() {
        return {
            municipalities: [] as IRegion[],
            loadingMunicipalities: false,
            error: null,
        }
    },

    methods: {
        /**
         * Fetches all municipalities.
         */
        async fetchMunicipalities() {
            this.loadingMunicipalities = true;
            this.error = null;
            try {
                const response = await service.getAll();
                this.municipalities = response.content;
            } catch (err) {
                this.error = err?.message ?? 'An error occurred while fetching municipalities.';
            } finally {
                this.loadingMunicipalities = false;
            }
        }
    },

    /**
     * Fetch municipalities when the component is created.
     */
    created() {
        this.fetchMunicipalities();
    }
}