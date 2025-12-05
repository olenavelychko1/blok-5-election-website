<script lang="ts">
import {defineComponent} from 'vue'
import IconCircleChart from "@/components/icons/IconCircleChart.vue";
import CircleChart from "@/components/compare/results/charts/CircleChart.vue";

export default defineComponent({
  name: "ComparisonCircleChart",
  components: {CircleChart, IconCircleChart}
})
</script>

<template>
  <section class="bg-light-blue w-full lg:w-1/2 h-200 rounded-xl p-5">
    <header class="flex flex-row gap-2 items-start mr-auto">
      <IconCircleChart class="text-dark-blue w-7"/>
      <div class="flex flex-col">
        <p class="font-semibold text-2xl text-dark-blue">Distribution of votes</p>
        <p class="text-gray-blue text-sm">View the share of total votes for each party</p>
      </div>
    </header>
    <figure class="h-full w-full p-12 flex justify-center items-center">
      <CircleChart/>
    </figure>
  </section>
</template>
