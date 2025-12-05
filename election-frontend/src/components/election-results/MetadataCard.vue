<script lang="ts">
import {defineAsyncComponent, defineComponent} from 'vue'
import IconBlankVote from "@/components/icons/election-metadata/IconBlankVote.vue";
import IconCheck from "@/components/icons/election-metadata/IconCheck.vue";
import IconCross from "@/components/icons/election-metadata/IconCross.vue";
import IconTotalVotes from "@/components/icons/election-metadata/IconTotalVotes.vue";

export default defineComponent({
  name: 'MetadataCard',
  components: {IconTotalVotes, IconCross, IconCheck, IconBlankVote},
  props: {
    icon: {type: String, required: true}, // e.g. icon-vote
    label: {type: String, required: true},
    value: {type: [String, Number], required: true},
    percentage: {type: String, required: false, default: ''},
    backgroundClass: {type: String, required: false, default: 'bg-white text-black'},
  },
  computed: {
    IconComponent() {
      if (!this.icon) return console.log(`No icon ${this.icon} found`); // avoids undefined imports
      // Dynamically import matching file based on prop
      return defineAsyncComponent(() =>
          import(`@/components/icons/election-metadata/Icon${this.icon}.vue`)
      );
    }
  }
})
</script>

<template>
  <div
      class="rounded-xl shadow p-3 flex flex-col items-center text-center justify-evenly transition-transform hover:scale-[1.02]"
      :class="backgroundClass"
  >
    <div id="icon+header" class="flex flex-col items-center text-center">
      <component :is="IconComponent" class="w-8 h-8 mb-1"/>
      <p class="text-sm font-semibold">{{ label }}</p>
    </div>
    <div id="value+percentage" class="flex flex-col items-center text-center">
      <p class="text-xl font-extrabold">{{ value }}</p>

      <p class="text-sm font-semibold ">{{ percentage }}</p>
    </div>
  </div>
</template>
