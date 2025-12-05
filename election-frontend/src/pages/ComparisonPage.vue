<script lang="ts">
import {defineComponent} from 'vue'
import ComparisonFilters from "@/components/compare/filters/ComparisonFilters.vue";
import ComparisonResults from "@/components/compare/results/ComparisonResults.vue";
import {IPartyVote} from "@/interfaces/IPartyVote";
import {IMetadata} from "@/interfaces/IMetadata";
import {IParty} from "@/interfaces/IParty";
import {IResultFilters} from "@/interfaces/IResultFilters";

/**
 * ComparisonPage component
 * This component allows users to compare election results of Dutch political parties.
 * It includes filters for selecting parties and displays the comparison results.
 *
 * This page stores the state for selected parties, their votes, and metadata,
 * and provides methods to update this state to child components.
 */
export default defineComponent({
  name: "ComparisonPage",
  components: {ComparisonResults, ComparisonFilters},
  data() {
    return {
      // State for comparison including selected parties, their votes, and metadata
      comparisonState: {
        selectedParties: [] as IParty[],
        selectedPartyVotes: [] as IPartyVote[],
        metadata: [] as IMetadata[],
        filters: {} as IResultFilters,
        sortDesc: true
      },
      partyVotesState: {
        loading: false,
        error: false
      }
    }
  },
  // Provide comparison state and update methods to child components
  provide() {
    return {
      comparisonState: this.comparisonState,
      updateSelectedParties: this.updateSelectedParties,
      updateMetadata: this.updateMetadata,
      partyVotesState: this.partyVotesState,
      updatePartyVotesLoadingState: this.updatePartyVotesLoadingState,
      updatePartyVoteErrorState: this.updatePartyVoteErrorState
    }
  },
  methods: {
    /**
     * Called when selected parties are updated in ComparisonFilters
     * Sorts the selected parties and their votes based on the current sort order
     * @param parties - array of selected parties
     * @param partyVotes - array of party votes for the selected parties
     */
    updateSelectedParties(parties: IParty[], partyVotes: IPartyVote[]) {
      const desc = this.comparisonState.sortDesc;

      // Sort votes
      const sortedVotes = [...partyVotes].sort((a, b) =>
          desc ? b.votes - a.votes : a.votes - b.votes);

      // Sort the parties
      const sortedParties = sortedVotes.map(vote => {
        return parties.find((party: IParty) => party.id === vote.partyId)!;
      });

      this.comparisonState.selectedParties = sortedParties;
      this.comparisonState.selectedPartyVotes = sortedVotes;
    },

    /**
     * Called when metadata is updated in ComparisonFilters
     * @param metadata - updated metadata
     */
    updateMetadata(metadata: IMetadata[]) {
      this.comparisonState.metadata = metadata;
    },
    /**
     * Update loading state for party votes
     * @param isLoading - whether party votes are currently loading
     */
    updatePartyVotesLoadingState(isLoading: boolean) {
      this.partyVotesState.loading = isLoading;
    },
    /**
     * Update error state for party votes
     * @param isError - whether there was an error loading party votes
     */
    updatePartyVoteErrorState(isError: boolean) {
      this.partyVotesState.error = isError;
    }
  },
  computed: {
    /**
     * Check if there are enough parties selected for comparison
     */
    hasEnoughParties(): boolean {
      return this.comparisonState.selectedParties.length >= 2;
    }
  }
})
</script>

<template>
  <section class="min-h-screen flex flex-col items-center mb-20 gap-1">
    <header class="flex items-center flex-col text-center my-10 mb-1 gap-2">
      <h1 class="text-4xl font-bold text-dark-blue">Compare election results</h1>
      <p class="text-md text-gray-blue w-100">Analyze and compare election results of Dutch political parties at
        different levels of government</p>
    </header>
    <!--    The filter components holds the level selector and the party selector-->
    <ComparisonFilters/>

    <!--    The result component shows the comparison results when enough parties are selected-->
    <ComparisonResults v-if="hasEnoughParties"/>
  </section>
</template>
