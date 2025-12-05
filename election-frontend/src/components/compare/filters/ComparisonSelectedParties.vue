<script lang="ts">
import {defineComponent} from 'vue'
import IconMunicipality from "@/components/icons/IconMunicipality.vue";
import IconCross from "@/components/icons/IconCross.vue";
import {IParty} from "@/interfaces/IParty";
import {IPartyVote} from "@/interfaces/IPartyVote";

/**
 * Component to display selected parties for comparison with option to remove them
 */
export default defineComponent({
  name: "ComparisonSelectedParties",
  components: {IconCross, IconMunicipality},
  inject: ["comparisonState", "updateSelectedParties"],
  /**
   * Check if no parties are selected
   */
  computed: {
    /**
     * Returns true if no parties are selected for comparison
     */
    hasNoPartiesSelected() {
      return this.comparisonState.selectedParties.length === 0;
    },
    selectedParties() {
      return this.comparisonState.selectedParties;
    }
  },
  methods: {
    /**
     * Remove a party from the selected parties and its votes
     * Emit the updated selected parties and votes to parent (ComparisonFilters)
     * @param party - party to remove
     */
    removeParty(party: IParty) {
      const newParties = this.comparisonState.selectedParties.filter((p: IParty) => p.id !== party.id);
      const newVotes = this.comparisonState.selectedPartyVotes.filter((v: IPartyVote) => v.partyId !== party.id);
      this.updateSelectedParties(newParties, newVotes);
    }
  }
})
</script>

<template>
  <section class="bg-light-blue min-h-20 h-auto w-full rounded-xl flex p-5">
    <!-- Empty state -->
    <section v-if="hasNoPartiesSelected" class="text-gray-blue flex items-center justify-center flex-col m-auto p-8 text-center">
      <IconMunicipality class="w-15 text-dark-blue"/>
      <h2 class="text-dark-blue font-semibold">No parties selected. Please select parties to compare.</h2>
      <p class="text-gray-blue text-sm font-100">Select at least two parties to compare.</p>
    </section>

    <!-- Selected parties list -->
    <ul v-else class="m-auto flex flex-row flex-wrap items-center justify-center gap-2">
      <li v-for="party in selectedParties" :key="party.id">
        <button
            @click="removeParty(party)"
            class="bg-blue-selected border-gray-blue border-1 px-3 py-2 rounded-lg cursor-pointer flex justify-between
                 items-center hover:bg-light-red hover:border-red text-gray-blue hover:text-red
                 transition-colors text-xs">
          <span>{{ party.name }}</span>
          <IconCross class="w-6"/>
        </button>
      </li>
    </ul>
  </section>
</template>

