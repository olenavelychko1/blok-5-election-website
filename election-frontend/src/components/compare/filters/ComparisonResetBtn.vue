<script lang="ts">
import {defineComponent} from 'vue'
import IconTrash from "@/components/icons/IconTrash.vue";
import useElectionFiltersMixin from "@/mixins/useElectionFiltersMixin";
import {IRegion} from "@/interfaces/IRegion";

/**
 * Button to reset selected comparison parties
 */
export default defineComponent({
  name: "ComparisonResetBtn",
  components: {IconTrash},
  inject: ["updateSelectedParties", "updateFilters", "comparisonState"],
  data() {
    return {
      defaultYear: null as IRegion | null
    }
  },
  methods: {
    /**
     * Reset selected parties by calling updateSelectedParties with empty arrays
     */
    resetParties() {
      this.updateSelectedParties([], []);
    },
    /**
     * Reset filters to default values
     */
    resetFilters() {
      this.updateFilters(
          {
            level: 'National',
            year: this.comparisonState.filters.year,
            province: '',
            authority: '',
            stationId: '',
            stationCode: ''
          }
      )
    },
    /**
     * Reset both selected parties and filters
     */
    reset() {
      this.resetParties();
      this.resetFilters();
    }
  }
})
</script>

<template>
  <button class="bg-light-red border-red border-1 text-red px-3 py-1 w-24 rounded-lg ml-auto text-xs
                  hover:bg-[#C98585FF] transition-colors active:bg-light-red flex items-center gap-2"
          @click="reset">
    <IconTrash class="text-red w-4"/>
    <span>Reset</span>
  </button>
</template>
