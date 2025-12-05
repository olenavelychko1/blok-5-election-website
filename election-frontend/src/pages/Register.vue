<script lang="ts">
import {defineComponent} from 'vue'
import IconLogo from "@/components/icons/IconLogo.vue";
import RegisterForm from "@/components/forms/RegisterForm.vue";
import Navbar from "@/components/layout/Navbar.vue";
import Footer from "@/components/layout/Footer.vue";

export default defineComponent({
  name: "Register",
  components: {Footer, Navbar, RegisterForm, IconLogo}
})
</script>

<template>
  <div class="relative bg-dark-blue min-h-screen overflow-x-hidden">

    <Navbar/>

    <div class="relative pt-10 pb-32">
      <!-- Left circle -->
      <div class="absolute -left-55 -top-100 w-240 h-240 border-2 border-gray-blue rounded-full"></div>

      <!-- Right circle -->
      <div class="absolute -bottom-70 -right-25 w-180 h-180 border-2 border-gray-blue rounded-full"></div>

      <RegisterForm/>
    </div>

    <Footer/>
  </div>
</template>

