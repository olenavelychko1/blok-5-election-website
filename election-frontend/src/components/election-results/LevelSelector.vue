<script lang="ts">
import {defineComponent} from 'vue'
import IconLevel from "@/components/icons/result-filter-panel/IconLevel.vue";
import {IResultFilters} from "@/interfaces/IResultFilters";
import useElectionFiltersMixin from "@/mixins/useElectionFiltersMixin";
import {IRegion} from "@/interfaces/IRegion";
import LevelSelectorOption from "@/components/election-results/LevelSelectorOption.vue";
import useElectionYears from "@/mixins/useElectionYears.vue";

/**
 * Component responsible for displaying and managing the level selector UI.
 * Includes options for selecting election year, province, municipality, and polling station.
 * Handles loading and updating filter options based on the selected level.
 * Emits events to parent components when filters change.
 *
 * Step plan:
 * 1. click on the level selector -> load the options for the selected level via @click=handleProvinceClick
 * 2. click on the province -> send the filters to the parent components to load data and preload the municipality options for the selected province via the watcher and handleFilterChange
 * 3. repeat for the next steps
 *
 * When a filter is selected, the lower levels are emptied and the filter names are updated in handleFilterChange() and resetDownstream()
 */
export default defineComponent({
  name: "LevelSelector",
  components: {
    LevelSelectorOption,
    IconLevel

  },
  /**
   * Mixin providing shared election filter methods (e.g., loadFilterOptions).
   */
  mixins: [useElectionFiltersMixin, useElectionYears],

  /**
   * Props
   * @prop {IResultFilters} filters - The active filter set passed from the parent component.
   */
  props: {
    filters: {
      type: Object as () => IResultFilters,
      required: true
    }
  },
  data() {
    return {
      // local filters are used to prevent parent from mutating the filters prop directly
      localFilters: {
        ...this.filters as IResultFilters,
        year: null as IRegion | null,
      },
      levels: ['National', 'Constituency', 'Municipality', 'Polling station'],

      // lists that get filled with level options
      years: [] as IRegion[],
      provinces: [] as IRegion[],
      authorities: [] as IRegion[],
      stations: [] as IRegion[],

      loadingFilters: false,
      error: null as string | null,
    }
  },
  mounted() {
    // Load all provinces initially
    this.loadFilterOptions(this.localFilters, 'Constituency').then(({ options }) => {
      this.provinces = options
    })

    this.loadElectionYears().then(({ electionYears }) => {
      this.years = electionYears
      // Set default year to the most recent one
      if (electionYears.length > 0) {
        this.localFilters.year = electionYears[electionYears.length - 1]
        this.emitFilterNames()
        this.emitFilters()
      }
    })



  },
  // this is a deep watcher, so it will watch for changes in the filters object and all its deep nested properties
  watch: {
    /**
     * The watcher keeps localFilters in sync when the parent updates filters (e.g., when resetting).
     */
    filters: {
      deep: true,
      immediate: true,
      handler(newFilters: IResultFilters) {
        // console.log('New filters:', newFilters)
        this.localFilters = {...newFilters}
      },
      // TODO figure out later how to use onWatcherCleanup in my case. perhaps in the mixin watcher?
    },

    // When a constituency is selected -> load all municipalities under this constituency
    'localFilters.province'(newVal, oldVal) {
      // if the new value is empty then handlefilterchange type is natinoal
      console.log('Constituency selected:', newVal)

      // If constituency is cleared, switch back to National level, otherwise load constituency level.
      if (!newVal) {
        this.handleFilterChange('national', newVal, oldVal)
      } else {
        this.handleFilterChange('province', newVal, oldVal)
      }
    },

    // When a municipality is selected -> load all polling stations under this municipality
    'localFilters.authority'(newVal, oldVal) {
      console.log('Municipality selected:', newVal)
      this.handleFilterChange('authority', newVal, oldVal)
    },

    // When a polling station is selected -> update the level
    'localFilters.stationId'(newVal, oldVal) {
      console.log('Polling station selected:', newVal)
      this.handleFilterChange('station', newVal, oldVal)
    },
    'localFilters.year'(newVal, oldVal) {
      console.log('Year selected:', newVal)
      this.handleFilterChange('year', String(newVal?.id || ''), String(oldVal?.id || ''))
    },
  },

  /**
   * Events emitted by this component.
   * @emits update:filters - Fired whenever the local filter state changes.
   */
  emits: ['update:filters', 'update:filter-names'],
  methods: {
    /**
     * Handles when the province/constituency level is clicked.
     * Preloads all available constituency options without changing the current level.
     */
    async handleProvinceClick() {
      const {options} = await this.loadFilterOptions(this.localFilters, 'Constituency')
      this.provinces = options
    },

    /**
     * Handles when the municipality level is clicked.
     * Loads available municipality options and updates the local level.
     */
    async handleMunicipalityClick() {
      const {options} = await this.loadFilterOptions(this.localFilters, 'Municipality')
      this.authorities = options
    },
    /**
     * Handles when the polling station level is clicked.
     * Loads available polling station options and updates the local level.
     */
    async handleStationClick() {
      const {options} = await this.loadFilterOptions(this.localFilters, 'Polling station')
      this.stations = options
    },
    /**
     * Handles changes to the selected election year.
     * Updates the local filter state and emits the updated filters to the parent component.
     *
     * @param {string} yearId - The ID of the newly selected year.
     */
    handleYearChange(yearId: string) {
      const selectedYear = this.years.find(y => String(y.id) === String(yearId))
      this.localFilters.year = selectedYear || null
      this.emitFilterNames(true)
    },

    /**
     * Emits the current local filters to the parent component.
     * Used to synchronize the parent `filters` prop with internal state.
     *
     * @emits update:filters - The current local filters object.
     */
    emitFilters() {
      this.$emit('update:filters', this.filtersWithLevel)
    },

    /**
     * Emits an event to update the display names of the currently selected filters.
     *
     * This method retrieves the names of the selected province, authority, and station
     * based on their IDs in `localFilters`. It then emits an `update:filter-names` event
     * containing those names so that components like {@link PartyResultsDisplayer}
     * can display the correct region hierarchy in their headers.
     *
     * @emits update:filter-names - An object with the selected region names:
     *   {
     *     province: string,
     *     authority: string,
     *     station: string
     *   }
     */
    emitFilterNames(reset?: boolean) {
      // console.log('Emitting filter names for:', regionType, newRegionId)
      // console.log(this.provinces);

      if (reset) {
        this.$emit('update:filter-names', {
          province: '',
          authority: '',
          station: '',
          year: this.localFilters.year.name.replace("TK", '') || ''
        })
        return
      }

      const provinceName: string = this.provinces.find((p: IRegion) => p.id === Number(this.localFilters.province))?.name || '';
      const authorityName: string = this.authorities.find((a: IRegion) => a.id === Number(this.localFilters.authority))?.name || '';
      const stationName: string = this.stations.find((s: IRegion) => s.id === Number(this.localFilters.stationId))?.name || '';
      const yearName: string = this.localFilters.year?.name;

      let updated = {
        province: provinceName,
        authority: authorityName,
        station: stationName,
        year: yearName
      }

      // console.log('Updated filter names:', updated)

      this.$emit('update:filter-names', updated)
    },

    /**
     * Resets all filters back to their initial state (National level, no selections).
     * Also clears any loaded province, authority, and station lists.
     * Called by the parent wrapper (LevelSelectorElectionBase).
     */
    resetFilters() {
      this.localFilters = {level: 'National', year: this.filters.year, authority: '', stationId: '', stationCode: ''}
      this.provinces = []
      this.authorities = []
      this.stations = []
      this.emitFilterNames(true)
      this.emitFilters();
    },

    /**
     * Clears downstream filters when a higher-level filter changes.
     * For example:
     *  - Changing a province clears its municipalities and stations.
     *  - Changing a municipality clears its polling stations.
     *  - Resetting to National level clears all selected filters and select lists
     *
     * @param {'province' | 'authority' | 'national'} from - The level that triggered the reset.
     */
    resetDownstream(from: 'province' | 'authority' | 'national') {
      if (from === 'province' || from === 'national') {
        this.localFilters.authority = ''
        this.localFilters.stationId = ''
        this.localFilters.stationCode = ''
        this.authorities = []
        this.stations = []
      } else if (from === 'authority') {
        this.localFilters.stationId = ''
        this.localFilters.stationCode = ''
        this.stations = []
      }
    },

    /**
     * Handles any change to filter selections.
     * Automatically updates dependent filters and loads new options based on the selected level.
     *
     * @param {string} type - The type of filter that changed ('province', 'authority', 'station').
     * @param {string} newVal - The newly selected value.
     * @param {string} oldVal - The previous value.
     */
    async handleFilterChange(type: string, newVal: string, oldVal: string) {
      if (type === 'national' && newVal !== oldVal) {
        this.resetDownstream('national');
      }

      // Reset dependent selections and load new options as necessary
      if (type === 'province' && newVal !== oldVal) {
        this.resetDownstream('province')

        // Update the displayed names in the PartyResultsDisplayer
        this.emitFilterNames()

        // Pre-fetch municipalities first (for fast access and to avoid white field)
        const { options } = await this.loadFilterOptions({ ...this.localFilters }, 'Municipality');
        this.authorities = options;

      }

      if (type === 'authority' && newVal !== oldVal) {
        this.resetDownstream('authority')

        // Update the displayed names in the PartyResultsDisplayer
        this.emitFilterNames()

        // Pre-fetch polling stations first (for fast access and to avoid white field)
        const { options } = await this.loadFilterOptions({ ...this.localFilters }, 'Polling station');
        this.stations = options;
      }

      if (type === 'station' && newVal !== oldVal) {

        // add the station code to the filters
        const selected = this.stations.find(s => s.id === Number(newVal))
        this.localFilters.stationCode = selected?.pollingStationId || ''

        // Update the displayed names in the PartyResultsDisplayer
        this.emitFilterNames()
        // TODO if a municipality has no polling stations, show "no polling stations text". Example: NBSB in 'd-Gravenhage
      }

      if (type === 'year' && newVal !== oldVal) {
        this.resetDownstream('national')
        const {options} = await this.loadFilterOptions(this.localFilters, 'Constituency')
        this.provinces = options
        this.localFilters.province = ''
      }

      // Always notify the parent after a change
      this.emitFilters()
    },
  },
  computed: {
    autoLevel(): string {
      if (this.localFilters.stationId) return 'Polling station';
      if (this.localFilters.authority) return 'Municipality';
      if (this.localFilters.province) return 'Constituency';
      return 'National';
    },
    filtersWithLevel(): IResultFilters {
      return {
        ...this.localFilters,
        level: this.autoLevel   // inject computed level for use
      };
    }
  }
})
</script>

