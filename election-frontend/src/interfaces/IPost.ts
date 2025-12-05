export interface IPost {
    id: number;
    userId: number,
    title: string;
    content: string;
    createdAt: Date;
    authorName: string;
    commentCount: number;
    likeCount: number;
}