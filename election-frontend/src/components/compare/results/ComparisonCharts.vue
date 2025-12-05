<script lang="ts">
import {defineComponent} from 'vue'
import ComparisonBarChart from "@/components/compare/results/ComparisonBarChart.vue";
import ComparisonCircleChart from "@/components/compare/results/ComparisonCircleChart.vue";

export default defineComponent({
  name: "ComparisonCharts",
  components: {ComparisonCircleChart, ComparisonBarChart},
})
</script>

<template>
  <section class="w-full flex flex-col gap-3">
    <div class="flex flex-col lg:flex-row w-full gap-3">
      <ComparisonBarChart/>
      <ComparisonCircleChart/>
    </div>
  </section>
</template>
