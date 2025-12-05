<script lang="ts">
import {defineComponent} from 'vue'
import IconSearch from "@/components/icons/IconSearch.vue";
import IconResetFilter from "@/components/icons/result-filter-panel/IconResetFilter.vue";
import IconOptions from "@/components/icons/IconOptions.vue";
import LevelSelector from "@/components/election-results/LevelSelector.vue";
import {IResultFilters} from "@/interfaces/IResultFilters";
import IconCheckmark from "@/components/icons/IconCheckmark.vue";

export default defineComponent({
  name: "LevelSelectorComparison",
  components: {IconCheckmark, LevelSelector, IconOptions, IconResetFilter, IconSearch},
  props: {
    filters: {
      type: Object as () => IResultFilters,
      required: true
    }
  },
  emits: ['update:filters'],
  data() {
    return {
      localFilters: { ...this.filters } as IResultFilters
    }
  },

  watch: {
    filters: {
      deep: true,
      immediate: true,
      handler(newVal) {
        this.localFilters = { ...newVal }
      }
    }
  },
  methods: {
    emitFilters(updated: IResultFilters) {
      this.localFilters = { ...updated }
      this.$emit('update:filters', updated)
    }
  }
})
</script>

<template>
  <section class="bg-light-blue h-full rounded-xl">
    <div class="flex flex-row w-full h-full gap-5">
      <div class="flex flex-col gap-3 w-full">
        <level-selector ref="selector" v-model:filters="localFilters" @update:filters="emitFilters"/>
      </div>
    </div>
  </section>
</template>

<style scoped>
/* limit the width of the filter panel on larger screens*/
@media screen and (width >= 900px) {
  .width-limit {
    max-width: 400px;
  }
}
</style>