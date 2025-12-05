<script lang="ts">
import {defineComponent} from 'vue';
import IconResults from "@/components/icons/IconResults.vue";
import FilterNameDisplay from "@/components/election-results/FilterNameDisplay.vue";
import IconUsers from "@/components/icons/result-filter-panel/IconUsers.vue";
import {IMetadata} from "@/interfaces/IMetadata";

export default defineComponent({
  name: "PartyResultsHeader",
  components: {
    IconResults,
    FilterNameDisplay,
    IconUsers,
  },
  props: {
    filterNames: {
      type: Object as () => { province?: string; authority?: string; station?: string; year?: string },
      required: false,
      default: () => ({}),
    },
    metadata: {
      type: Object as () => IMetadata | null,
      default: null,
    },
  },
  methods: {
    /**
     * Formats the number of cast ballots for display.
     * Returns 'Undefined' if the value is zero or undefined.
     *
     * @param cast - The number of cast ballots
     * @returns A formatted string with thousands separator, or 'Unknown' if zero
     */
    displayCastBallots(cast: number | undefined): string {
      if (!cast || cast <= 0) return 'Unknown number of';
      return cast.toLocaleString();
    }
  },
  computed: {
    castLocation(): string {
      // Clean the station name from double stembureau and "e.g. (postcode: 9201 BW)"
      const cleanStation = (value?: string): string | null => {
        if (!value) return null;
        return value
            // Remove any "( ... )"
            .replace(/\(.*?\)/g, '')
            // Remove duplicated "stembureau stembureau"
            .replace(/\bstembureau\b\s+\bstembureau\b/gi, 'Stembureau:')
            .trim();
      };

      const station = cleanStation(this.filterNames.station);

      if (this.filterNames.station) return `at ${station}`;
      if (this.filterNames.authority) return `in ${this.filterNames.authority}`;
      if (this.filterNames.province) return `in ${this.filterNames.province}`;

      // No province/authority/station selected → national
      return "in the Netherlands";
    },
    year(): string {
      return this.filterNames.year ?? '';
    }
  }
});
</script>

<template>
  <div class="flex flex-col justify-between items-start w-full rounded-t-2xl bg-dark-blue p-2">
    <h3 class="text-[1.4rem] text-white font-extrabold text-center flex flex-row gap-3 items-end-safe ml-4">
      <icon-results class="w-9 h-10 flex text-white fill-white"/>
      Results {{ year.replace("TK", '') }}
    </h3>

    <filter-name-display :filter-names="filterNames"/>

    <p class="text-white mx-2 flex flex-row gap-1 items-end-safe">
      <icon-users class="w-7 h-7 flex text-white stroke-[1]"/>
      {{ displayCastBallots(metadata?.cast) }} ballots cast {{ castLocation }}
    </p>
    <!--  CAST - number of ballots cast (people who physically came to vote, or all ballots submitted). -->
  </div>
</template>

<style scoped>
</style>