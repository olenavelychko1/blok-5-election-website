import {IPost} from "@/interfaces/IPost";
import {IPageResponse} from "@/interfaces/IPageResponse";

export interface IPostService {
    getById(id: number): Promise<IPost>;
    getAll(sort: string, page: number, size: number, query?: string): Promise<IPageResponse<IPost>>;
}