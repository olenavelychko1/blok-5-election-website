<script lang="ts">
import {defineComponent, inject} from "vue";
import IconChair from "@/components/icons/IconChair.vue";
import { IPartyVote } from "@/interfaces/IPartyVote";
import { usePartyColors } from "@/hooks/usePartyColours";
import { IMetadata } from "@/interfaces/IMetadata";
import {safePercent} from "@/hooks/useSafePercent";

export default defineComponent({
  name: "PartyResultsList",
  components: {
    IconChair,
  },
  props: {
    results: {
      type: Array as () => IPartyVote[],
      required: true,
      default: () => [],
    },
    metadata: {
      type: Object as () => IMetadata | null,
      default: null,
    },
  },
  methods: {
    safePercent,
    /**
     * Sets an almost unique key per party card
     * Forces Vue DOM rerender (forbids Vue from reusing the same DOM party in case the id is the same,
     * thus preventing party duplicates)
     *
     * @param party the party
     *
     * @returns string "partyId-party.votes-timestamp"
     */
    partyKey(party: any) {
      // Combine a timestamp or random ID with partyId
      return party.partyId + '-' + party.votes + '-' + Math.random()
    }
  },
  setup() {
    const { getPartyColor } = usePartyColors();
    return { getPartyColor};
  },
});
</script>

<template>

    <li
      v-for="(party, index) in results"
      :key="partyKey(party)"
      class="flex items-center justify-between bg-white m-2 p-3 rounded-lg shadow
      transition-all duration-300 ease-out
      hover:shadow-md hover:scale-[1.01]
      animate-[fadeOpacity_0.3s_ease-out] group"
    >
      <div class="flex items-center gap-3">
        <span class="font-bold text-gray-600 w-6 text-center">{{ index + 1 }}</span>
        <span
          class="w-4 h-4 rounded-full"
          :style="{ backgroundColor: getPartyColor(party.partyId) }"
        ></span>
        <span class="font-semibold text-gray-800">{{ party.partyName }}</span>
      </div>

      <div class="flex gap-6 items-center">
        <!-- Votes -->
        <div class="flex flex-col items-end leading-tight">
          <span class="font-bold text-gray-700">
            {{ party.votes.toLocaleString() }}
          </span>
          <span class="text-gray-500 text-sm">votes</span>
        </div>

        <!-- Seats -->
        <div class="flex flex-col items-center leading-tight min-w-12">
          <span class="flex items-center gap-1 font-bold text-gray-700">
            <icon-chair class="w-5 h-5 text-gray-700 stroke-[1]" />
            {{ party.seats || 0 }}
          </span>
          <span class="text-gray-500 text-sm">seats</span>
        </div>

        <!-- Percentage -->
        <span
          class="font-bold text-gray-700 text-lg flex flex-col items-end leading-tight min-w-17"
        >
          {{ safePercent(party.votes, metadata?.total_counted | 0) }}
        </span>
      </div>
    </li>
</template>

<style>
@keyframes fadeOpacity {
  0% {
    opacity: 0.5;
  }
  100% {
    opacity: 1;
  }
}
</style>