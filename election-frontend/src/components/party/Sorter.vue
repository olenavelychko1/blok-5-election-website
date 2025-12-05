<script lang="ts">
import { defineComponent, PropType } from "vue";
import ISelectOption from "@/interfaces/form/ISelectOption";
import Selector from "@/components/utils/Selector.vue";
import OrderBy from "@/components/party/Sorter/OrderBy.vue";
import ISortOption from "@/interfaces/ISortOption";
import ISortState from "@/interfaces/ISortState";

export default defineComponent({
  name: "Sorter",
  components: { OrderBy, Selector },
  props: {
    filterValue: {
      type: String,
      default: "",
    },
    filterOptions: {
      type: Array as PropType<ISelectOption[]>,
      default: () => [],
    },
    onFilterChange: {
      type: Function as PropType<(id: string) => void>,
      required: false,
    },
    sort: {
      type: Object as PropType<ISortState>,
      required: true,
    },
    sortOptions: {
      type: Array as PropType<ISortOption[]>,
      required: true,
    },
  },
  emits: ["update:sort"],
});
</script>

<template>
  <div class="flex items-center justify-between w-full gap-4">
    <Selector
        :label="null"
        :value="filterValue"
        :options="filterOptions"
        :setValue="onFilterChange"
    />

    <OrderBy
        :sort="sort"
        :options="sortOptions"
        @update:sort="$emit('update:sort', $event)"
    />
  </div>
</template>
