<script lang="ts">
import {defineComponent} from 'vue'
import IconLogo from "@/components/icons/IconLogo.vue";
import IconHome from "@/components/icons/IconHome.vue";
import IconStats from "@/components/icons/IconStats.vue";
import IconCompare from "@/components/icons/IconCompare.vue";
import IconMap from "@/components/icons/IconMap.vue";
import IconList from "@/components/icons/IconList.vue";
import IconQuiz from "@/components/icons/IconQuiz.vue";
import IconForum from "@/components/icons/IconForum.vue";
import IconCircleUser from "@/components/icons/IconCircleUser.vue";
import NavbarAuth from "@/components/layout/navbar/NavbarAuth.vue";
import HamburgerMenu from "@/components/layout/navbar/HamburgerMenu.vue";
import NavbarMenu from "@/components/layout/navbar/NavbarMenu.vue";

export default defineComponent({
  name: "Navbar",
  components: {
    NavbarMenu,
    HamburgerMenu,
    NavbarAuth,
    IconCircleUser, IconForum, IconQuiz, IconList, IconMap, IconCompare, IconStats, IconHome, IconLogo
  },
  data() {
    return {
      showMenu: false,
      isAuthenticated: false,
    }
  },
  methods: {
    // Toggle the visibility of the menu
    toggleMenu() {
      this.showMenu = !this.showMenu;
    },
  }
})
</script>

<template>
  <nav class="bg-dark-blue sticky top-0 z-1001 w-full h-24 border border-b-green border-b-12">
    <!--    Logo section-->
    <div>
      <router-link to="/">
        <IconLogo class="absolute top-0 left-5 z-101 w-75"/>
      </router-link>
    </div>

    <NavbarAuth :isAuthenticated="isAuthenticated"/>
    <HamburgerMenu :showMenu="showMenu" @click="toggleMenu"/>
    <NavbarMenu v-model:showMenu="showMenu"/>
  </nav>
</template>

