<script lang="ts">
import {defineComponent} from 'vue'

export default defineComponent({
  name: "HamburgerMenu",
  props: {
    showMenu: {
      type: Boolean,
      required: true
    }
  },
})
</script>

<template>
  <button class="absolute right-5 top-7 cursor-pointer">
      <span :class="['block w-10 h-1 bg-light rounded mb-2 transition-all duration-300',
        showMenu ? 'transform translate-y-3 rotate-45' : '']">
      </span>
    <span :class="['block w-10 h-1 bg-light rounded mb-2 transition-all duration-300',
        showMenu ? 'opacity-0' : '']">
      </span>
    <span :class="['block w-10 h-1 bg-light rounded transition-all duration-300',
        showMenu ? 'transform -translate-y-3 -rotate-45' : '']">
      </span>
  </button>
</template>
