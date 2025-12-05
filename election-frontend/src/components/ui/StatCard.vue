<script lang="ts">
import {defineComponent} from 'vue'
import IconLocation from "@/components/icons/IconLocation.vue";

export default defineComponent({
  name: "StatCard",
  components: {IconLocation},
  props: {
    bgGradient: {
      type: String,
      required: true
    },
    text: {
      type: String,
      required: true
    },
    data: {
      type: Number,
      required: true
    }
  }
})
</script>

<template>
  <div :class="`${bgGradient} p-10 border-5 border-white rounded-3xl w-60 h-50 flex justify-center items-center flex-col
  hover:scale-103 transition-transform duration-800 ease-out shadow-md`">
    <div class="flex flex-row justify-center items-center">
      <slot name="icon"></slot>
      <span class="text-5xl text-dark-blue font-bold" :class="{ 'text-lg': data === 'Loading...' }">
        {{ data }}
      </span>
    </div>
    <p class="text-gray-blue text-center mt-2">{{ text }}</p>
  </div>
</template>
