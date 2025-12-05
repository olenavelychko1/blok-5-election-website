<script lang="ts">
import {defineComponent} from 'vue'
import IconForum from "@/components/icons/IconForum.vue";
import {IPost} from "@/interfaces/IPost";
import IconHeart from "@/components/icons/IconHeart.vue";
import Highlighter from 'vue-highlight-words'

export default defineComponent({
  name: "ForumPostCard",
  components: {IconHeart, IconForum, Highlighter},
  props: {
    post: {
      type: Object as () => IPost,
      required: true
    },
    query: {
      type: String,
      required: false,
      default: ''
    }
  },
  computed: {
    /**
     * Splits the search query into individual keywords for highlighting.
     */
    keywords() {
      if (this.query) {
        return this.query.split(' ').map((word: string) => this.escapeForRegExp(word));
      } else {
        return [];
      }

    }
  },
  methods: {
    /**
     * Formats a date string into a more readable format.
     * @param dateString - The date string to format.
     * @returns A formatted date string in the format "DD Month YYYY at HH:MM"
     */
    formatDate(dateString: Date): string {
      const date = new Date(dateString);
      const datePart = new Date(dateString).toLocaleDateString("en-GB", {
        day: "numeric",
        month: "long",
        year: "numeric"
      });
      const timePart = date.toLocaleTimeString("en-GB", {
        hour: "2-digit",
        minute: "2-digit",
      });
      return `${datePart} at ${timePart}`;
    },
    /**
     * Escapes special characters in a string for use in a regular expression.
     * @param value
     */
    escapeForRegExp(value: string): string {
      return value.replace(/[.*+?^${}()|[\]\\]/g, '\\$&');
    },
  }
})
</script>

<template>
  <router-link :to="{ name: 'forum-post', params: { id: post.id } }" class="min-h-30 p-5 flex flex-col gap-2 text-left">
    <time class="text-gray-blue">
      {{ formatDate(post.createdAt) }} &nbsp; • &nbsp;<timeago :datetime="post.createdAt"/>
    </time>
    <Highlighter
        class="text-dark-blue text-3xl font-semibold"
        highlightClassName="bg-yellow-200"
        :searchWords="keywords"
        :textToHighlight="post.title"
    />
    <p class="text-gray-blue text-sm -mt-2"> Posted by <span class="font-semibold ">{{
        post.authorName
      }}</span></p>
    <hr class="border-gray-blue opacity-50">
    <p class="text-gray-blue wrap-break-word"> {{ post.content.slice(0, 200) }}... </p>

    <!--    Bottom stats, comment and likes count-->
    <div class="flex flex-row items-center justify-between gap-2 mt-8">
      <div class="flex flex-row items-center gap-2">
        <IconForum class="w-10 text-dark-blue"/>
        <p class="text-dark-blue text-lg font-semibold">
          {{ post.commentCount }}
        </p>
      </div>
      <!--      TODO: uncomment when likes is implemented-->
      <!--      <div class="flex flex-row items-center gap-2">-->
      <!--        <IconHeart class="text-dark-blue w-10"/>-->
      <!--        <p class="text-dark-blue text-lg font-semibold">-->
      <!--          {{ post.likeCount }}-->
      <!--        </p>-->
      <!--      </div>-->
    </div>
  </router-link>
</template>
