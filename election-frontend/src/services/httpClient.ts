class HttpClient {
    private baseURL: string = import.meta.env.VITE_API_URL as string;

    constructor() {
    }

    public async get<T>(endpoint: string): Promise<T> {
        const response: Response = await fetch(`${this.baseURL}${endpoint}`, {
            method: 'GET',
            credentials: 'include',
            headers: {
                'Content-Type': 'application/json'
            }
        });

        if (!response.ok) {
            throw new Error(`GET ${endpoint} failed: ${response.statusText}`);
        }
        return await response.json() as Promise<T>;
    }

    public async post<T, B = unknown>(endpoint: string, data: B): Promise<T> {
        const response = await fetch(`${this.baseURL}${endpoint}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        });

        if (!response.ok) {
            throw new Error(`POST ${endpoint} failed: ${response.statusText}`);
        }

        return await response.json() as Promise<T>;
    }
}

const httpClient = new HttpClient();

export default httpClient;