<template>


  <section id="level-selector">
    <!-- Selectors for level, year, province, authority, station -->
    <div class="bg-light-blue rounded-xl p-4 space-y-4">

      <!-- Level (read-only display) -->
      <div>
        <label class=" mb-1 font-medium flex items-center gap-3">
          <icon-level class="w-5 h-5 flex"/>
          Level
        </label>
        <input
            type="text"
            class="w-full border p-2 rounded-lg bg-light  text-gray-blue"
            :value="autoLevel"
            readonly
        />
      </div>

      <!-- Year  -->
      <LevelSelectorOption
          label="Year"
          icon="Year"
          :options="years"
          :model-value="localFilters.year?.id || ''"
          @update:model-value="handleYearChange"
          placeholder="Select year"
      />

      <!-- province TODO needs to be changed later to province -->
      <!-- Constituency -->
      <LevelSelectorOption
          label="Constituency"
          icon="Province"
          :options="provinces"
          v-model="localFilters.province"
          placeholder="Select constituency"
          @click="handleProvinceClick"

      />

      <!-- local authority - municipality (only shown if constituency selected) -->
      <LevelSelectorOption
          v-show="localFilters.province"
          label="Municipality"
          icon="LocalAuthority"
          :options="authorities"
          v-model="localFilters.authority"
          placeholder="Select municipality"
          @click="handleMunicipalityClick"

      />

      <!-- Polling station (only shown if municipality selected) -->
      <LevelSelectorOption
          v-show="localFilters.authority"
          label="Polling Station"
          icon="PollingStation"
          :options="stations"
          v-model="localFilters.stationId"
          placeholder="Select polling station"
          @click="handleStationClick"

      />
    </div>
  </section>


</template>

<style scoped>

</style>