<script lang="ts">
import {defineComponent} from 'vue'
import PartyCard from "@/components/home/PartyCard.vue";
import usePartyVotes from "@/mixins/usePartyVotes";
import usePartiesMixin from "@/mixins/usePartiesMixin";
import {IPartyVote} from "@/interfaces/IPartyVote";
import {RegionType} from "@/enums/RegionType";
import {IParty} from "@/interfaces/IParty";
import IconArrow from "@/components/icons/IconArrow.vue";
import LoadingState from "@/components/ui/LoadingState.vue";
import ErrorState from "@/components/ui/ErrorState.vue";
import {ITopParty} from "@/interfaces/ITopParty";
import useElectionFiltersMixin from "@/mixins/useElectionFiltersMixin";
import {IRegion} from "@/interfaces/IRegion";
import useElectionYears from "@/mixins/useElectionYears.vue";

/**
 * TopParties component that displays the three parties with the most votes.
 * It fetches party votes and party details using mixins and shows loading and error states.
 */
export default defineComponent({
  name: "TopParties",
  components: {ErrorState, LoadingState, IconArrow, PartyCard},
  mixins: [usePartyVotes, usePartiesMixin, useElectionYears],
  data() {
    return {
      topParties: [] as IPartyVote[],
      electionYears: [] as IRegion[],
      // Override the filter object from the party mixin to fetch parties for the 2025 election
      filter: {
        page: 1,
        size: 50,
        sort: [{
          property: 'id',
          direction: 'asc'
        }],
        electionId: "TK2025",
      }
    }
  },
  /**
   * Fetch the top three parties based on votes when the component is created
   */
  async created() {
    // Set filter size to 50 to ensure we get enough parties to choose from
    this.filter.size = 50;

    // Fetch election years first
    const { electionYears } = await this.loadElectionYears();
    this.electionYears = electionYears;

    const mostRecentYear = electionYears.length > 0
        ? electionYears[electionYears.length - 1]
        : null;

    // Gets the party votes for the most recent election year
    if (mostRecentYear) {
      const partyVotes: IPartyVote[] = await this.fetchPartyVotesByRegionAndId(
          RegionType.NATIONAL,
          mostRecentYear.id
      );

      // Sort the parties by votes in descending order and take the top three
      this.topParties = partyVotes.sort((a, b) => b.votes - a.votes).slice(0, 3);
    }
  },
  methods: {
    /**
     * Get the leader of a party by its name
     * @param partyName - name of the party
     */
    getPartyLeader(partyName: string): string {
      // Find the party by name in the parties list
      const party: IParty = this.parties.find((p: IParty) => p.name === partyName);
      // Return the leader's initials and last name if available
      if (party && party.candidates && party.candidates.length > 0) {
        const leader = party.candidates[0];
        return `${leader.initials} ${leader.lastName}`;
      }
      return '';
    },
    /**
     * Get the CSS class for ordering the party cards based on their place
     * @param place - place of the party (1, 2, or 3)
     */
    getOrderClass(place: number): string {
      switch (place) {
        case 1:
          return 'order-2 xl:order-2'
        case 2:
          return 'order-1 xl:order-1'
        case 3:
          return 'order-3 xl:order-3'
        default:
          return ''
      }
    }
  },
  /**
   * Computed properties for the top three parties and their leaders
   */
  computed: {
    topThreeParties(): ITopParty[] {
      return this.topParties.map((party: IPartyVote, index: number) => {
        const topParty: ITopParty = {
          party: party,
          leader: this.getPartyLeader(party.partyName),
          place: index + 1
        };
        return topParty;
      })
    }
  }
})
</script>

<template>
  <section class="w-3/4 flex flex-col items-center justify-center m-5 my-15 gap-5">
    <h2 class="text-5xl text-dark-blue font-bold">LARGEST PARTIES</h2>
    <p class="text-gray-blue">The three parties with the most votes in 2025</p>
    <LoadingState v-if="loading || loadingPartyVotes"/>
    <ErrorState v-else-if="error"/>
    <ol class="flex xl:flex-row gap-10 relative mt-25 flex-col">
      <li v-for="topParty in topThreeParties" :key="topParty.party.partyName" :class="getOrderClass(topParty.place)">
        <PartyCard :topParty="topParty"/>
      </li>
    </ol>
    <div>
      <button class="btn-clear-dark text-dark-blue flex flex-row gap-2 w-54 justify-center items-center">
        <router-link to="/parties">
          View all parties
        </router-link>
        <IconArrow class="w-5  rotate-90"/>
      </button>
    </div>
  </section>
</template>
