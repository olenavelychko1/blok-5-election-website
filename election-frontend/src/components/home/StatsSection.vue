<script lang="ts">
import {defineComponent} from "vue";
import IconArrow from "@/components/icons/IconArrow.vue";
import IconLocation from "@/components/icons/IconLocation.vue";
import StatCard from "@/components/ui/StatCard.vue";
import IconMunicipality from "@/components/icons/IconMunicipality.vue";
import IconVote from "@/components/icons/IconVote.vue";
import IconGroup from "@/components/icons/IconGroup.vue";
import usePartiesMixin from "@/mixins/usePartiesMixin";
import useMunicipalities from "@/mixins/useMunicipalities";
import usePollingStations from "@/mixins/usePollingStations";
import LoadingState from "@/components/ui/LoadingState.vue";
import ErrorState from "@/components/ui/ErrorState.vue";

/**
 * Component to display election statistics such as number of provinces, municipalities, polling stations, and parties
 */
export default defineComponent({
  name: "StatsSection",
  components: {ErrorState, LoadingState, IconGroup, IconVote, IconMunicipality, StatCard, IconLocation, IconArrow},
  mixins: [usePartiesMixin, useMunicipalities, usePollingStations],
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
        electionId: "TK2025",
      }
    }
  },
  computed: {
    /**
     * Determine if any data is still loading
     */
    statsLoading(): boolean {
      return this.loadingMunicipalities || this.loadingPollingStations || this.loading;
    },
    /**
     * Determine if there was an error loading any data
     */
    statsError(): boolean {
      return this.error;
    },
    /**
     * Statistics to display in the stats section
     */
    stats() {
      return [
        {text: "Provinces", data: 12, icon: "Location", bgGradient: "bg-gradient-to-r from-[#CDE5FB] to-[#BDEFC5]"},
        {
          text: "Municipalities",
          data: this.municipalities.length,
          icon: "Municipality",
          bgGradient: "bg-gradient-to-r from-[#BDEFC5] to-[#CDE5FB]"
        },
        {
          text: "Polling Stations",
          data: this.pollingStationCount,
          icon: "Vote",
          bgGradient: "bg-gradient-to-r from-[#CDE5FB] to-[#FFFAB2]"
        },
        {
          text: "Parties",
          data: this.parties.length,
          icon: "Group",
          bgGradient: "bg-gradient-to-r from-[#FFFAB2] to-[#CDEFCB]"
        }
      ];
    }
  },
  methods: {
    getIconComponent(iconName: string) {
      switch (iconName) {
        case 'Location':
          return IconLocation;
        case 'Municipality':
          return IconMunicipality;
        case 'Vote':
          return IconVote;
        case 'Group':
          return IconGroup;
        default:
          return null;
      }
    }
  }
});
</script>

<template>
  <section class="w-full h-175 bg-[#E2EFFF] my-5 flex flex-col justify-center items-center gap-8">
    <header>
      <h3 class="text-5xl text-dark-blue font-bold">FROM PROVINCE TO PARTY</h3>
      <p class="text-gray-blue text-center mt-4">Explore the election results in depth</p>
    </header>
    <LoadingState v-if="statsLoading"/>
    <ErrorState v-else-if="statsError"/>
    <ul v-else class="flex flex-row gap-5 flex-wrap justify-center items-center">
      <li v-for="(stat, index) in stats" :key="index">
        <StatCard :text="stat.text" :data="stat.data" :bgGradient="stat.bgGradient">
          <template #icon>
            <component :is="getIconComponent(stat.icon)" class="w-10 text-dark-blue"/>
          </template>
        </StatCard>
      </li>
    </ul>
    <button class="btn-clear-dark text-dark-blue flex flex-row gap-2 w-54 justify-center items-center">
      <a href="/election-results">
        Election results
      </a>
      <IconArrow class="w-5 rotate-90"/>
    </button>
  </section>
</template>