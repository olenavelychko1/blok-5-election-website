<script lang="ts">
import {defineComponent} from 'vue'
import IconLoading from "@/components/icons/IconLoading.vue";

export default defineComponent({
  name: "LoadingState",
  components: {IconLoading}
})
</script>

<template>
  <section class="flex items-center justify-center w-full flex-col gap-4 p-5">
    <IconLoading class="w-10 text-dark-blue animate-spin"/>
    <p class="text-gray-blue">Loading...</p>
  </section>
</template>

