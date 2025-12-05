<script lang="ts">
import { defineComponent, PropType } from 'vue'
import ISelectOption from '@/interfaces/form/ISelectOption'

export default defineComponent({
  name: 'Selector',
  props: {
    label: {
      type: String,
      required: false
    },
    value: {
      type: [Number, String] as PropType<number | string>,
      required: false
    },
    setValue: {
      type: Function as PropType<(val: number | string) => void>,
      required: true
    },
    options: {
      type: Array as PropType<ISelectOption[]>,
      default: () => []
    }
  },
  setup(props) {
    const handleChange = (event: Event) => {
      const target = event.target as HTMLSelectElement
      const raw = target.value

      // If current value is a number, keep it numeric, otherwise keep string
      const next =
          typeof props.value === 'number' ? Number(raw) : (raw as string)

      props.setValue(next)
    }

    return { handleChange }
  }
})
</script>

<template>
  <section class="flex flex-col">
    <label
        v-if="label !== null"
        for="selector"
        class="block mb-2 text-sm font-medium text-gray-900"
    >
      {{ label }}
    </label>

    <select
        id="selector"
        :value="value"
        @change="handleChange"
        class="border text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 bg-white text-gray-500 border-gray-300 hover:bg-gray-100 hover:text-gray-700"
    >
      <option
          v-for="option in options"
          :key="option.value"
          :value="option.value"
      >
        {{ option.label }}
      </option>
    </select>
  </section>
</template>
