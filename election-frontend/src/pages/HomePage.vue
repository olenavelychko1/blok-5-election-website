<script lang="ts">
import {defineComponent} from 'vue'
import HeroSection from "@/components/home/HeroSection.vue";
import StatsSection from "@/components/home/StatsSection.vue";
import ElectionMapSection from "@/components/home/ElectionMapSection.vue";
import AboutUsSection from "@/components/home/AboutUsSection.vue";
import TopParties from "@/components/home/TopParties.vue";

/**
 * HomePage component that serves as the main landing page for the election results application.
 * It includes sections for top parties, statistics, election map, and about us information.
 */
export default defineComponent({
  name: "HomePage",
  components: {TopParties, AboutUsSection, ElectionMapSection, StatsSection, HeroSection}
})
</script>

<template>
  <header>
    <HeroSection/>
  </header>

  <main class="min-h-screen flex flex-col items-center ">
    <TopParties/>
    <StatsSection/>
    <ElectionMapSection/>
    <AboutUsSection/>
  </main>
</template>