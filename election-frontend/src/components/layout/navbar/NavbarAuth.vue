<script lang="ts">
import {defineComponent} from 'vue'
import IconCircleUser from "@/components/icons/IconCircleUser.vue";

export default defineComponent({
  name: "NavbarAuth",
  components: {IconCircleUser},
  props: {
    isAuthenticated: {
      type: Boolean,
      required: true
    }
  },
})
</script>

<template>
  <div>
    <!--    Navigation Links when authenticated-->
    <!--        TODO: Add correct links-->
    <div v-if="!isAuthenticated" class="hidden md:block">
      <button class="btn-blue absolute top-6 right-25 w-35">
        <router-link to="/login" class=" text-light text-md">
          Login
        </router-link>
      </button>
      <router-link
          to="/register"
          class="btn-clear-white absolute top-6 right-65 w-35 text-light text-md flex items-center justify-center">
        Register
      </router-link>
    </div>

    <!--    Navigation Links when not authenticated-->
    <!--        TODO: Add correct links-->
    <div v-else>
      <IconCircleUser class="w-10 absolute right-65 top-6 text-light "/>
      <button class="btn-clear-white absolute top-6 right-25 w-35">
        <router-link to="#" class=" text-light text-md">
          Logout
        </router-link>
      </button>
    </div>
  </div>
</template>
