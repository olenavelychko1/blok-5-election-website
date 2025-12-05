<script lang="ts">
import {defineComponent} from 'vue'
import IconArrow from "@/components/icons/IconArrow.vue";

export default defineComponent({
  name: "ElectionMapSection",
  components: {IconArrow}
})
</script>

<template>
  <section class="flex flex-col 2xl:flex-row items-center justify-center m-4 w-2/3 h-200 2xl:h-150 rounded-3xl
  overflow-hidden bg-white shadow-md">
    <figure class="w-full 2xl:w-1/2 h-2/3 2xl:h-full p-4 2xl:p-10 flex justify-center items-center overflow-hidden">
      <div class="bg-light 2xl:p-8 h-full 2xl:h-full w-full rounded-3xl flex justify-center items-center mx-8 2xl:mx-0">
        <img src="/images/map.png" alt="Dutch map Image"
             class="h-100 2xl:h-full w-full max-w-full object-contain transition-transform duration-1000 ease-out
             hover:scale-103"/>
      </div>
    </figure>

    <article class="flex justify-center p-2 2xl:p-8 items-center flex-col w-full 2xl:w-1/2 h-1/4 transition-transform
    duration-1000 ease-out
    hover:scale-103 gap-5">
      <h3 class="text-dark-blue text-5xl font-bold ">ELECTION MAP</h3>
      <p class="text-gray-blue text-md px-5 2xl:px-25">
        Explore the election results and voter turnout percentages across the different provinces in the Netherlands,
        and gain insights into how regional differences may have influenced the overall outcome.
      </p>

      <!--      TODO: change link to the dutch election map-->
      <button class="btn-clear-dark text-dark-blue flex flex-row gap-2 w-54 justify-center items-center">
        <a href="#">
          View Map
        </a>
        <IconArrow class="w-5  rotate-90"/>
      </button>
    </article>
  </section>
</template>
