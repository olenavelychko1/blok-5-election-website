<script lang="ts">
import {defineComponent} from 'vue'
import IconSad from "@/components/icons/IconSad.vue";

export default defineComponent({
  name: "ErrorState",
  components: {IconSad}
})
</script>

<template>
  <section class="flex items-center justify-center h-full w-full flex-col gap-4 p-5">
    <IconSad class="w-15 text-dark-blue"/>
    <p class="text-gray-blue">Something went wrong... Try again</p>
  </section>
</template>
