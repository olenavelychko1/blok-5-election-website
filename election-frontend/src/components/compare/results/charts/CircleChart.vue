<script lang="ts">
import {Pie} from 'vue-chartjs'
import {defineComponent} from 'vue'
import {IPartyVote} from "@/interfaces/IPartyVote";
import {IMetadata} from "@/interfaces/IMetadata";
import {ArcElement, CategoryScale, Chart as ChartJS, Legend, LinearScale, Title, Tooltip} from "chart.js";

ChartJS.register(Title, Tooltip, Legend, ArcElement, CategoryScale, LinearScale)

/**
 * CircleChart component to display voting distribution of selected parties
 * Uses a pie chart to visualize the data
 */
export default defineComponent({
  name: "CircleChart",
  components: {Pie},
  inject: ['comparisonState'],
  computed: {
    /**
     * Get selected party votes from comparison state
     */
    selectedPartyVotes(): IPartyVote[] {
      return this.comparisonState.selectedPartyVotes;
    },
    /**
     * Get metadata from comparison state
     */
    metadata(): IMetadata[] {
      return this.comparisonState.metadata;
    },

    /**
     * Prepare chart data for the circle chart
     * Creates labels and datasets based on selected party votes and metadata
     */
    chartData() {
      const labels: string[] = this.selectedPartyVotes.map((vote: IPartyVote) => vote.partyName);
      const votes: number[] = this.selectedPartyVotes.map((vote: IPartyVote) => vote.votes);

      const palette: string[] = [
        "#2563EB", "#1E3A8A", "#10B981", "#F59E0B",
        "#EF4444", "#8B5CF6", "#06B6D4", "#F472B6"
      ]
      const backgroundColor: string[] = labels.map((_, index) => palette[index % palette.length]);

      return {
        labels,
        datasets: [
          {
            label: 'Votes',
            data: votes,
            backgroundColor,
            borderRadius: 10
          }
        ]
      }
    },
    /**
     * Chart options for the circle chart
     */
    chartOptions() {
      return {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            position: 'bottom' as const
          }
        }
      }
    }
  }
})
</script>

<template>
  <Pie :data="chartData"
       :options="chartOptions"/>
</template>
