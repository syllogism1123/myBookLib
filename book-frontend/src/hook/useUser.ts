import axios from "axios";
import {useState} from "react";
import {User, UserModel} from "../model/UserModel";

export default function useUser() {
    const [user, setUser] = useState<User | null>(null);
    const [error, setError] = useState<boolean>();
    const [username, setUsername] = useState<string>();

    const login = async (username: string, password: string) => {
        return await axios.post("http://localhost:8080/api/users/login", undefined, {
            withCredentials: true,
            auth: {
                username,
                password
            }
        }).then((r) => {
            setUsername(r.data);
            setUser(r.data);
            return true;
        }).catch(error => {
            console.error(error);
            return false;
        });
    }
    const logout = async () => {
        return await axios.post("http://localhost:8080/api/users/logout", undefined, {
            withCredentials: true,
        }).then(() => {
            setUser(null);
            localStorage.clear();
            window.location.reload();
        }).catch(error => {
            console.error(error);
        })
    }

    const createUser = async (newUser: UserModel) => {
        return await axios.post("http://localhost:8080/api/users/signup", newUser, {
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
        return await axios.get(`http://localhost:8080/api/users/${username}`, {
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
        await axios.put(`http://localhost:8080/api/users/${user.username}`, user, {
            withCredentials: true
        }).then((response) => {
            setUser(response.data)
        }).catch(error => {
            console.error(error);
        })
    }


    return {user, username, error, setUser, login, logout, createUser, setError, loadUser, updateUser}
}





