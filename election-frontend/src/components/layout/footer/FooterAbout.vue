<script lang="ts">
import {defineComponent} from 'vue'
import IconArrow from "@/components/icons/IconArrow.vue";
import IconLogo from "@/components/icons/IconLogo.vue";
import IconLocation from "@/components/icons/IconLocation.vue";

export default defineComponent({
  name: "FooterAbout",
  components: {IconLocation, IconLogo, IconArrow},
  methods: {
    // Scroll to the top of the page smoothly
    goToTop() {
      window.scrollTo({top: 0, behavior: 'smooth'});
    }
  }
})
</script>

<template>
  <div class="flex flex-col w-full xl:w-1/3">
    <IconLogo class="w-100"/>
    <p class="w-full xl:w-2/3 text-left mt-5 ml-8 text-light">
      An interactive election platform that provides users with real-time results, historical comparisons,
      detailed maps, party information, quizzes, and a community forum to stay informed and engaged with the
      democratic process.
    </p>
    <p class="w-full xl:w-2/3 text-left mt-5 ml-8 text-gray-blue flex-row flex gap-2">
      <span><IconLocation class="w-5 text-gray-blue"/></span>
      Amsterdam, The Netherlands
    </p>
    <button class="btn-clear-white w-50 ml-8 mt-5 flex-row flex items-center gap-2" @click="goToTop">
      <IconArrow class="text-light w-8"/>
      <span>Back to top</span>
    </button>
  </div>
</template>
