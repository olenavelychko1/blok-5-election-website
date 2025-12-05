<script lang="ts">
import { defineComponent, PropType } from "vue";
import IconArrow from "@/components/icons/IconArrow.vue";
import ISortOption from "@/interfaces/ISortOption";
import ISortState from "@/interfaces/ISortState";

export default defineComponent({
  name: "OrderBy",
  components: { IconArrow },
  props: {
    sort: {
      type: Object as PropType<ISortState>,
      required: true,
    },
    options: {
      type: Array as PropType<ISortOption[]>,
      required: true,
    },
  },
  emits: ["update:sort"],
  methods: {
    onSelectChange(event: Event) {
      const target = event.target as HTMLSelectElement;
      const selectedKey = target.value;

      if (!selectedKey) {
        return;
      }

      const direction = this.sort[0].direction == "asc" ? "desc" : "asc";

      // new property selection always starts ASC
      this.$emit("update:sort", [{
        property: selectedKey,
        direction: direction,
      }]);
    },
  },
});
</script>

<template>
      <select
          class="border text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block p-2.5 bg-white text-gray-500 border-gray-300 hover:bg-gray-100 hover:text-gray-700"
          :value="sort.property || ''"
          @change="onSelectChange"
      >
        <option value="" disabled>Select order</option>
        <option
            v-for="option in options"
            :key="option.key"
            :value="option.key"
        >
          {{ option.label }}
        </option>
      </select>
</template>
