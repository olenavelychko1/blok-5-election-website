<script>
import Navbar from "@/components/layout/Navbar.vue";
import Footer from "@/components/layout/Footer.vue";

import {useRoute} from "vue-router";
import {computed} from "vue";

export default {
  name: "RootLayout",
  components: {
    Footer,
    Navbar
  },
  setup() {
    const route = useRoute()
    // Hide layout on login/register pages
    const showLayout = computed(() => !route.meta.hideLayout)
    return { showLayout }
  }

}
</script>

<template>
  <Navbar v-if="showLayout"/>

  <router-view />

  <Footer v-if="showLayout"/>
</template>

