export interface Pageable {
    page: number;
    size: number;
    sort: Array<{
        property: string;
        direction: "ASC" | "DESC";
    }>;
}