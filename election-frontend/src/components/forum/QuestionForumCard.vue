<script lang="ts">
import { defineComponent } from "vue";
import type { IPost } from "@/interfaces/IPost";

export default defineComponent({
  name: "QuestionForumCard",

  props: {
    post: {
      type: Object as () => IPost,
      required: true
    }
  }
});
</script>

<template>
  <div class="border bg-white border-gray-400 rounded-xl p-10">

    <!-- TITLE -->
    <h1 class="text-3xl font-bold text-dark-blue mb-3">
      {{ post.title }}
    </h1>

    <!-- TIME AGO + COMMENT COUNT -->
    <div class="flex items-center gap-2 text-sm text-gray-500 mb-6">
      <timeago :datetime="post.createdAt" />
      <span class="w-1 h-1 bg-gray-400 rounded-full"></span>
      <span>{{ post.commentCount }} replies</span>
    </div>

    <!-- HORIZONTAL LINE -->
    <hr class="border-gray-400 my-6 mx-4" />

    <!-- AUTHOR SECTION -->
    <div class="flex items-center gap-3 mb-6">
      <div class="w-10 h-10 rounded-full bg-blue-200 flex items-center justify-center text-dark-blue font-semibold">
        {{ post.authorName.charAt(0).toUpperCase() }}
      </div>
      <div>
        <p class="font-semibold text-gray-800">{{ post.authorName }}</p>
        <p class="text-sm text-gray-500">Original poster</p>
      </div>
    </div>

    <!-- FULL CONTENT -->
    <p class="text-gray-700 leading-relaxed whitespace-pre-line mb-8">
      {{ post.content }}
    </p>

  </div>
</template>
