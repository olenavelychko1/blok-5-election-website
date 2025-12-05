<script lang="ts">
import { defineComponent } from "vue";
import type { ICreatePostRequest } from "@/interfaces/ICreatePostRequest";
import {PostService} from "@/services/postService";
import {useToast} from "vue-toast-notification";

export default defineComponent({
  name: "CreatePostModal",

  props: {
    visible: {
      type: Boolean,
      required: true
    }
  },

  emits: ["close", "created"],

  data() {
    return {
      title: "",
      content: "",
      postService: new PostService(),
    };
  },

  methods: {
    close() {
      this.$emit("close");
      this.title = "";
      this.content = "";
    },

    async submit() {
      const toast = useToast();

      const payload: ICreatePostRequest = {
        userId: 1, // TODO change id when login is done
        title: this.title,
        content: this.content,
      };

      try {
        const createdPost = await this.postService.createPost(payload);

        toast.success("Post created successfully!", {
          duration: 3000,
          position: "bottom-left"
        });

        this.$emit("created", createdPost);
        this.close();

      } catch (err) {
        toast.error(err.message, {
          duration: 4000,
          dismissible: true,
          position: "bottom-left"
        });
      }
    }

  }
});
</script>

<template>
  <div
      v-if="visible"
      class="fixed inset-0
         bg-black/20
         backdrop-blur-sm
         flex items-center justify-center
         z-100"
  >
    <div class="bg-white rounded-xl p-8 w-[500px] shadow-xl z-101">

      <h2 class="text-2xl font-bold mb-5">Create a new post</h2>

      <!-- TITLE -->
      <label class="block mb-2 font-semibold">Title</label>
      <input
          v-model="title"
          class="w-full border rounded-lg p-2 mb-4"
          placeholder="Enter title..."
      />

      <!-- CONTENT -->
      <label class="block mb-2 font-semibold">Content</label>
      <textarea
          v-model="content"
          class="w-full border rounded-lg p-2 h-32 mb-5"
          placeholder="Write your content..."
      ></textarea>

      <div class="flex justify-end gap-4">
        <!-- Cancel -->
        <button
            class="px-4 py-2 rounded-lg
         bg-gray-300
         text-black
         hover:bg-red-500
         hover:text-white
         transition-colors duration-200"
            @click="close"
        >
          Cancel
        </button>

        <!-- Submit -->
        <button
            class="px-4 py-2 btn-blue text-white rounded-lg"
            @click="submit"
        >
          Submit
        </button>
      </div>

    </div>
  </div>
</template>
