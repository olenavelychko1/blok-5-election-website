<script lang="ts">
import {defineComponent} from 'vue'
import IconSearch from "@/components/icons/IconSearch.vue";
import IconCross from "@/components/icons/IconCross.vue";

/**
 * ForumSearch component allows users to search forum posts with a debounce mechanism.
 * Emits a 'search' event with the current query string.
 */
export default defineComponent({
  name: "ForumSearch",
  components: {IconCross, IconSearch},
  props: {
    query: {
      type: String,
      required: true
    }
  },
  emits: ['search', 'update:query'],
  data() {
    return {
      debounce: null as number | null
    }
  },
  computed: {
    currentQuery: {
      get(): string {
        return this.query;
      },
      set(value: string) {
        this.$emit('update:query', value);
      }
    }
  },
  methods: {
    /**
     * Emits the search event with the current query.
     * @param immediate - If true, emits the event immediately; otherwise, debounce the emission.
     */
    search(immediate: boolean = false) {
      // cancel any pending timer
      if (this.debounce !== null) {
        clearTimeout(this.debounce);
        this.debounce = null;
      }

      if (immediate) {
        this.$emit('search', this.currentQuery);
        return;
      }

      this.debounce = window.setTimeout(() => {
        this.$emit('search', this.currentQuery);
        this.debounce = null;
      }, 1500);
    },
    /**
     * Resets the search query and emits an empty search event.
     */
    resetSearch() {
      if (this.debounce !== null) {
        clearTimeout(this.debounce);
        this.debounce = null;
      }

      this.$emit('update:query', '');
      this.$emit('search', '');
    }
  }
})
</script>

<template>
  <section class="m-5 relative w-full flex flex-row items-center justify-center gap-4 hover:-translate-y-1 transition-transform duration-400">
    <div class="relative w-1/4">
      <input
          type="text"
          :placeholder="currentQuery ? '' : 'Search forum posts...'"
          v-model="currentQuery"
          @input="search()"
          @keyup.enter="search(true)"
          class="w-full bg-white px-8 py-5 pl-16 text-lg rounded-xl shadow-xs">
      <button @click="search(true)" class="w-8 h-8 absolute top-4 left-5 cursor-pointer">
        <IconSearch class="w-full h-full text-gray-blue "/>
      </button>
      <button @click="resetSearch" class="w-10 h-10 absolute top-3 right-3 cursor-pointer">
        <IconCross class="w-full h-full text-gray-blue hover:text-red transition-colors duration-500"/>
      </button>
    </div>
  </section>
</template>
