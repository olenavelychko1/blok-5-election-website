<script lang="ts">
import {defineComponent} from 'vue'
import IconMunicipality from "@/components/icons/IconMunicipality.vue";
import IconGroup from "@/components/icons/IconGroup.vue";
import IconArrow from "@/components/icons/IconArrow.vue";
import StatCard from "@/components/ui/StatCard.vue";
import IconVote from "@/components/icons/IconVote.vue";
import IconLocation from "@/components/icons/IconLocation.vue";

export default defineComponent({
  name: "VoteMetaData",
  components: {IconLocation, IconVote, StatCard, IconArrow, IconGroup, IconMunicipality},
  data() {
    return {
      metadata: []
    }
  },
  // Will delete later, for showing purposes
  mounted() {
    try {
      fetch("http://localhost:8081/municipality/metadata?municipality=Amsterdam&year=2023")
          .then(res => {
            return res.json();
          })
          .then(data => {

            this.metadata = data;
            console.log(this.metadata);
          })
    } catch {
      console.error('Failed to fetch metadata');
    }
  },
  computed: {
    totalRejected(): number {
      const first = this.metadata[0];
      if (!first || !first.rejectedVotes) return 0;
      let sum = 0;
      for (const key in first.rejectedVotes) {
        sum += first.rejectedVotes[key];
      }
      return sum;
    },
    totalUncounted(): number {
      const first = this.metadata[0];
      if (!first || !first.uncountedVotes) return 0;
      let sum = 0;
      for (const key in first.uncountedVotes) {
        sum += first.uncountedVotes[key];
      }
      return sum;
    }
  }
})
</script>

<template>
  <section class="w-full h-175 bg-[#E2EFFF] my-5 flex flex-col justify-center items-center gap-8">
    <div>
      <h3 class="text-5xl text-dark-blue font-bold text-center">AMSTERDAM MUNICIPALITY VOTES</h3>
      <p class="text-gray-blue text-center mt-4">Discover detailed voting insights for the municipality of Amsterdam</p>
    </div>
    <div class="flex flex-row gap-5 flex-wrap justify-center items-center">
      <StatCard text="Cast" :number="metadata[0]?.cast"
                bgGradient="bg-gradient-to-r from-[#CDE5FB] to-[#BDEFC5]"></StatCard>
      <StatCard text="Total counted" :number="metadata[0]?.totalCounted"
                bgGradient="bg-gradient-to-r from-[#BDEFC5] to-[#CDE5FB]"></StatCard>
      <StatCard text="Rejected votes" :number="totalRejected"
                bgGradient="bg-gradient-to-r from-[#CDE5FB] to-[#FFFAB2]"></StatCard>
<!--      <StatCard text="Total uncounted" :number="totalUncounted"-->
<!--                bgGradient="bg-gradient-to-r from-[#FFFAB2] to-[#CDE5FB]"></StatCard>-->


    </div>
  </section>
</template>

<style scoped>

</style>