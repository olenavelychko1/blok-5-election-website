<script lang="ts">
import {defineComponent} from 'vue'
import {Bar} from 'vue-chartjs'
import {Chart as ChartJS, Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale} from 'chart.js'
import {IPartyVote} from "@/interfaces/IPartyVote";
import {IMetadata} from "@/interfaces/IMetadata";

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale)

/**
 * BarChart component to display voting percentages of selected parties
 */
export default defineComponent({
  name: "BarChart",
  components: {Bar},
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
     * Prepare chart data for the bar chart
     * Creates labels and datasets based on selected party votes and metadata
     */
    chartData() {
      const labels: string[] = this.selectedPartyVotes.map((vote: IPartyVote) => vote.partyName);
      const votePercentages: number[] = this.selectedPartyVotes.map((vote: IPartyVote) => {
        const totalVotes = this.metadata[0].total_counted;
        return Number(((vote.votes / totalVotes) * 100).toFixed(2));
      });

      const palette: string[] = [
        "#2563EB", "#1E3A8A", "#10B981", "#F59E0B",
        "#EF4444", "#8B5CF6", "#06B6D4", "#F472B6"
      ]
      const backgroundColor: string[] = labels.map((_, index) => palette[index % palette.length]);

      return {
        labels,
        datasets: [
          {
            label: 'Vote Percentage',
            data: votePercentages,
            backgroundColor,
            borderRadius: 10
          }
        ]
      }
    },
    /**
     * Chart options for the bar chart
     */
    chartOptions() {
      return {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {
          legend: {
            display: false
          },
          tooltip: {
            callbacks: {
              label: (context: any) => {
                return context.parsed.y + '%';
              }
            }
          },
        },
        scales: {
          y: {
            beginAtZero: true,
            ticks: {
              callback: (value: string) => value + '%'
            }
          }
        }
      }
    },
  }
})
</script>

<template>
  <Bar :data="chartData"
       :options="chartOptions"/>
</template>
