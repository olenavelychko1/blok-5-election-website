<!--component will be split up in next sprint-->
<script lang="ts">
import { defineComponent } from 'vue'
import IconLogo from "@/components/icons/IconLogo.vue";
import useLogin from "@/mixins/useLogin";

export default defineComponent({
  name: 'LoginForm',
  components: { IconLogo },
  mixins: [useLogin]
});

</script>

<template>
  <div
      class="w-full max-w-xl mx-auto bg-white rounded-xl shadow-lg p-10 space-y-8 -scale-z-200"
  >


    <!-- Title -->
    <h2 class="text-3xl font-bold text-center text-dark-blue">
      Sign up
    </h2>

    <!-- Form -->
    <form class="space-y-6" @submit.prevent="submitForm">

      <!-- Email -->
      <div>
        <label class="block mb-2 text-lg font-medium text-dark-blue">Email</label>

        <input
            type="text"
            v-model="email"
            :class="['w-full px-5 py-4 border  rounded-lg focus:ring-2 focus:outline-none text-lg',
            errors.email ? 'border-red-500 focus:ring-red-300' : 'border-gray-300 focus:ring-blue-300']"
            placeholder="Enter your email"
        />

        <p v-if="errors.email" class="text-red-500 mt-1 text-sm">
          {{ errors.email }}
        </p>
      </div>

      <!-- Password -->
      <div>
        <div class="flex items-center gap-2 mb-2">
          <label class="text-lg font-medium text-dark-blue">Password</label>

          <!-- Info icon with tooltip -->
          <div class="relative group cursor-pointer">
            <span class="text-gray-400 hover:text-gray-600 text-sm select-none">
              <img class="h-5 w-5" src="/images/i-icon.png" alt="i-icon">
            </span>

            <!-- Tooltip -->
            <div
                class="absolute left-1/2 -translate-x-1/2 mt-2 w-max
               rounded-md bg-gray-800 text-white text-xs py-1 px-2
               opacity-0 group-hover:opacity-100 transition-opacity duration-200
               whitespace-nowrap pointer-events-none z-10"
            >
              Must be at least 8 characters.
            </div>
          </div>
        </div>

        <div class="relative">
          <input
              :type="showPassword ? 'text' : 'password'"
              v-model="password"
              class="w-full px-5 py-4 border rounded-lg text-lg focus:ring-2 focus:outline-none"
              :class="errors.password ? 'border-red-500 focus:ring-red-300' : 'border-gray-300 focus:ring-blue-300'"
              placeholder="Enter your password"
          />

          <!-- Eye icon -->
          <span
              class="absolute inset-y-0 right-4 flex items-center cursor-pointer text-dark-blue"
              @click="showPassword = !showPassword"
          >
      <font-awesome-icon
          :icon="showPassword ? 'eye-slash' : 'eye'"
          class="text-dark-blue cursor-pointer"
      />
    </span>
        </div>

        <p v-if="errors.password" class="text-red-500 mt-2">{{ errors.password }}</p>
      </div>

      <!-- Don't have an account yet -->
      <p class="text-center text-gray-600 text-lg">
        Don't have an account yet?
        <router-link
            to="/register"
            class="text-blue-600 font-medium hover:underline"
        >
          register here
        </router-link>
      </p>

      <!-- Register Button -->
      <button
          type="submit"
          class="w-full py-4 rounded-lg text-xl font-semibold text-white bg-[#4D6683] hover:bg-[#3b5168]
          transition-colors duration-200 cursor-pointer"
      >
        Sign up
      </button>
    </form>
  </div>
</template>
