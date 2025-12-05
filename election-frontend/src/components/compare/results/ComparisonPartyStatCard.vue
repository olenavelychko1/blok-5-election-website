<script lang="ts">
import {defineComponent} from 'vue'
import IconMunicipality from "@/components/icons/IconMunicipality.vue";
import {IPartyVote} from "@/interfaces/IPartyVote";
import {IMetadata} from "@/interfaces/IMetadata";
import ComparisonPartyStatCardBox from "@/components/compare/results/ComparisonPartyStatCardBox.vue";
import {formatNumber} from "chart.js/helpers";
import ComparisonPartySeatPercentageBar from "@/components/compare/results/ComparisonPartySeatPercentageBar.vue";
import IconVote from "@/components/icons/IconVote.vue";
import IconChair from "@/components/icons/IconChair.vue";

export default defineComponent({
  name: "ComparisonPartyStatCard",
  components: {
    IconChair,
    IconVote,
    ComparisonPartySeatPercentageBar: ComparisonPartySeatPercentageBar,
    ComparisonPartyStatCardBox,
    ComparisonPartyStatCardSection: ComparisonPartyStatCardBox,
  },
  inject: ["transformNameToImageName"],
  props: {
    partyVote: {
      type: Object as () => IPartyVote
    },
    metadata: {
      type: Object as () => IMetadata[]
    }
  },
  computed: {
    votePercentage(): string {
      const totalCounted = this.metadata[0].total_counted;
      const votes = this.partyVote.votes;
      const percentage = (votes / totalCounted) * 100;

      // 2 decimals for example 12.34%
      let formatted = percentage.toFixed(2);

      // If formatted is 0.00, show 3 decimals for example 0.001%
      if (formatted === "0.00") {
        formatted = percentage.toFixed(3);
      }

      return formatted + "%";
    },
    stats() {
      return [
        {value: formatNumber(this.partyVote.votes, 'en-US'), text: 'votes', icon: IconVote},
        {value: this.votePercentage, text: 'of total votes', icon: null},
        {value: this.partyVote.seats.toString(), text: 'seats', icon: IconChair}
      ]
    }
  },
  methods: {
    formatNumber,
    getPartyImg(name: string): string {
      return this.transformNameToImageName(name);
    }
  }
})
</script>

<template>
  <article class="bg-light-blue w-full rounded-xl flex flex-col">
    <header class="w-full p-5 pb-1 relative min-h-25">
      <div class="w-5/6">
        <h3 class="text-2xl text-dark-blue font-bold">{{ partyVote.partyName }}</h3>
      </div>
      <img
          :src="`/logo's/${getPartyImg(partyVote.partyName)}.png`"
          :alt="`${partyVote.partyName} logo`"
          class="w-14 absolute right-5 top-5"/>
    </header>
    <ul class="h-1/3 w-full flex flex-col sm:flex-row gap-2 px-5 pb-5 text-center">
      <li v-for="(stat, index) in stats" :key="index" class="w-full">
        <ComparisonPartyStatCardBox :value="stat.value" :text="stat.text">
          <template #icon v-if="stat.icon">
            <component :is="stat.icon" class="w-6 h-6 text-blue"/>
          </template>
        </ComparisonPartyStatCardBox>
      </li>
    </ul>
    <ComparisonPartySeatPercentageBar :seats="partyVote.seats"/>
  </article>
</template>

