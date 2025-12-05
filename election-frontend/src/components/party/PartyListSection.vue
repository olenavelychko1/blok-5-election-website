<script lang="ts">
import {defineComponent, PropType} from 'vue'
import type { IParty } from '@/interfaces/IParty'
import Loading from "@/components/utils/Loading.vue";

export default defineComponent({
  name: "PartyListSection",
  components: {Loading},
  props: {
    parties: {
      type: Array as PropType<IParty[]>,
      required: true
    },
    loading: {
      type: Boolean
    },
    error: {
      type: String
    }
  },
  setup() {
    // transforms the party name to the related logo name
    const transformNameToImageName = (name: string): string => {
      return name.replace('/', '-')
    }

    // returns the slug of the party's leader
    const getPartyLeaderSlug = (party: IParty): string => {
        const candidate = party.candidates[0];

        return candidate.initials + " " + candidate.lastName;
    }

    return {
      transformNameToImageName,
      getPartyLeaderSlug
    }
  }
})

</script>

<template>
  <section class="pt-12">
    <div class="mx-auto">
      <div class="w-full grid gap-6 grid-cols-2">
        <!-- Result Card Template -->
        <router-link
            :to="`/party/${party.id}`"
            v-for="(party, index) in parties"
            :key="index"
            class="hover:scale-103 bg-[#EDF5FE] hover:bg-[#d9e9fb] transition-transform duration-800 ease-out rounded-4xl p-4 shadow-md aspect-square flex flex-col justify-between"
            style="box-shadow: 0px 4px 20px 7px rgba(0, 0, 0, 0.15)"
        >
            <h1 class="text-center font-bold">{{ party.name}}</h1>
            <img
                :src="`/logo's/${transformNameToImageName(party.name)}.png`"
                :alt="`${party.name} Logo`"
                class="block max-w-2/3 max-h-full mx-auto object-contain"
            />
            <div class="text-center text-sm">
              <p class="text-gray-500">Party leader</p>
              <p class="font-bold text-gray-800">{{ getPartyLeaderSlug(party) }}</p>
            </div>
        </router-link>
      </div>
    </div>
  </section>
  <Loading :loading="loading" label="loading parties..." />
</template>