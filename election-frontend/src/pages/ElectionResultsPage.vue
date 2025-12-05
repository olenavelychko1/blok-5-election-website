<script lang="ts">
import {defineComponent} from 'vue'
import PartyResultsDisplayer from "@/components/election-results/PartyResultsDisplayer.vue";
import useElectionResults from '../mixins/useElectionResults';
import LevelSelectorElectionBase from "@/components/election-results/LevelSelectorElectionBase.vue";
import ElectionCircleDiagram from "@/components/election-results/ElectionCircleDiagram.vue";

export default defineComponent({
  name: "ElectionResultsPage",
  components: {
    ElectionCircleDiagram,
    LevelSelectorElectionBase,
    PartyResultsDisplayer,

  },
  mixins: [useElectionResults],
  // because of the v-model for filters, there is a local filters object that will get update event, and the mixin as a result as well
  data() {
    return {
      // holds the names of the selected filters, incoming from the LevelSelector
      filterNames: {province: '', authority: '', station: '', year: ''}
    }
  },
})
</script>

<template>

  <header class="text-center my-8">
    <h1 class="text-4xl font-bold dark-blue col dark-">Election Results</h1>
    <h5 class="font-thin">Compare election results between parties on national, province, municipality and polling
      station level</h5>
  </header>

  <main class="min-h-screen flex flex-col gap-5 md:gap-0 md:flex-row mb-8">

    <!-- LEFT COLUMN ON DESKTOP / TOP ON MOBILE -->
    <div class="w-full max-w-full md:w-1/3 flex flex-col gap-5 md:order-1 order-1">

      <level-selector-election-base
          v-model:filters="filters"
          @update:filter-names="filterNames = $event"
          class="w-[95.5%] max-w-[95.5%] md:max-w-[93%] order-1 mr-0 md:mr-4"
      />

      <!-- Diagram on desktop goes here under selector -->
      <!-- On mobile this will NOT show here because we change order -->
      <election-circle-diagram
          v-if="results"
          :results="results"
          :metadata="metadata"
          class="hidden md:block max-w-[93%] order-2  p-1.5 ml-4"
      />
    </div>

    <!-- PARTY RESULTS (ALWAYS MIDDLE ON DESKTOP, SECOND ON MOBILE) -->
    <party-results-displayer
        class="w-[95.5%] md:w-2/3 ml-4 mr-4 md:ml-0 md:order-2 order-2"
        :results="results"
        :loading="loading"
        :error="error"
        :filter-names="filterNames"
        :metadata="metadata"
        :m-loading="mLoading"
        :m-error="mError"
    />

    <!-- MOBILE DIAGRAM (placed AFTER party results) -->
    <election-circle-diagram
        v-if="results"
        :results="results"
        :metadata="metadata"
        class="block md:hidden ml-4 max-w-[95.5%] order-3"
    />

    <!-- Map Component -->
    <!--    <results-map-->
    <!--        v-if="results"-->
    <!--        :metadata="metadata"-->
    <!--        :filters="filters"-->
    <!--        class="w-[95.5%] md:w-full order-4"-->
    <!--    />-->


  </main>

</template>


<style scoped>
h1, h5 {
  color: var(--color-dark-blue);
}

</style>