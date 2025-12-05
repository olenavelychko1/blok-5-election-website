<script lang="ts">
import {defineComponent} from 'vue'
import {IParty} from "@/interfaces/IParty";
import IconSort from "@/components/icons/IconSort.vue";
import IconAscending from "@/components/icons/IconAscending.vue";
import IconDescending from "@/components/icons/IconDescending.vue";
import ComparisonSortMenu from "@/components/compare/ComparisonSortMenu.vue";

export default defineComponent({
  name: "ComparisonSorter",
  components: {ComparisonSortMenu, IconDescending, IconAscending, IconSort},
  inject: ["comparisonState", "updateSelectedParties"],
  data() {
    return {
      menuOpen: false
    }
  },
  methods: {
    /**
     * Toggle sort order between ascending and descending
     */
    selectSort(desc: boolean) {
      this.comparisonState.sortDesc = desc;
      this.updateSelectedParties(this.comparisonState.selectedParties, this.comparisonState.selectedPartyVotes);
      this.menuOpen = false;
    },
    /**
     * Toggle the visibility of the sort menu
     */
    toggleMenu() {
      this.menuOpen = !this.menuOpen;
    }
  },
  computed: {
    sortDesc(): boolean {
      return this.comparisonState.sortDesc;
    }
  }
})
</script>

<template>
  <section class="relative">

    <button class="flex flex-row justify-end items-center gap-2 text-lg border-gray-blue border-1 py-1 px-2
    rounded-sm transition-colors duration-300 hover:bg-light-blue w-45" :class="{'border-gray-blue': menuOpen}"
            @click="toggleMenu">
      <IconSort class="w-7 text-gray-blue"/>
      <span class="text-gray-blue text-sm">
        Sort by
        <span class="font-bold">
          {{ sortDesc ? 'Descending' : 'Ascending' }}
        </span>
      </span>
    </button>
    <ComparisonSortMenu v-model:open="menuOpen" :sortDesc="sortDesc" @select="selectSort"/>
  </section>

</template>
