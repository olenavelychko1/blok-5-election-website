<script lang="ts">
import {defineComponent} from 'vue'
import IconBarChart from "@/components/icons/IconBarChart.vue";
import BarChart from "@/components/compare/results/charts/BarChart.vue";

export default defineComponent({
  name: "ComparisonBarChart",
  components: {BarChart, IconBarChart},
})
</script>

<template>
  <section class="bg-light-blue w-full lg:w-1/2 h-200 rounded-xl p-5">
    <header class="flex flex-row gap-2 items-start mr-auto">
      <IconBarChart class="text-dark-blue w-7"/>
      <div class="flex flex-col">
        <h2 class="font-semibold text-2xl text-dark-blue">Voting Percentages</h2>
        <p class="text-gray-blue text-sm">View the percentage of votes for each party</p>
      </div>
    </header>
    <figure class="w-full h-full p-12">
      <BarChart/>
    </figure>
  </section>
</template>
