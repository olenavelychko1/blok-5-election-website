import {IPostService} from "@/interfaces/IPostService";
import {PostService} from "@/services/postService";
import {IPost} from "@/interfaces/IPost";

const service: IPostService = new PostService()

/**
 * Mixin for fetching posts.
 * Manages pagination and searching by titel
 */
export default {
    data() {
        return {
            posts: [] as IPost[],
            loadingPosts: false,
            error: null,

            currentPage: 0,
            pageSize: 5,
            totalPages: 0,
            currentQuery: '',
        }
    },
    methods: {
        /**
         * Fetches a page of posts.
         * @param page - The page number to fetch.
         * @param query - Optional search query to filter posts by title.
         */
        async fetchPage(page = 0, query = '') {
            this.loadingPosts = true;
            this.error = null;
            this.currentQuery = query;

            try {
                const pageResponse = await service.getAll("desc", page, this.pageSize, query);

                // Update posts and pagination info
                this.posts = pageResponse.content;
                this.currentPage = pageResponse.number;
                this.totalPages = pageResponse.totalPages;
            } catch (err) {
                this.error = err?.message ?? "Failed to load posts.";
            } finally {
                this.loadingPosts = false;
            }
        },
    },
    /**
     * Fetch the first page of posts when the component is created.
     */
    created() {
        this.fetchPage(0);
    }
}
