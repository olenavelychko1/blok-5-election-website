<script lang="ts">
import { defineComponent } from "vue";
import ForumPosts from "@/components/forum/ForumPosts.vue";
import CreatePostModal from "@/components/forum/CreatePostModal.vue";

export default defineComponent({
  name: "ForumOverviewPage",
  components: { ForumPosts, CreatePostModal },

  data() {
    return {
      showModal: false,
      postsReloadKey: 0
    };
  },
  methods: {
    openModal() {
      this.showModal = true;
    },
    closeModal() {
      this.showModal = false;
    },
    refreshPosts() {
      this.postsReloadKey++;
    }
  }
});
</script>

<template>
  <section class="mb-10">
    <div class="flex items-center flex-col text-center my-25">
      <h1 class="text-6xl font-bold text-dark-blue">Forum</h1>
      <p class="text-md text-gray-blue w-100">
        Discuss and share your opinions on election results and political parties in the Netherlands
      </p>
    </div>
    <ForumPosts :key="postsReloadKey" />
  </section>

  <button
      class="fixed bottom-10 right-10 btn-blue w-75 h-20 z-100"
      @click="openModal"
  >
    <span class="text-light text-xl font-semibold">
      Start a discussion
    </span>
  </button>

  <CreatePostModal
      :visible="showModal"
      @close="closeModal"
      @created="refreshPosts"
  />
</template>
