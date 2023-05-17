import axios from "axios";
import {useState} from "react";
import {User, UserModel} from "../model/UserModel";

export default function useUser() {
    const [user, setUser] = useState<User | null>(null);
    const [error, setError] = useState<boolean>();
    const [username, setUsername] = useState<string>();
    const baseUrl = "https://my-booklibrary.fly.dev";
    const login = async (username: string, password: string) => {
        try {
            const response = await axios.post(baseUrl + "/api/users/login", {
                username: username,
                password: password
            }, {
                withCredentials: true,
                auth: {
                    username,
                    password
                }
            });
            if (response.status === 200) {
                setUsername(username);
                setUser(response.data.user);
                return true;
            } else if (response.status === 401) {
                if (response.data === "Invalid password") {
                    console.log(response.data)
                } else if (response.data === "User not found")
                    console.log(response.data)
                return false;
            } else {
                return false;
            }
        } catch (error) {
            console.error(error);
            return false;
        }
    };

    const logout = async () => {
        return await axios.post(baseUrl + "/api/users/logout", undefined, {
            withCredentials: true,
        }).then(() => {
            setUser(null);
            localStorage.clear();
        }).catch(error => {
            console.error(error);
        })
    }

    const createUser = async (newUser: UserModel) => {
        return await axios.post(baseUrl + "/api/users/signup", newUser, {
            withCredentials: true
        }).then((response) => {
            setUser(response.data)
            return true;
        }).catch((error) => {
            console.error(error);
            return false;
        })
    }

    const loadUser = async (username: string) => {
        return await axios.get(baseUrl + `/api/users/${username}`, {
            withCredentials: true
        }).then((response) => {
            setUser(response.data)
            const expirationTime = 60 * 1000 * 5;
            localStorage.setItem('token', JSON.stringify(response.data));
            localStorage.setItem('expiration', JSON.stringify(Date.now() + expirationTime));
        }).catch((error) => {
            console.error(error);
        })
    }

    const updateUser = async (user: User) => {
        await axios.put(baseUrl + `/api/users/${user.username}`, user, {
            withCredentials: true
        }).then((response) => {
            setUser(response.data)
        }).catch(error => {
            console.error(error);
        })
    }


    return {user, username, error, setUser, login, logout, createUser, setError, loadUser, updateUser}
}





