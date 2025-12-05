<script lang="ts">
import {defineComponent} from 'vue'
import ComparisonPartyStatCard from "@/components/compare/results/ComparisonPartyStatCard.vue";
import {IPartyVote} from "@/interfaces/IPartyVote";
import {IMetadata} from "@/interfaces/IMetadata";

/**
 * Component to display statistics for selected parties in comparison view
 */
export default defineComponent({
  name: "ComparisonPartyStats",
  components: {ComparisonPartyStatCard},
  inject: ['comparisonState'],
  computed: {
    /**
     * Get selected party votes from comparison state
     */
    selectedPartyVotes(): IPartyVote[] {
      return this.comparisonState.selectedPartyVotes;
    },
    /**
     * Get metadata from comparison state
     */
    metadata(): IMetadata[] {
      return this.comparisonState.metadata;
    }
  }
})
</script>

<template>
  <ul class="grid gap-3 w-full grid-cols-[repeat(auto-fit,minmax(350px,1fr))]">
    <!--    Loop through selectedPartyVotes and render ComparisonPartyStatCard for each-->
    <li v-for="partyVote in selectedPartyVotes"
        :key="partyVote.partyId"
        class="w-full">
      <ComparisonPartyStatCard
          :partyVote="partyVote"
          :metadata="metadata"/>
    </li>
  </ul>
</template>


