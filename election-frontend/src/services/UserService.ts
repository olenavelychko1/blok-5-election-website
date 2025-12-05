import {IUserService} from "@/interfaces/IUserService";
import {IUser} from "@/interfaces/IUser";

/**
 * Service responsible for communicating with the backend User API.
 */
export class UserService implements IUserService {
    private url: string = import.meta.env.VITE_API_URL as string;

    /**
     * Sends a registration request to the backend.
     *
     * @param username - The username provided by the user
     * @param email - The user's email address
     * @param password - The user's chosen password
     *
     * @returns The created user object returned by the backend
     *
     * @throws Error when the backend returns a non-OK status code
     */
    public async register(username: string, email: string, password: string): Promise<IUserService> {
        const res: Response = await fetch(`${this.url}/users`, {
            method: "POST",
            body: JSON.stringify({ username, email, password }),
            headers: {
                "Content-Type": "application/json"
            },
        })

        // If the backend returns 400 or other errors, throw the message for UI handling
        if (!res.ok) {
            const errorMsg = await res.text();
            throw new Error(errorMsg);
        }

        return await res.json();
    }

    /**
     * Authenticates a user with the provided email and password.
     *
     * @param {string} email - The email address of the user trying to log in.
     * @param {string} password - The password associated with the given email.
     * @return {Promise<IUser>} A promise that resolves to the authenticated user's data if login is successful.
     * @throws {Error} Throws an error if the login request fails or the server returns a non-OK status.
     */
    public async login(email: string, password: string): Promise<IUser> {
        const res: Response = await fetch(`${this.url}/users/login`, {
            method: "POST",
            body: JSON.stringify({ email, password }),
            headers: {
                "Content-Type": "application/json"
            },
            credentials: 'include',
        })

        if (!res.ok) {
            const message = await res.text();
            throw new Error(message || `Login failed with status ${res.status}`);
        }

        return (await res.json()) as IUser;
    }
}