import {ElectionResultsService} from "@/services/electionResultsService"
import {IResultFilters} from "@/interfaces/IResultFilters";
import {IPartyVote} from "@/interfaces/IPartyVote";
import {IMetadataService} from "@/interfaces/IMetadataService";
import {MetadataService} from "@/services/MetadataService";
import {RegionType} from "@/enums/RegionType";
import {IMetadata} from "@/interfaces/IMetadata";

const electionResultsService = new ElectionResultsService();
const metadataService: IMetadataService = new MetadataService();

export default {
    /**
     * This component is responsible for:
     * - Tracking filter changes (level, year, region)
     * - Requesting election data from the backend
     * - Displaying loading and error states
     *
     * It reacts automatically whenever the user changes filters in the parent component.
     */
    data(): {
        filters: IResultFilters;
        results: IPartyVote[];
        metadata: IMetadata | null;
        loading: boolean;
        error: string | null;
        mLoading: boolean;
        mError: string | null;
    } {
        return {
            filters: {
                level: 'National',
                year: null,
                province: '',
                authority: '',
                stationId: '',
                stationCode: ''
            },
            results: [],
            loading: false,
            error: null, // stores an error message if fetching fails

            // metadata
            metadata: null,
            mLoading: false,
            mError: null
        };
    },

    methods: {
        /**
         * Loads election data from the backend based on the current filters.
         * Automatically determines which region ID (if any) should be sent.
         */
        async loadElectionData(this: {
            filters: IResultFilters;
            results: IPartyVote[];
            loading: boolean;
            error: string | null;
        }): Promise<void> {
            // Only fetch if minimal filters are filled
            if (!this.filters.level || !this.filters.year) return;

            this.loading = true;
            this.error = null;

            try {
                // Decide name parameter based on level
                const id =
                    this.filters.level === 'Municipality'
                        ? this.filters.authority
                        : this.filters.level === 'Constituency'
                            ? this.filters.province
                            : this.filters.level === 'Polling station'
                                ? this.filters.stationId
                                : this.filters.level === 'National'
                                    ? this.filters.year.id
                                    : null;
                this.results = (await electionResultsService.fetchElectionData(
                    this.filters.level.toLowerCase(),
                    id
                )) as IPartyVote[];

                // console.log(this.results);
            } catch (err) {
                console.error(err);
                this.error = 'Failed to fetch election data';
            } finally {
                this.loading = false;
            }
        },

        /**
         * Loads election metadata from the backend based on the current filters.
         * Automatically determines which region ID (if any) should be sent.
         */
        async loadMetadata(this: any): Promise<void> {
            if (!this.filters.level) return;

            this.mLoading = true;
            this.mError = null;

            try {
                const regionId =
                    this.filters.level === 'National'
                        ? this.filters.year?.id
                        : this.filters.level === 'Constituency'
                            ? this.filters.province
                            : this.filters.level === 'Municipality'
                                ? this.filters.authority
                                : this.filters.stationId;

                const regionType: RegionType = electionResultsService.mapLevelToRegionType(this.filters.level)
                console.log('Found type' + regionType);

                this.metadata = (await metadataService.getByRegionTypeAndId(
                    regionType,
                    regionId
                ))[0];
            } catch (err: any) {
                console.error(err);
                this.mError = 'Failed to fetch metadata';
            } finally {
                this.mLoading = false;
            }
        }
    },
    // this watcher will watch for changes in the filters object in the parent component, like ElectionResultsPage.vue
    watch: {
        filters: {
            async handler(this: any) {
                await this.loadElectionData();
                await this.loadMetadata();
            },
            deep: true // looks also for changes in nested properties
        }
    },
    mounted(this: any) {
        // default data is loaded when the component mounts
        this.loadElectionData();
        this.loadMetadata();
    }
};
