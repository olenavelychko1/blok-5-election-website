<script lang="ts">
import {defineComponent} from 'vue'
import IconForum from "@/components/icons/IconForum.vue";
import IconStats from "@/components/icons/IconStats.vue";
import IconList from "@/components/icons/IconList.vue";
import IconCompare from "@/components/icons/IconCompare.vue";
import IconHome from "@/components/icons/IconHome.vue";
import IconMap from "@/components/icons/IconMap.vue";
import IconQuiz from "@/components/icons/IconQuiz.vue";

export default defineComponent({
  name: "NavbarMenu",
  components: {IconQuiz, IconMap, IconHome, IconCompare, IconList, IconStats, IconForum},
  props: {
    showMenu: {
      type: Boolean,
      required: true
    }
  },
  emits: ['update:showMenu'],
  methods: {
    /**
     * Handle clicks outside the menu to close it
     * @param event - MouseEvent
     */
    handleClickOutside(event: MouseEvent) {
      if (this.showMenu && this.$refs.menu // make sure menu ref exists
          && !(this.$refs.menu as HTMLElement).contains(event.target as Node) // click outside menu
          && !(event.target as HTMLElement).closest('button')) { // click is not on the toggle button
        this.$emit('update:showMenu', false)
      }
    },
    /**
     * Handle click on menu item to close the menu
     */
    handleClick() {
      this.$emit('update:showMenu', false)
    }
  },
  /**
   * Watch for changes in showMenu to add/remove event listener
   */
  watch: {
    showMenu(val) {
      if (val) {
        document.addEventListener('mousedown', this.handleClickOutside)
      } else {
        document.removeEventListener('mousedown', this.handleClickOutside)
      }
    }
  }
})
</script>

<template>
  <!-- Slide-in Menu -->
  <!--        TODO: Add correct links-->
  <div ref="menu">
    <ul :class="['fixed top-20 right-0 bg-dark-blue rounded-bl-xl w-100 md:w-64 overflow-hidden text-lg transition-transform ' +
       'duration-300', { 'translate-x-0': showMenu, 'translate-x-[100%]': !showMenu }]">
      <router-link to="/" @click="handleClick"
                   class="flex items-center hover:bg-gray-blue transition-colors transition-duration-500 pl-5 w-full">
        <IconHome class="w-8 text-light"/>
        <span class="block px-4 py-4 text-light">Home</span>
      </router-link>

      <router-link to="/election-results" @click="handleClick"
                   class="flex items-center hover:bg-gray-blue transition-colors transition-duration-500 pl-5 w-full">
        <IconStats class="w-8 text-light"/>
        <span class="block px-4 py-4 text-light">Elections Results</span>
      </router-link>

      <router-link to="/compare" @click="handleClick"
                   class="flex items-center hover:bg-gray-blue transition-colors transition-duration-500 pl-5 w-full">
        <IconCompare class="w-8 text-light"/>
        <span class="block px-4 py-4 text-light">Compare Elections</span>
      </router-link>

      <router-link to="#" @click="handleClick"
                   class="flex items-center hover:bg-gray-blue transition-colors transition-duration-500 pl-5 w-full">
        <IconMap class="w-8 text-light"/>
        <span class="block px-4 py-4 text-light">Election Map</span>
      </router-link>

      <router-link to="/parties" @click="handleClick"
                   class="flex items-center hover:bg-gray-blue transition-colors transition-duration-500 pl-5 w-full">
        <IconList class="w-8 text-light"/>
        <span class="block px-4 py-4 text-light">Party List</span>
      </router-link>

      <router-link to="/example" @click="handleClick"
                   class="flex items-center hover:bg-gray-blue transition-colors transition-duration-500 pl-5 w-full">
        <IconQuiz class="w-8 text-light"/>
        <span class="block px-4 py-4 text-light">Quiz</span>
      </router-link>

      <router-link to="/forum" @click="handleClick"
                   class="flex items-center hover:bg-gray-blue transition-colors transition-duration-500 pl-5 w-full">
        <IconForum class="w-8 text-light"/>
        <span class="block px-4 py-4 text-light">Forum</span>
      </router-link>

      <router-link to="/login" @click="handleClick"
                   class="flex items-center bg-blue hover:bg-gray-blue transition-colors pl-5 md:hidden w-full">
        <span class="text-light text-md cursor-pointer p-4">Login</span>
      </router-link>

      <router-link to="#" @click="handleClick"
                   class="flex items-center bg-green hover:bg-gray-blue transition-colors pl-5 md:hidden w-full">
        <span class="text-dark-blue text-md p-4">Register</span>
      </router-link>

    </ul>
  </div>
</template>
