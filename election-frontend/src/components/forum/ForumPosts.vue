<script lang="ts">
import {defineComponent} from 'vue'
import ForumPostCard from "@/components/forum/ForumPostCard.vue";
import usePosts from "@/mixins/usePosts";
import LoadingState from "@/components/ui/LoadingState.vue";
import ErrorState from "@/components/ui/ErrorState.vue";
import IconForum from "@/components/icons/IconForum.vue";
import IconArrow from "@/components/icons/IconArrow.vue";
import ForumSearch from "@/components/forum/ForumSearch.vue";
import {IPost} from "@/interfaces/IPost";
import IconSearch from "@/components/icons/IconSearch.vue";
import ForumPagination from "@/components/forum/ForumPagination.vue";
import PaginationNavigation from "@/components/utils/PaginationNavigation.vue";

/**
 * ForumPosts component displays a list of forum posts with search functionality.
 */
export default defineComponent({
  name: "ForumPosts",
  components: {
    PaginationNavigation,
    ForumPagination, IconArrow, IconSearch, ForumSearch, IconForum, ErrorState, LoadingState, ForumPostCard},
  mixins: [usePosts],
  methods: {
    /**
     * Handles the search event from the ForumSearch component.
     * @param query - The search query string.
     */
    handleSearch(query: string) {
      this.fetchPage(0, query);
    },
    setPage(page: number) {
      this.fetchPage(page - 1, this.currentQuery);
    }
  },
  computed: {
    /**
     * Checks if there are no posts available.
     */
    noPosts() {
      return this.posts.length === 0;
    }
  }
})
</script>

<template>
  <ForumSearch @search="handleSearch" v-model:query="currentQuery"/>

  <LoadingState v-if="loadingPosts" class="h-150"/>
  <ErrorState v-else-if="error"/>

  <section v-else-if="noPosts && !currentQuery" class="flex flex-col items-center justify-center py-25">
    <IconForum class="w-20 text-dark-blue"/>
    <h3 class="text-dark-blue text-3xl font-semibold mt-4">No forum posts yet</h3>
    <p class="text-gray-blue">Be the first to start a discussion and share your thoughts on the election!</p>
  </section>

  <section v-else class="w-full flex flex-col gap-6 items-center justify-center">
    <div v-if="noPosts && currentQuery" class="h-125 flex flex-col items-center justify-center">
      <IconSearch class="w-20 h-20 text-dark-blue"/>
      <h3 class="text-dark-blue text-3xl font-semibold mt-4">No posts found for your search</h3>
      <p class="text-gray-blue">Try adjusting your search criteria to find what you're looking for.</p>
    </div>

    <article v-for="post in posts" :key="post.id" class="w-3/4 bg-white p-5 rounded-xl shadow-sm cursor-pointer
    hover:bg-light-blue hover:-translate-y-2 transition-all duration-500 ease-out">
      <ForumPostCard :post="post" :query="currentQuery"/>
    </article>
  </section>
  <PaginationNavigation
      v-if="totalPages > 1"
      class="flex items-center justify-center mt-10"
      :page="currentPage + 1"
      :amount="totalPages"
      :setPage="setPage"/>
</template>
