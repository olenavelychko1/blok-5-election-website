<script lang="ts">
import {defineComponent} from 'vue'
import IconCheckmark from "@/components/icons/IconCheckmark.vue";
import {IPartyVote} from "@/interfaces/IPartyVote";
import {IParty} from "@/interfaces/IParty";
import usePartyVotes from "@/mixins/usePartyVotes";
import usePartiesMixin from "@/mixins/usePartiesMixin";
import {IResultFilters} from "@/interfaces/IResultFilters";
import LoadingState from "@/components/ui/LoadingState.vue";
import ErrorState from "@/components/ui/ErrorState.vue";
import {IRegion} from "@/interfaces/IRegion";

/**
 * Component for selecting parties to compare
 */
export default defineComponent({
  name: "ComparisonPartySelector",
  components: {ErrorState, LoadingState, IconCheckmark},
  /**
   * Inject method to update selected parties and comparison state
   */
  inject: ["updateSelectedParties", "comparisonState", "regionAndIdFromFilters"],
  mixins: [usePartiesMixin, usePartyVotes],
  data() {
    return {
      // OVerwrite the filter to fetch all parties for the 2025 election
      filter: {
        page: 1,
        size: 100,
        sort: [{
          property: 'id',
          direction: 'asc'
        }],
        electionId: this.comparisonState.filters.year?.name || '',
      }
    }
  },
  computed: {
    /**
     * Get array of selected party IDs
     * Used in template to determine if a party is selected
     */
    selectedPartyIds(): number[] {
      return this.comparisonState.selectedParties.map((p: IParty) => p.id);
    }
  },

  /**
   * Watch for changes in comparison filters to update votes for selected parties
   * This ensures that when filters change, the votes for all selected parties are re-fetched
   * For example when national changes to municipality level
   */
  watch: {
    'comparisonState.filters': {
      deep: true,
      handler: async function (newFilters, oldFilters) {
        // Only proceed if the year has not changed, as year change is handled separately
        if (newFilters.year !== oldFilters.year) {
          return;
        }
        await this.updateVotesForSelectedParties();
      }
    },
    'comparisonState.filters.year'(newYear: IRegion | null, oldYear: IRegion | null) {
      if (newYear !== oldYear) {
        // Update the filter's electionId when year changes
        this.filter.electionId = newYear?.name || '';
        // Reset selected parties and votes when year changes
        this.updateSelectedParties([], []);
      }
    },
  },
  methods: {
    /**
     * Toggle party selection
     * If party is already selected, remove it
     * If party is not selected and max parties not reached, add it
     * @param party - party to toggle
     */
    async toggleParty(party: IParty) {
      const isSelected = this.isPartySelected(party);
      if (!isSelected && this.isMaxPartiesReached()) return;

      if (isSelected) {
        this.removeParty(party);
      } else {
        await this.addParty(party);
      }
    },
    /**
     * Check if a party is already selected
     * @param party - party to check
     */
    isPartySelected(party: IParty): boolean {
      return this.comparisonState.selectedParties.some((p: IParty) => p.id === party.id);
    },
    /**
     * Check if maximum number of selectable parties is reached
     */
    isMaxPartiesReached(): boolean {
      return this.comparisonState.selectedParties.length >= 8;
    },
    /**
     * Remove a party from selected parties and its votes
     * @param party - party to remove
     */
    removeParty(party: IParty) {
      const updatedParties = this.comparisonState.selectedParties.filter((p: IParty) => p.id !== party.id);
      const updatedVotes = this.comparisonState.selectedPartyVotes.filter((v: IPartyVote) => v.partyId !== party.id);
      this.updateSelectedParties(updatedParties, updatedVotes);
    },
    /**
     * Add a party to selected parties and fetch its votes
     * @param party - party to add and fetch votes for
     */
    async addParty(party: IParty) {
      // Fetch the party votes for the selected party based on current filters
      const filters = this.comparisonState.filters;
      const {region, id} = this.regionAndIdFromFilters(filters);
      const partyVote = await this.fetchPartyVotes(region, id, party);

      // Update selected parties and votes by calling the injected method
      const updatedParties = [...this.comparisonState.selectedParties, party];
      const updatedVotes = [...this.comparisonState.selectedPartyVotes, ...partyVote];
      this.updateSelectedParties(updatedParties, updatedVotes);
    },
    /**
     * Re-fetch votes for all currently selected parties based on new filters
     * When user changes filters, we need to update votes for all selected parties
     */
    async updateVotesForSelectedParties() {
      const selectedParties: IParty[] = [...this.comparisonState.selectedParties];
      const filters: IResultFilters = this.comparisonState.filters;
      const {region, id} = this.regionAndIdFromFilters(filters);
      const updatedVotes: IPartyVote[] = [];

      for (const party of selectedParties) {
        const partyVotes = await this.fetchPartyVotes(region, id, party);
        updatedVotes.push(...partyVotes);
      }

      this.updateSelectedParties(selectedParties, updatedVotes);
    }
  }
})
</script>

<template>
  <section class="bg-light-blue rounded-xl">
    <LoadingState v-if="loading" class="h-100"/>
    <ErrorState v-else-if="error"/>
    <ul class="grid grid-cols-2 gap-2 px-5 py-8" v-else>
      <!--      Loops through all parties and displays them as selectable buttons-->
      <li v-for="party in parties" :key="party.id">
        <button
            @click="toggleParty(party)"
            :class="[
            selectedPartyIds.includes(party.id) ? 'bg-blue-selected' : 'bg-light',
            'border-1 px-3 py-2 rounded-lg w-full cursor-pointer flex justify-between hover:bg-blue-selected ' +
            'transition-colors duration-300 active:bg-light items-center min-h-12 w-full h-full']">
          <span class="text-gray-blue">{{ party.name }}</span>
          <!--          Icon or plus sign based on selection-->
          <span class="text-gray-blue text-xl">
            <IconCheckmark v-if="selectedPartyIds.includes(party.id)" class="text-gray-blue w-5"/>
            <span v-else>+</span>
          </span>
        </button>
      </li>
    </ul>
  </section>
</template>

