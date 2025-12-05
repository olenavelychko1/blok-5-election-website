<script lang="ts">
import { defineComponent } from 'vue'
import IconLogo from "@/components/icons/IconLogo.vue";
import {UserService} from "@/services/UserService";

export default defineComponent({
  name: 'RegisterForm',
  components: { IconLogo },

  /**
   * Reactive form fields and validation errors.
   */
  data() {
    return {
      username: "",
      email: "",
      password: "",
      confirmPassword: "",
      showPassword: false,
      showConfirmPassword: false,
      errors: {
        username: "",
        email: "",
        password: "",
        confirmPassword: ""
      }
    };
  },

  methods: {
    /**
     * Validates the username field and returns an error message or an empty string if valid
     */
    validateUsername() {
      if (!this.username) {
        return "Username is required.";
      }
      if (this.username.length < 5) {
        return "Username must be at least 5 characters.";
      }
      return "";
    },

    /**
     * Validates the email format and required field, returns an error message or empty string
     */
    validateEmail() {
      if (!this.email) {
        return "Email is required.";
      }
      if (!/^\S+@\S+\.\S+$/.test(this.email)) {
        return "Email is not valid.";
      }
      return "";
    },

    /**
     * Checks if the password meets the required rules and returns an error message or empty string
     */
    validatePassword() {
      if (!this.password) {
        return "Password is required.";
      }
      if (this.password.length < 8) {
        return "Password must be at least 8 characters.";
      }
      return "";
    },

    /**
     * Ensures confirmPassword matches the password, returns an error message or empty string
     */
    validateConfirmPassword() {
      if (this.confirmPassword !== this.password) {
        return "Passwords do not match.";
      }
      return "";
    },

    /**
     * Runs all validation methods, updates error fields and returns true if the form is valid
     */
    validateFields() {
      this.errors.username = this.validateUsername();
      this.errors.email = this.validateEmail();
      this.errors.password = this.validatePassword();
      this.errors.confirmPassword = this.validateConfirmPassword();

      return (
          !this.errors.username &&
          !this.errors.email &&
          !this.errors.password &&
          !this.errors.confirmPassword
      );
    },

    /**
     * Validates form and sends the register request to the backend.
     * Redirects to login on success, maps backend errors to fields.
     */
    async submitForm() {
      if (!this.validateFields()) {
        return;
      }

      try {
        const userService = new UserService()
        await userService.register(this.username, this.email, this.password);

        this.$router.push("/login");

      } catch (err: any) {
          const msg = err.message;

          if (msg.includes("Email already exists")) {
              this.errors.email = msg;
        }
          if (msg.includes("Username already exists")) {
            this.errors.username = msg;
        }
      }
    }
  }
});

</script>

<template>
  <div
      class="w-full max-w-xl mx-auto bg-white rounded-xl shadow-lg p-10 space-y-8 -scale-z-200"
  >


    <!-- Title -->
    <h2 class="text-3xl font-bold text-center text-dark-blue">
      Create an account
    </h2>

    <!-- Form -->
    <form class="space-y-6" @submit.prevent="submitForm">

      <!-- Username -->
      <div>
        <label class="block mb-2 text-lg font-medium text-dark-blue">Username</label>

        <input
            type="text"
            v-model="username"
            :class="['w-full px-5 py-4 border rounded-lg focus:ring-2 focus:outline-none text-lg',
            errors.username ? 'border-red-500 focus:ring-red-300' : 'border-gray-300 focus:ring-blue-300']"
            placeholder="Enter your username"
        />

        <p v-if="errors.username" class="text-red-500 mt-1 text-sm">
          {{ errors.username }}
        </p>
      </div>

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



      <!-- Confirm Password -->
      <div>
        <label class="block mb-2 text-lg font-medium text-dark-blue">Confirm Password</label>

        <div class="relative">
          <input
              :type="showConfirmPassword ? 'text' : 'password'"
              v-model="confirmPassword"
              class="w-full px-5 py-4 border rounded-lg text-lg focus:ring-2 focus:outline-none"
              :class="errors.confirmPassword ? 'border-red-500 focus:ring-red-300' : 'border-gray-300 focus:ring-blue-300'"
              placeholder="Confirm your password"
          />

          <!-- Eye icon -->
          <span
              class="absolute inset-y-0 right-4 flex items-center cursor-pointer text-dark-blue"
          >
          <font-awesome-icon
              :icon="showConfirmPassword ? 'eye-slash' : 'eye'"
              class="text-dark-blue cursor-pointer"
              @click="showConfirmPassword = !showConfirmPassword"
          />
          </span>
        </div>

        <p v-if="errors.confirmPassword" class="text-red-500 mt-2">
          {{ errors.confirmPassword }}
        </p>
      </div>

      <!-- Already have account -->
      <p class="text-center text-gray-600 text-lg">
        Already have an account?
        <router-link
            to="/login"
            class="text-blue-600 font-medium hover:underline"
        >
          Login
        </router-link>
      </p>

      <!-- Register Button -->
      <button
          type="submit"
          class="w-full py-4 rounded-lg text-xl font-semibold text-white bg-[#4D6683] hover:bg-[#3b5168]
          transition-colors duration-200 cursor-pointer"
      >
        Register
      </button>
    </form>
  </div>
</template>