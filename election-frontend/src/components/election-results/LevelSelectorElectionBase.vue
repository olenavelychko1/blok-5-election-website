<script lang="ts">
import {defineComponent} from 'vue'
import IconSearch from "@/components/icons/IconSearch.vue";
import IconResetFilter from "@/components/icons/result-filter-panel/IconResetFilter.vue";
import IconOptions from "@/components/icons/IconOptions.vue";
import LevelSelector from "@/components/election-results/LevelSelector.vue";
import {IResultFilters} from "@/interfaces/IResultFilters";

/**
 * High-level component that wraps the LevelSelector with additional UI elements
 * like the "Search postcode" and "Reset" buttons.
 *
 * The component maintains a local copy of filters (`localFilters`) to enable
 * two-way binding and manual reset behavior.
 */
export default defineComponent({
  name: "LevelSelectorElectionBase",
  components: {LevelSelector, IconOptions, IconResetFilter, IconSearch},
  props: {
    /**
     * The filter object containing the current selection state.
     * It is synced with the parent component through `v-model:filters`.
     */
    filters: {
      type: Object as () => IResultFilters,
      required: true
    }
  },
  // accepts the filter-names event from the level selector and passes it up to the parent
  emits: ['update:filters', 'update:filter-names'],
  data() {
    return {
      /**
       * Local copy of filters used for internal updates before emitting
       * changes back to the parent. Helps avoid direct mutation of props.
       */
      localFilters: { ...this.filters } as IResultFilters
    }
  },

  watch: {
    /**
     * Keeps the local filter state synchronized when the parent updates it.
     * Runs immediately on component mount.
     */
    filters: {
      deep: true,
      immediate: true,
      handler(newVal) {
        this.localFilters = { ...newVal }
      }
    }
  },
  methods: {
    /**
     * Resets all filters to their default (national) state.
     * Also triggers a reset inside the LevelSelector if available.
     */
    handleReset() {
      // reset both internal and emitted filters
      const reset = {
        level: 'National',
        year: null,
        province: '',
        authority: '',
        stationId: '',
        stationCode: ''
      } as IResultFilters

      this.localFilters = reset

      // if LevelSelector has a ref reset function
      if (this.$refs.selector) {
        ;(this.$refs.selector as any).resetFilters()
      }
    },
    /**
     * Emits updated filters to the parent when LevelSelector changes.
     *
     * @param {IResultFilters} updated - The newly selected filter set.
     */
    emitFilters(updated: IResultFilters) {
      this.localFilters = { ...updated }
      this.$emit('update:filters', updated)
    }
  }
})
</script>

<template>

  <section
      class="bg-light shadow-[0rem_0.25rem_0.5rem_rgba(0,0,0,0.25)] transition-shadow hover:shadow-[0rem_0.25rem_0.5rem_rgba(0,0,0,0.35)] width-limit rounded-2xl p-3 flex flex-col gap-10 items-center justify-center mx-4">
    <div class="flex flex-row w-full h-full gap-5">
      <div class="flex flex-col gap-3 w-full">
        <!-- Header -->
        <div class="flex flex-row gap-3 items-center ml-4 ">
          <IconOptions class="text-dark-blue w-7"/>
          <p class="font-semibold font-weight text-[1.2rem] ">Select Options</p>
        </div>

        <level-selector ref="selector" v-model:filters="localFilters" @update:filters="emitFilters" @update:filter-names="$emit('update:filter-names', $event)"/>


        <!--  Reset filter and search postcode button holder -->
        <div class="flex flex-row gap-2 items-center">
          <button @click=""
                  class="bg-light border border-gray-blue  px-3 py-2 w-4/6 pl-0 h-15 text-gray-blue rounded-lg ml-auto hover:bg-[#E2EFFF] transition-colors flex items-center  cursor-text">
            <icon-search class="w-10 h-7 flex"/>
            Search postcode
          </button>
          <button @click="handleReset"
                  class="bg-light-red border border-red text-red px-3 py-2 w-2/6 pl-1 h-15  rounded-lg ml-auto hover:bg-[#C98585FF] transition-colors flex items-center gap-2 cursor-pointer">
            <icon-reset-filter class="w-7 h-7 flex stroke-red"/>
            Reset
          </button>
        </div>
      </div>
    </div>
  </section>

</template>
