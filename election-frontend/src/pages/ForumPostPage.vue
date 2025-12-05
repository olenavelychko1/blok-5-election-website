<script lang="ts">
import { defineComponent } from "vue";
import QuestionForumCard from "@/components/forum/QuestionForumCard.vue";
import { PostService } from "@/services/postService";
import LoadingState from "@/components/ui/LoadingState.vue";
import ErrorState from "@/components/ui/ErrorState.vue";

export default defineComponent({
  name: "ForumPostPage",
  components: {ErrorState, LoadingState, QuestionForumCard },

  props: {
    id: { type: String, required: true }
  },

  data() {
    return {
      post: null,
      loading: true,
      error: "",
      postService: new PostService()
    };
  },

  /**
   * Fetches the post by ID when the component is created.
   * Shows a loading state, handles errors, and stores the result.
   */
  async created() {
    try {
      this.post = await this.postService.getById(Number(this.id));
    } catch (err: any) {
      this.error = err.message;
    } finally {
      this.loading = false;
    }
  }
});
</script>

<template>
  <section class="min-h-screen w-full flex justify-center pt-28 pb-24">

    <LoadingState v-if="loading" class="h-150"/>
    <ErrorState v-else-if="error"/>

    <div v-else class="w-full max-w-5xl px-6">
      <button
          @click="$router.push('/forum')"
          class="flex items-center gap-2 text-dark-blue hover:text-blue-800 mb-6 font-medium"
      >
        <span class="text-xl">←</span>
        Back to forum
      </button>

      <QuestionForumCard :post="post" />
    </div>

  </section>
</template>
