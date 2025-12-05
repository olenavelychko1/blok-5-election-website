<script lang="ts">
import {defineComponent} from 'vue'

/**
 * Component to display a single statistic box for a party in comparison view
 */
export default defineComponent({
  name: "ComparisonPartyStatCardBox",
  props: {
    value: {
      type: String
    },
    text: {
      type: String
    }
  }
})
</script>

<template>
  <article class="bg-light rounded-xl w-full h-30 flex flex-col items-center justify-center
hover:-translate-y-1 hover:shadow-sm transition-all duration-300 ease-in-out">
    <div class="flex flex-row gap-2 justify-center items-center">
      <h3 class="text-xl text-blue font-bold">{{ value ?? "?" }}</h3>
      <slot name="icon"></slot>
    </div>
    <p class="text-gray-blue text-md">{{ text }}</p>
  </article>
</template>
