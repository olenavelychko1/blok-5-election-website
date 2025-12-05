<script lang="ts">
import {defineComponent} from 'vue'
import {Pie} from "vue-chartjs";
import {usePartyColors} from "@/hooks/usePartyColours";
import {IPartyVote} from "@/interfaces/IPartyVote";
import {IMetadata} from "@/interfaces/IMetadata";
import {ArcElement, TooltipItem, Chart as ChartJS, Legend, Title, Tooltip} from "chart.js";
import {safePercent} from "@/hooks/useSafePercent";

// Register necessary Chart.js components for the pie chart
ChartJS.register(Title, Tooltip, Legend, ArcElement);

/**
 * ElectionCircleDiagram component
 * Displays a pie chart showing the distribution of votes among parties.
 * Shows votes, calculated percentage, and number of seats in the tooltip.
 */
export default defineComponent({
  name: "ElectionCircleDiagram",
  components: {Pie},
  /**
   * Props:
   * - results: Array of party vote objects to display in the chart
   * - metadata: Metadata object containing total_counted votes (used to calculate percentages)
   */
  props: {
    results: {
      type: Array as () => IPartyVote[],
      required: true,
      default: () => [],
    },
    metadata: {
      type: Object as () => IMetadata | null,
      default: null
    },
  },
  setup() {
    const {getPartyColor} = usePartyColors(); // Custom hook to get party-specific colors
    return {getPartyColor};
  },
  computed: {
    /**
     * Prepares chart data for the Pie chart
     * - labels: Party names
     * - data: Number of votes per party
     * - backgroundColor: Colors assigned to each party via getPartyColor
     */
    chartData() {
      const votes = this.results.map((vote: IPartyVote) => vote.votes);

      const backgroundColor = this.results.map((vote: IPartyVote) =>
          this.getPartyColor(vote.partyId)
      );

      return {
        labels: this.results.map((vote: IPartyVote) => vote.partyName),
        datasets: [
          {
            label: 'Votes',
            data: votes,
            backgroundColor,
            borderRadius: 10,
          },
        ],
      };
    },
    /**
     * Configures chart options
     * - Responsive and maintains aspect ratio
     * - Tooltips display votes, percentage, and seats
     * - Legend is positioned at the bottom
     */
    chartOptions() {
      return {
        responsive: true,
        maintainAspectRatio: false,
        plugins: {

          tooltip: {
            callbacks: {
              /**
               * Custom tooltip label callback
               * Shows: "votes — percentage — seats"
               * @param context TooltipItem for pie chart
               */
              label: (context: TooltipItem<'pie'>) => {
                const vote = this.results[context.dataIndex];
                const total = this.metadata?.total_counted ?? 0;
                const percentage = safePercent(vote.votes, total);
                // console.log(` ${vote.votes.toLocaleString()} votes — ${percentage} — ${vote.seats || 0} seats`)
                return ` ${vote.votes.toLocaleString()} votes — ${percentage} — ${vote.seats || 0} seats`;
              }
            }
          },
          legend: {
            position: 'bottom' as const
          }
        }
      };
    },
  },
});
</script>

<template>
    <div class="w-full h-136 md:h-124 bg-light shadow-[0rem_0.25rem_0.5rem_rgba(0,0,0,0.25)] transition-shadow hover:shadow-[0rem_0.25rem_0.5rem_rgba(0,0,0,0.35)] rounded-2xl ">
  <Pie :data="chartData" :options="chartOptions"/>
    </div>
</template>