<script lang="ts">

import {defineComponent} from 'vue';
import {IPartyVote} from "@/interfaces/IPartyVote";
import IconEcosystem from "@/components/icons/IconEcosystem.vue";
import IconList from "@/components/icons/IconList.vue";
import IconLevel from "@/components/icons/result-filter-panel/IconLevel.vue";
import MetadataCard from "@/components/election-results/MetadataCard.vue";
import {IMetadata} from "@/interfaces/IMetadata";
import IconChair from "@/components/icons/IconChair.vue";
import MetadataGrid from "@/components/election-results/MetadataGrid.vue";
import PartyResultsHeader from './PartyResultsHeader.vue';
import PartyResultsList from './PartyResultsList.vue';

export default defineComponent({
  name: "PartyResultsDisplayer",
  components: {
    MetadataGrid,
    MetadataStats: MetadataGrid,
    IconChair,
    MetadataCard,
    IconLevel,
    IconList,
    IconEcosystem,
    PartyResultsHeader,
    PartyResultsList
  },
  props: {
    results: {
      type: Array as () => IPartyVote[],
      required: true,
      default: () => []
    },
    loading: {
      type: Boolean,
      default: false
    },
    error: {
      type: String,
      default: null
    },
    metadata: {
      type: Object as () => IMetadata | null,
      default: null
    },
    mLoading: {
      type: Boolean,
      default: false
    },
    mError: {
      type: String,
      default: null
    },
    // these names will come from the LevelSelector -> the parent (ElectionResultsPage) and then here
    filterNames: {
      type: Object as () => { province?: string; authority?: string; station?: string; year?: string },
      required: false,
      default: () => ({})
    }
  }
})
</script>

<template>

  <section
      class=" h-full flex flex-col justify-center items-center rounded-2xl shadow-[0rem_0.25rem_0.5rem_rgba(0,0,0,0.25)] transition-shadow hover:shadow-[0rem_0.25rem_0.5rem_rgba(0,0,0,0.35)] ml-1">
    <!-- Displaying the top header - name -->
    <PartyResultsHeader
        :filter-names="filterNames"
        :metadata="metadata"
    />


    <!-- Displaying metadata -->
    <MetadataGrid
        :metadata="metadata"
        :mLoading="mLoading"
        :mError="mError"
    />

    <div class="w-[85%] border-t border-dark-blue  my-3"></div>

    <!--    Displaying the selected parties-->
    <div
        class="relative w-full transition-opacity duration-500"
        :class="loading ? 'min-h-64' : 'min-h-[0]'">
      <div v-if="error" class="text-center text-red-500">{{ error }}</div>
      <ul v-else class="divide-y divide-gray-200 w-full overflow-hidden transition-opacity duration-300">
        <PartyResultsList
            :results="results"
            :metadata="metadata"
        />
      </ul>
    </div>

  </section>


</template>

<style scoped>
</style>