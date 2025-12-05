export interface IUserService {
    register(username: string, email: string, password: string): Promise<IUserService>;
}