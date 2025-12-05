<script lang="ts">
import {defineComponent} from 'vue'
import IconTable from "@/components/icons/IconTable.vue";
import {IPartyVote} from "@/interfaces/IPartyVote";
import {IMetadata} from "@/interfaces/IMetadata";
import {RegionType} from "@/enums/RegionType";
import {formatNumber} from "chart.js/helpers";
import usePartyVotes from "@/mixins/usePartyVotes";

/**
 * Component to display a detailed comparison table for selected parties
 */
export default defineComponent({
  name: "ComparisonTable",
  components: {IconTable},
  inject: ['comparisonState', 'transformNameToImageName'],
  mixins: [usePartyVotes],
  data() {
    return {
      nationalPartyVotes: [] as IPartyVote[],
      nationalPartyVotePercentages: {} as Record<number, string>
    }
  },
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
    },
    /**
     * Get the current comparison level from filters
     */
    level(): string {
      return this.comparisonState.filters.level;
    }
  },
  methods: {
    formatNumber,
    /**
     * Get national party votes for a given party ID
     * @param partyId - ID of the party
     */
    getNationalPartyVotes(partyId: number): number {
      const nationalPartyVote: IPartyVote = this.nationalPartyVotes.find((pv: IPartyVote) => pv.partyId === partyId);
      return nationalPartyVote ? nationalPartyVote.votes : 0;
    },
    /**
     * Calculate the vote percentage for a given number of votes
     * @param votes - number of votes for the party
     */
    calculateVotePercentage(votes: number): string {
      const totalVotes: number = this.metadata[0].total_counted;
      return ((votes / totalVotes) * 100).toFixed(2) + '%';
    },
    /**
     * Get party image filename based on party name
     * @param name - name of the party
     */
    getPartyImg(name: string): string {
      return this.transformNameToImageName(name);
    },
    /**
     * Update national party vote percentages for selected parties
     */
    updateNationalPercentages() {
      for (const pv of this.selectedPartyVotes) {
        const totalVotes = this.getNationalPartyVotes(pv.partyId);
        this.nationalPartyVotePercentages[pv.partyId] = ((pv.votes / totalVotes) * 100).toFixed(2) + '%';
      }
    }
  },
  /**
   * Fetch national party votes on component creation
   */
  async created() {
    this.nationalPartyVotes = await this.fetchPartyVotesByRegionAndId(RegionType.NATIONAL, this.comparisonState.filters.year?.id);
    this.updateNationalPercentages();
  },
  /**
   * Watch for changes in selected party votes to update national vote percentages
   */
  watch: {
    selectedPartyVotes: {
      deep: true,
      immediate: true,
      async handler() {
        this.updateNationalPercentages();
      }
    }
  }
})
</script>

<template>
  <section class="bg-light-blue w-full rounded-xl p-5 pb-12">
    <div class="flex flex-row gap-2 items-start mr-auto">
      <IconTable class="text-dark-blue w-7"/>
      <div class="flex flex-col">
        <p class="font-semibold text-2xl text-dark-blue">Detailed Comparison</p>
        <p class="text-gray-blue text-sm">View the detailed comparison in the table below</p>
      </div>
    </div>
    <div class="w-full overflow-x-auto">
      <table class="w-full divide-y divide-gray-300 border border-gray-300 mt-4">
        <thead class="bg-light">
        <tr class="hover:bg-light-blue transition-colors duration-300 hover:font-bold">
          <th class="text-md  text-left text-gray-blue px-6 py-3">PARTY</th>
          <th class="text-md  text-right text-gray-blue px-6 py-3">VOTES</th>
          <th class="text-md  text-right text-gray-blue px-6 py-3">SEATS</th>
          <th class="text-md  text-right text-gray-blue px-6 py-3">VOTE SHARE</th>
          <th v-if="level !== 'National'" class="text-md  text-right text-gray-blue px-6 py-3">NATIONAL PARTY VOTE
            SHARE
          </th>
        </tr>
        </thead>
        <tbody class="bg-white divide-y divide-gray-200">
        <tr v-for="pv in selectedPartyVotes" :key="pv.partyId" class="hover:bg-light transition-all duration-300
          hover:font-bold">
          <td class="px-6 py-4 text-sm font-medium text-dark-blue flex flex-row gap-3 items-center">
            <img
                :src="`/logo's/${getPartyImg(pv.partyName)}.png`"
                :alt="`${pv.partyName} Logo`"
                class="w-10"
            />
            {{ pv.partyName }}
          </td>
          <td class="px-6 py-4 text-sm text-right text-gray-blue">
            {{ formatNumber(pv.votes, "en-US") }}
          </td>
          <td class="px-6 py-4 text-sm text-right text-gray-blue">
            {{ pv.seats }}
          </td>
          <td class="px-6 py-4 text-sm text-right text-gray-blue">
            {{ calculateVotePercentage(pv.votes) }}
          </td>
          <td v-if="level !== 'National'" class="px-6 py-4 text-sm text-right text-gray-blue">
            {{ nationalPartyVotePercentages[pv.partyId] }}
          </td>
        </tr>
        </tbody>
      </table>
    </div>
  </section>
</template>
