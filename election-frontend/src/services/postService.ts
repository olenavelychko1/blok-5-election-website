import {IPostService} from "@/interfaces/IPostService";
import {IPost} from "@/interfaces/IPost";
import {IPageResponse} from "@/interfaces/IPageResponse";
import {ICreatePostRequest} from "@/interfaces/ICreatePostRequest";

/**
 * Service for managing posts.
 */
export class PostService implements IPostService {
    private url: string = import.meta.env.VITE_API_URL as string;

    /**
     * Fetches all posts.
     * @returns A promise that resolves to an array of posts.
     */
    public async getAll(sort: string = "desc", page: number = 0, size: number = 0, query?: string): Promise<IPageResponse<IPost>> {
        const q: string = query ? `&query=${encodeURIComponent(query)}` : '';
        const url: string = `${this.url}/posts?sort=createdAt,${sort}&page=${page}&size=${size}${q}`;

        const response: Response = await fetch(`${url}`, {
            method: 'GET',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json'
            }
        })
        if (!response.ok) {
            throw new Error('Failed to fetch posts');
        }
        return await response.json()
    }

    /**
     * Fetches a single post by its unique identifier.
     * @param id - The ID of the post to retrieve.
     * @returns A promise that resolves to the requested post.
     * @throws If the request fails or the server returns an error response.
     */
    public async getById(id: number): Promise<IPost> {
        const response = await fetch(`${this.url}/posts/${id}`, {
            method: "GET",
            credentials: "include",
            headers: {
                "Content-Type": "application/json"
            }
        });

        if (!response.ok) {
            const msg = await response.text();
            throw new Error(msg);
        }

        return await response.json();
    }

    /**
     * Creates a new post.
     * @param post - The post payload containing the data required to create a new post.
     * @returns A promise that resolves to the newly created post.
     * @throws If the request fails or the server returns an error response.
     */
    public async createPost(post: ICreatePostRequest): Promise<IPost> {
        const res = await fetch(`${this.url}/posts`, {
            method: 'POST',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(post)
        });

        if (!res.ok) {
            const errorMsg = await res.text();
            throw new Error(errorMsg);
        }

        return await res.json();
    }
}