interface ImportMetaEnv {
    readonly VITE_API_URL: string;
    readonly VITE_BACKEND_URL: string;
    readonly VITE_BACKEND_PORT: string | number;
}

interface ImportMeta {
    readonly env: ImportMetaEnv;
}