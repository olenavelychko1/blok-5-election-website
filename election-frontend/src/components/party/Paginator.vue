<script lang="ts">
import {defineComponent, PropType} from 'vue'
import PageSizeSelector from "@/components/party/Paginator/PageSizeSelector.vue";
import PageSelector from "@/components/party/Paginator/PageSelector.vue";
import Loading from "@/components/utils/Loading.vue";

export default defineComponent({
  name: "Paginator",
  components: {Loading, PageSelector, PageSizeSelector},
  props: {
    page: Number,
    size: Number,
    totalPartiesCount: Number,
    totalPartiesCountLoading: Boolean,
  },
  emits: ["update:page", "update:size"],
})

</script>

<template>
  <section class="py-4 pb-8">
    <Loading v-if="totalPartiesCountLoading" :loading="totalPartiesCountLoading" />
    <div v-else class="flex items-center w-full">
      <div class="flex-1 flex justify-start">
        <PageSizeSelector
            :size="size"
            @update:size="$emit('update:size', $event)"
        />
      </div>

      <div class="flex-shrink-0">
        <PageSelector
            :page="page"
            :amount="Math.ceil(totalPartiesCount / size)"
            @update:page="$emit('update:page', $event)"
        />
      </div>

      <div class="flex-1 flex justify-end">
        <span class="text-gray-500">List of {{ totalPartiesCount }} items...</span>
      </div>
    </div>
  </section>

</template>
