/**
 * Response structure for paginated data.
 * Can be used for any type T, such as IPost.
 */
export interface IPageResponse<T> {
    content: T[];
    totalElements: number;
    totalPages: number;
    number: number;
    first: boolean;
    last: boolean;
}
