<script lang="ts">
import {defineComponent, inject} from 'vue';
import MetadataCard from "@/components/election-results/MetadataCard.vue";
import {IMetadata} from "@/interfaces/IMetadata";
import {safePercent} from "@/hooks/useSafePercent";

export default defineComponent({
  name: "MetadataGrid",
  methods: {safePercent},
  components: {
    MetadataCard
  },
  props: {
    metadata: {
      type: Object as () => IMetadata | null,
      default: null
    },
    mLoading: {
      type: Boolean,
      default: false
    },
    mError: {
      type: String,
      default: null
    }
  },
  computed: {
    validVotes(): number {
      if (!this.metadata) return 0;
      return this.metadata.total_counted - (this.metadata.invalid ?? 0) - (this.metadata.blank ?? 0);
    }
  }
});
</script>

<template>
  <div class="grid grid-cols-2 md:grid-cols-4 gap-3 w-[90%] mt-4">
    <MetadataCard
        icon="TotalVotes"
        label="turnout (counted votes)"
        :value="metadata?.total_counted.toLocaleString() ?? '-'"
        :percentage="metadata ? safePercent(metadata.total_counted, metadata.cast) : '-'"
        background-class="bg-[#dceeff] text-friendly-blue"
        :loading="mLoading"
        :error="mError"
    />
    <!-- TOTAL-COUNTED - total number of ballots processed (counted). Can be higher than cast, because of: volmachtstemmen en kiezerspassen (proxy votes votes cast with a voter pass)
      Can be interpreted as "CAST - UNCOUNTED VOTES" due to different reasons (e.g., invalid ballots, missing ballots, etc.). -->
    <MetadataCard
        icon="Check"
        label="valid votes"
        :value="metadata ? (metadata.total_counted - metadata.invalid - metadata.blank).toLocaleString() : '-'"
        :percentage="metadata ? safePercent((validVotes), metadata.cast) : '-'"
        background-class="bg-[#d6f5e3] text-friendly-green"
        :loading="mLoading"
        :error="mError"
    />
    <!-- valid = TotalCounted − (ongeldig + blanco) -->
    <MetadataCard
        icon="BlankVote"
        label="blank votes"
        :value="metadata?.blank.toLocaleString() ?? '-'"
        :percentage="metadata ? safePercent(metadata.blank, metadata.cast) : '-'"
        background-class="bg-[#f1d4ff] text-friendly-purple"
        :loading="mLoading"
        :error="mError"
    />
    <!-- blanco -->
    <MetadataCard
        icon="Cross"
        label="invalid votes"
        :value="metadata?.invalid.toLocaleString() ?? '-'"
        :percentage="metadata ? safePercent(metadata.invalid, metadata.cast) : '-'"
        background-class="bg-[#ffd4d4] text-friendly-red"
        :loading="mLoading"
        :error="mError"
    />
    <!-- invalid -->
  </div>
</template>

<style scoped>
</style>