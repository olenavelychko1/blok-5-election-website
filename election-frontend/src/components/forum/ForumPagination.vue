<script lang="ts">
import {defineComponent} from 'vue'
import IconArrow from "@/components/icons/IconArrow.vue";

export default defineComponent({
  name: "ForumPagination",
  components: {IconArrow},
  props: {
    currentPage: {type: Number, required: true},
    totalPages: {type: Number, required: true},
    hasNext: {type: Boolean, required: true},
    hasPrevious: {type: Boolean, required: true}
  },
  methods: {
    /**
     * Emits an event to navigate to the next page.
     */
    nextPage() {
      this.$emit('next');
    },
    /**
     * Emits an event to navigate to the previous page.
     */
    previousPage() {
      this.$emit('previous');
    }
  }
})
</script>

<template>
  <div class="flex items-center justify-center gap-2 mt-12">
    <button
        class="bg-blue p-3 hover:bg-green transition-all rounded-lg disabled:opacity-50 hover:-translate-y-1 shadow-sm"
        :disabled="!hasPrevious"
        @click="previousPage">
      <IconArrow class="w-5 -rotate-90 text-dark-blue"/>
    </button>
    <div>
      Page {{ currentPage + 1 }} of {{ totalPages }}
    </div>
    <button
        class="bg-blue p-3 hover:bg-green transition-all rounded-lg disabled:opacity-50 hover:-translate-y-1 shadow-sm"
        :disabled="!hasNext"
        @click="nextPage">
      <IconArrow class="w-5 rotate-90 text-dark-blue"/>
    </button>
  </div>
</template>