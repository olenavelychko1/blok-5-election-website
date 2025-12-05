<script lang="ts">
import {defineComponent} from 'vue'

export default defineComponent({
  name: "PaginationNavigation",
  props: {
    page: Number,
    setPage: {
      type: Function as unknown as (val: Number) =>  void,
    },
    amount: Number
  }
})
</script>

<template>
  <ul class="flex items-center -space-x-px h-8 text-sm">
    <li>
      <button @click="page !== 1 && setPage(page - 1)">
      <span
          class="flex items-center h-10 justify-center p-2.5 ms-0 leading-tight text-gray-500 bg-white border border-e-0 border-gray-300 rounded-s-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">
        <svg class="w-2.5 h-2.5 rtl:rotate-180" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
             viewBox="0 0 6 10">
          <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 1 1 5l4 4"/>
        </svg>
      </span>
      </button>
    </li>
    <li>
      <button v-for="i in amount" :key="i" @click="() => setPage(i) ">
      <span
          class="flex items-center h-10 p-2.5 leading-tight bg-white text-gray-500 border border-gray-300 hover:bg-gray-100 hover:text-gray-700"
          :class="i === page && 'font-bold'"
      >
        {{ i }}
      </span>
      </button>
    </li>
    <li>
      <button @click="page !== amount && setPage(page + 1)">
      <span
          class="flex items-center h-10 justify-center px-3 h-8 leading-tight text-gray-500 bg-white border border-gray-300 rounded-e-lg hover:bg-gray-100 hover:text-gray-700 dark:bg-gray-800 dark:border-gray-700 dark:text-gray-400 dark:hover:bg-gray-700 dark:hover:text-white">
        <svg class="w-2.5 h-2.5 rtl:rotate-180" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none"
             viewBox="0 0 6 10">
          <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m1 9 4-4-4-4"/>
        </svg>
      </span>
      </button>
    </li>
  </ul>
</template>