<script lang="ts">
import {defineComponent, PropType} from 'vue'
import Selector from "@/components/utils/Selector.vue";
import ISelectOption from "@/interfaces/form/ISelectOption";

export default defineComponent({
  name: "PageSizeSelector",
  components: {Selector},
  props: {
    size: Number,
  },
  emits: ["update:size"],
  setup(_, { emit }) {
    const setValue = (val: number) => {
      emit("update:size", val)
    }

    const sizeOptions: ISelectOption[] = [
      {value: 4, label: '4'},
      {value: 8, label: '8'},
      {value: 10, label: '10'},
      {value: 20, label: '20'},
      {value: 1000, label: 'All'},
    ];

    return { setValue, sizeOptions }
  }
})

</script>

<template>
    <Selector :value="size" :label="null" :setValue="setValue" :options="sizeOptions" />
</template>
