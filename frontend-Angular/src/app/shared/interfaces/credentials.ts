import { UserRole } from "../enums/user-role"

export interface Credentials {
    username: string,
    password: string,
}

export interface LoggedInUser {
    username: string,
    role: string
}
