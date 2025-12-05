<script lang="ts">
import {defineComponent} from 'vue'
import IconLogo from "@/components/icons/IconLogo.vue";
import IconLocation from "@/components/icons/IconLocation.vue";
import IconArrow from "@/components/icons/IconArrow.vue";
import IconQuiz from "@/components/icons/IconQuiz.vue";
import IconHome from "@/components/icons/IconHome.vue";
import IconStats from "@/components/icons/IconStats.vue";
import IconCompare from "@/components/icons/IconCompare.vue";
import IconMap from "@/components/icons/IconMap.vue";
import IconList from "@/components/icons/IconList.vue";
import IconForum from "@/components/icons/IconForum.vue";
import IconEmail from "@/components/icons/IconEmail.vue";
import IconPhone from "@/components/icons/IconPhone.vue";
import FooterAbout from "@/components/layout/footer/FooterAbout.vue";
import FooterNav from "@/components/layout/footer/FooterNav.vue";
import FooterContact from "@/components/layout/footer/FooterContact.vue";

export default defineComponent({
  name: "Footer",
  components: {
    FooterContact,
    FooterNav,
    FooterAbout,
    IconPhone,
    IconEmail,
    IconForum,
    IconList, IconMap, IconCompare, IconStats, IconHome, IconQuiz, IconArrow, IconLocation, IconLogo
  }
})
</script>

<template>
  <footer class="relative bg-dark-blue text-light text-center p-6 xl:p-10 h-auto border-t-green border-t-12 w-full
  overflow-hidden">
    <!--    Footer content-->
    <div class="flex flex-col xl:flex-row justify-between z-100 relative">
      <FooterAbout/>
      <FooterNav/>
      <FooterContact/>
    </div>

    <!--    Background circles-->
    <div class="relative z-0">
      <div class="absolute -left-30 bottom-35 w-140 h-140 bg-transparent border-2 border-gray-blue rounded-full"></div>
      <div class="absolute -right-30 -top-50 w-150 h-150 bg-transparent border-2 border-gray-blue rounded-full"></div>
    </div>

  </footer>

  <!-- Bottom bar -->
  <div class="bg-green h-25">
    <p class="text-sm text-dark-blue pt-8 p-5 ml-8">
      © 2025 EU Political Platform. All rights reserved.
    </p>
  </div>
</template>
