<script lang="ts">
import {defineComponent} from 'vue'
import IconArrow from "@/components/icons/IconArrow.vue";

export default defineComponent({
  name: "HeroSection",
  components: {IconArrow}
})
</script>

<template>
  <section>
    <header class="w-full h-screen relative flex justify-center items-center border-b-12 border-green">
      <figure class="w-full h-full">
        <img src="/images/election.png" alt="Hero Image" class="h-full w-full object-cover"/>
      </figure>
      <div class="absolute w-full h-full bg-dark-blue top-0 opacity-70"></div>
      <div class="flex flex-col justify-center items-center absolute top-85 gap-4">
        <h1 class="text-9xl font-bold text-light">ELECTIONS</h1>
        <p class="text-light">See how the Netherlands voted and which parties won seats!</p>
        <button class="btn-clear-white text-light flex flex-row gap-2">
          Election Results
          <IconArrow class="w-5 text-light rotate-90"/>
        </button>
      </div>
    </header>
  </section>
</template>
