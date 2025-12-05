<script lang="ts">
import {defineComponent} from 'vue'
import PaginationNavigation from "@/components/utils/PaginationNavigation.vue";
import Loading from "@/components/utils/Loading.vue";

export default defineComponent({
    name: "PageSelector",
    components: {Loading, PaginationNavigation},
      props: {
        page: Number,
        amount: Number,
      },
      emits: ["update:page"],
      setup(_, { emit }) {
        const setValue = (val: number) => {
          emit("update:page", val)
        }

        return { setValue }
      }
    })
</script>

<template>
  <PaginationNavigation :page="page" :amount="amount" :setPage="setValue" />
</template>
