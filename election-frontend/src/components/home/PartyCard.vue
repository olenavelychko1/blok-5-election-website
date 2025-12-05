<script lang="ts">
import {defineComponent} from 'vue'
import {IPartyVote} from "@/interfaces/IPartyVote";
import {formatNumber} from "chart.js/helpers";
import {ITopParty} from "@/interfaces/ITopParty";
import topParties from "@/components/home/TopParties.vue";

/**
 * PartyCard component that displays information about a political party,
 * including its name, logo, votes, and party leader.
 */
export default defineComponent({
  name: "PartyCard",
  computed: {
    topParties() {
      return topParties
    }
  },
  props: {
    topParty: {
      type: Object as () => ITopParty,
      required: true
    },
  },
  methods: {
    // Reuse the formatNumber method from chart.js/helpers
    formatNumber,
    /**
     * Transform party name to image name by replacing '/' with '-'
     * @param name - party name
     */
    transformNameToImageName(name: string): string {
      return name.replace('/', '-')
    }
  },
})
</script>

<template>
  <article class="relative bg-white w-100 h-100 rounded-3xl shadow-xl flex flex-col items-center justify-start
        hover:-translate-y-3 transition-all ease-out duration-800" :class="{ 'xl:-top-10 top-0': topParty.place == 1}"
           v-tooltip="{
            content: topParty.party.partyName,
            theme: 'info-tooltip'
          }">
    <!--    Place Badge-->
    <div class="absolute bg-white border-blue border-2 rounded-xl w-12 h-12 flex items-center justify-center -top-5 shadow-sm">
      <h3 class="text-blue font-bold text-3xl ">{{ topParty.place }}</h3>
    </div>

    <div class="flex flex-col items-center mt-15 gap-2">
      <!--      Party logo-->
      <figure>
        <img
            :src="`/logo's/${transformNameToImageName(topParty.party.partyName)}.png`"
            :alt="`${topParty.party.partyName} Logo`"
            class="w-20"/>
        <figcaption class="sr-only">{{ topParty.party.partyName }} Logo</figcaption>
      </figure>

      <!--      Seats-->
      <div class="flex items-center justify-center flex-col gap-2 mt-3">
        <p class="text-dark-blue text-5xl font-semibold">{{ topParty.party.seats }}</p>
        <p class="text-gray-blue text-xl -mt-4">seats</p>
      </div>

      <!--      VOtes-->
      <div class="flex items-center justify-center flex-col gap-4 mt-2">
        <p class="text-dark-blue text-xl font-semibold">{{ formatNumber(topParty.party.votes, "en-US") }}</p>
        <p class="text-gray-blue text-xl -mt-5">votes</p>
      </div>

      <!--      Party leader-->
      <div class="flex flex-col items-center m-2 text-center">
        <p class="text-gray-blue text-sm">Party Leader</p>
        <p class="text-dark-blue font-semibold">{{ topParty.leader }}</p>
      </div>
    </div>
  </article>
</template>
