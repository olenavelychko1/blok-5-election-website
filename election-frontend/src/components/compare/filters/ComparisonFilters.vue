<script lang="ts">
import {defineComponent} from 'vue'
import IconOptions from "@/components/icons/IconOptions.vue";
import ComparisonPartySelector from "@/components/compare/filters/ComparisonPartySelector.vue";
import ComparisonSelectedParties from "@/components/compare/filters/ComparisonSelectedParties.vue";
import IconTrash from "@/components/icons/IconTrash.vue";
import IconGroup from "@/components/icons/IconGroup.vue";
import {RegionType} from "@/enums/RegionType";
import ComparisonResetBtn from "@/components/compare/filters/ComparisonResetBtn.vue";
import useMetadata from "@/mixins/useMetadata";
import LevelSelectorComparison from "@/components/compare/filters/LevelSelectorComparison.vue";
import LevelSelectorElectionBase from "@/components/election-results/LevelSelectorElectionBase.vue";
import {IResultFilters} from "@/interfaces/IResultFilters";
import {IMetadata} from "@/interfaces/IMetadata";

/**
 * Component for selecting comparison filters such as parties and options
 */
export default defineComponent({
  name: "ComparisonFilters",
  mixins: [useMetadata],
  components: {
    LevelSelectorElectionBase,
    LevelSelectorComparison,
    ComparisonResetBtn,
    IconGroup,
    IconTrash, ComparisonSelectedParties, ComparisonPartySelector: ComparisonPartySelector, IconOptions
  },
  /**
   * Provide methods to child components
   * - regionAndIdFromFilters: to get region and ID based on current filters
   * - updateFilters: to update filters from child components, like the reset button
   */
  provide() {
    return {
      regionAndIdFromFilters: this.regionAndIdFromFilters,
      updateFilters: this.updateFilters
    }
  },
  /**
   * Inject comparison state and update methods from parent component
   */
  inject: ["comparisonState", "updateMetadata"],
  data() {
    return {
      filters: {
        level: 'National',
        year: null,
        province: '',
        authority: '',
        stationId: '',
        stationCode: ''
      } as IResultFilters
    }
  },
  computed: {
    /**
     * Returns the count of selected parties
     */
    selectedCount(): number {
      return this.comparisonState.selectedParties.length;
    }
  },
  /**
   * Watch for changes in filters and fetch metadata accordingly
   */
  watch: {
    filters: {
      deep: true,
      immediate: true,
      /**
       * Handles changes to the filters by fetching and updating metadata for the selected region and ID
       *
       * @param newFilters - the updated result filters
       */
      async handler(newFilters: IResultFilters) {
        this.comparisonState.filters = {...newFilters}
        const {region, id} = this.regionAndIdFromFilters(newFilters)

        // Fetch metadata for the selected region and ID
        const metadata = await this.fetchMetadata(region, id ?? 0)
        console.log('Fetched metadata for region:', region, 'id:', id, metadata);
        // Update the comparison state with the fetched metadata
        this.updateMetadata(metadata);
      }
    }
  },
  methods: {
    /**
     * Determines the region type and ID based on the provided filters
     * Converts the relevant filter values to correct types
     * This method is provided to child components to fetch data based on current filters
     * @param filters - the result filters
     * @returns an object containing the region type and ID
     */
    regionAndIdFromFilters(filters: IResultFilters): { region: RegionType, id: number } {
      if (filters.stationId) {
        return {region: RegionType.POLLING_STATION, id: Number(filters.stationId)}
      }
      if (filters.authority) {
        return {region: RegionType.MUNICIPALITY, id: Number(filters.authority)}
      }
      if (filters.province) {
        return {region: RegionType.CONSTITUENCY, id: Number(filters.province)}
      }
      return {region: RegionType.NATIONAL, id: Number(filters.year?.id || 0)}
    },
    /**
     * Called when filters are updated in ComparisonFilters
     * This method is provided to child component reset button to reset filters
     * @param filters - updated result filters
     */
    updateFilters(filters: IResultFilters) {
      this.filters = filters;
    }
  }
})
</script>

<template>
  <section class="bg-light shadow-xl rounded-2xl w-full lg:w-3/4 p-4 sm:p-6 md:p-8 lg:p-10 flex flex-col gap-2 items-center justify-center">
    <!--    This section contains the filters for comparing election results and the party selector-->
    <section class="flex flex-col lg:flex-row w-full gap-2 md:gap-4">
      <!--      Level selector-->
      <section class="flex flex-col gap-3 sm:gap-5 w-full lg:w-1/2">
        <header class="flex flex-row gap-2 sm:gap-3 items-center">
          <IconOptions class="text-dark-blue w-5 sm:w-6 md:w-7"/>
          <h2 class="font-semibold text-sm sm:text-base">Select Options</h2>
        </header>
        <LevelSelectorComparison v-model:filters="filters"/>
      </section>
      <!--      Party selector-->
      <section class="flex flex-col gap-3 sm:gap-5 w-full lg:w-1/2 max-h-96 lg:max-h-125 overflow-auto">
        <header class="flex flex-row gap-2 items-center sticky top-0 bg-light z-10">
          <IconGroup class="text-dark-blue w-5 sm:w-6 md:w-7"/>
          <h2 class="font-semibold text-sm sm:text-base">Select parties ({{ selectedCount ?? '?' }}/8)</h2>
        </header>
        <ComparisonPartySelector/>
      </section>
      <!--      This section contains the selected parties and reset button-->
    </section>
    <section class="w-full flex gap-2 flex-col">
      <ComparisonSelectedParties/>
      <ComparisonResetBtn/>
    </section>
  </section>

</template>
