<script lang="ts">
import {defineComponent} from 'vue'

export default defineComponent({
  name: "ComparisonPartySeatPercentageBar",
  props: {
    seats: {
      type: Number
    }
  },
  computed: {
    seatPercentage(): string {
      const seats = this.seats ?? 0;
      const percentage = (seats / 150) * 100;
      return percentage.toFixed(2) + "%";
    }
  }
})
</script>

<template>
  <figure class="w-full pb-5 lg:pb-8 px-5 text-center"
      v-tooltip="{ content: seats + ' out of 150 seats', placement: 'bottom', distance: -20 }">
    <figcaption class="flex justify-between mb-2 text-sm text-gray-blue">
      <span>Percentage of seats</span>
      <span>{{ seatPercentage }}</span>
    </figcaption>
    <div class="w-full bg-light rounded-full h-2">
      <div class="bg-blue rounded-full h-2" :style="{ width: seatPercentage }"></div>
    </div>
  </figure>
</template>
