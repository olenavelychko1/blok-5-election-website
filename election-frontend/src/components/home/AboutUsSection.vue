<script lang="ts">
import {defineComponent} from 'vue'

export default defineComponent({
  name: "AboutUsSection"
})
</script>

<template>
  <section class="flex flex-col 2xl:flex-row items-center justify-center m-4 w-2/3 h-120 2xl:h-150 rounded-3xl overflow-hidden
bg-gradient-to-br from-blue to-green shadow-md">
    <article class="flex justify-center p-8 items-center flex-col w-full 2xl:w-1/2 h-full transition-transform duration-1000 ease-out
    hover:scale-103">
      <h3 class="text-light text-5xl font-bold mb-4">ABOUT US</h3>
      <!--      TODO: change to actual summary text, see issue #47-->
      <p class="text-light text-md px-5 2xl:px-25">
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore
        magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo
        consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla
        pariatur.
        Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
      </p>
    </article>

    <figure class="w-full 2xl:w-1/2 h-full justify-center items-center overflow-hidden">
      <img src="/images/about-us.png" alt="About Us Image" class="h-full w-full object-cover
      transition-transform duration-1000 ease-out hover:scale-103"/>
    </figure>
  </section>
</template>