import {RegionType} from "@/enums/RegionType";
import {IMetadataService} from "@/interfaces/IMetadataService";
import {MetadataService} from "@/services/MetadataService";
import {IMetadata} from "@/interfaces/IMetadata";

const service: IMetadataService = new MetadataService();
/**
 * Mixin for fetching metadata based on region type and region id.
 */
export default {
    data() {
        return {
            metadata: [] as IMetadata[],
            loadingMetadata: false,
            error: null,
        }
    },

    methods: {
        /**
         * Fetches metadata for a given region type and region ID.
         * @param regionType - The type of the region (e.g., MUNICIPALITY, COUNTY).
         * @param regionId - The ID of the region.
         */
        async fetchMetadata(regionType: RegionType, regionId: string) {
            this.loadingMetadata = true;
            this.error = null;
            try {
                this.metadata = await service.getByRegionTypeAndId(regionType, regionId);
                return this.metadata;
            } catch (err) {
                this.error = err?.message ?? 'An error occurred while fetching metadata.';
            } finally {
                this.loadingMetadata = false;
            }
        }
    }
}