<script lang="ts">
import {defineComponent} from 'vue'
import IntroductionSection from "@/components/party/IntroductionSection.vue";
import PartyListSection from "@/components/party/PartyListSection.vue";
import usePartiesMixen from "@/mixins/usePartiesMixin";
import Paginator from "@/components/party/Paginator.vue";
import Sorter from "@/components/party/Sorter.vue";
import ISelectOption from "@/interfaces/form/ISelectOption";
import ISortOption from "@/interfaces/ISortOption";

export default defineComponent({
  name: "PartyPage",
  components: {Sorter, IntroductionSection, PartyListSection, Paginator},
  mixins: [usePartiesMixen],
  methods: {
    setElectionId(id: string) {
      this.filter.electionId = id;
    }
  },
  setup() {
    const filterOptions: ISelectOption[] = [
      {
        label: '2021',
        value: 'TK2021',
      },
      {
        label: '2023',
        value: 'TK2023'
      },
      {
        label: '2025',
        value: 'TK2025'
      }];
    const sortOptions: ISortOption[] = [{
      label: 'name',
      key: 'name'
    }];

    return {
      filterOptions,
      sortOptions
    }
  }
})
</script>

<template>
  <header>
  </header>

  <main class="min-h-screen flex flex-col items-center">
    <div class="md:w-2/3 xl:w-1/2 md:p-0 px-4 w full">
      <IntroductionSection/>
      <div v-if="error" class="text-center text-red-500">{{ error }}</div>
      <div v-else>
        <Sorter
            :filter-value="filter.electionId"
            :filter-options="filterOptions"
            :onFilterChange="setElectionId"
            v-model:sort="filter.sort"
            :sortOptions="sortOptions"
        />
        <PartyListSection :parties="parties" :loading="loading" :error="error"/>
        <Paginator
            :page="filter.page"
            :size="filter.size"
            :total-parties-count="totalPartiesCount"
            :total-parties-count-loading="totalPartiesCountLoading"
            @update:page="(val: Number) => filter.page = val"
            @update:size="(val: Number) => filter.size = val"
        />
      </div>
    </div>
  </main>
</template>
