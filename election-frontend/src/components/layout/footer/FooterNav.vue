<script lang="ts">
import {defineComponent} from 'vue'
import IconForum from "@/components/icons/IconForum.vue";
import IconStats from "@/components/icons/IconStats.vue";
import IconList from "@/components/icons/IconList.vue";
import IconCompare from "@/components/icons/IconCompare.vue";
import IconQuiz from "@/components/icons/IconQuiz.vue";
import IconHome from "@/components/icons/IconHome.vue";
import IconMap from "@/components/icons/IconMap.vue";

export default defineComponent({
  name: "FooterNav",
  components: {IconMap, IconHome, IconQuiz, IconCompare, IconList, IconStats, IconForum}
})
</script>

<template>
  <!--  TODO: add correct links-->
  <div class="flex flex-col w-full mt-5 xl:mt-0 xl:w-1/3">
    <h3 class="text-lg text-green text-left">Navigation</h3>
    <ul class="">
      <li class="flex flex-row">
        <IconHome class="w-5 text-light"/>
        <router-link to="#" class="block px-4 py-1 text-light hover:text-blue transition-colors">Home</router-link>
      </li>
      <li class="flex flex-row">
        <IconStats class="w-5 text-light"/>
        <router-link to="#" class="block px-4 py-1 text-light hover:text-blue transition-colors">Election Results</router-link>
      </li>
      <li class="flex flex-row">
        <IconCompare class="w-5 text-light"/>
        <router-link to="/compare" class="block px-4 py-1 text-light hover:text-blue transition-colors">Compare Elections</router-link>
      </li>
      <li class="flex flex-row">
        <IconMap class="w-5 text-light"/>
        <router-link to="#" class="block px-4 py-1 text-light hover:text-blue transition-colors">Election Map</router-link>
      </li>
      <li class="flex flex-row">
        <IconList class="w-5 text-light"/>
        <router-link to="#" class="block px-4 py-1 text-light hover:text-blue transition-colors">Party List</router-link>
      </li>
      <li class="flex flex-row">
        <IconQuiz class="w-5 text-light"/>
        <router-link to="#" class="block px-4 py-1 text-light hover:text-blue transition-colors">Quiz</router-link>
      </li>
      <li class="flex flex-row">
        <IconForum class="w-5 text-light"/>
        <router-link to="#" class="block px-4 py-1 text-light hover:text-blue transition-colors">Forum</router-link>
      </li>
    </ul>
  </div>
</template>
