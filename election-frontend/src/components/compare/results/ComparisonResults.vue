<script lang="ts">
import {defineComponent} from 'vue'
import ComparisonPartyStats from "@/components/compare/results/ComparisonPartyStats.vue";
import ComparisonCharts from "@/components/compare/results/ComparisonCharts.vue";
import IconResult from "@/components/icons/IconResult.vue";
import ComparisonTable from "@/components/compare/results/ComparisonTable.vue";
import ComparisonSorter from "@/components/compare/ComparisonSorter.vue";
import LoadingState from "@/components/ui/LoadingState.vue";
import ErrorState from "@/components/ui/ErrorState.vue";

/**
 * Component to display comparison results including stats, charts, and table
 */
export default defineComponent({
  name: "ComparisonResults",
  components: {
    ErrorState,
    LoadingState, ComparisonSorter, ComparisonTable, IconResult, ComparisonCharts, ComparisonPartyStats},
  inject: ["partyVotesState"],
  /**
   * Provide method to transform party names to image names for child components
   */
  provide() {
    return {
      transformNameToImageName: this.transformNameToImageName
    }
  },
  methods: {
    /**
     * Transform party name to image name by replacing '/' with '-'
     * @param name - original party name
     * @returns transformed image name
     */
    transformNameToImageName(name: string): string {
      return name.replace('/', '-')
    }
  },
  /**
   * Computed properties to determine loading and error states
   */
  computed: {
    isLoading(): boolean {
      return this.partyVotesState.loading;
    },
    isError(): boolean {
      return this.partyVotesState.error;
    }
  }
})
</script>

<template>
  <section class="bg-light shadow-xl rounded-2xl w-full lg:w-3/4 p-4 sm:p-6 md:p-8 lg:p-10 flex flex-col gap-3 items-center">
    <header class="flex flex-col sm:flex-row justify-between items-start sm:items-center w-full gap-3 sm:gap-0">
      <div class="flex flex-row items-center gap-2">
        <IconResult class="text-dark-blue w-5 sm:w-6 md:w-7"/>
        <h2 class="font-semibold text-lg sm:text-xl md:text-2xl">Comparison Results</h2>
      </div>
      <ComparisonSorter/>
    </header>
    <LoadingState v-if="isLoading" class="h-96 sm:h-125 md:h-150" />
    <ErrorState v-else-if="isError" />
    <div v-else class="w-full flex flex-col gap-3">
      <ComparisonPartyStats/>
      <ComparisonCharts/>
      <ComparisonTable/>
    </div>
  </section>
</template>


