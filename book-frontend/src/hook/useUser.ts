import {useState} from "react";
import {User, UserModel} from "../model/UserModel";
import {toast} from "react-toastify";
import axios from 'axios';
import jwtDecode, {JwtPayload} from 'jwt-decode';

export default function useUser() {
    const [user, setUser] = useState<User | null>(null);
    const [error, setError] = useState<boolean>();
    const [username, setUsername] = useState<string>();
    const baseUrl = "https://my-booklibrary.fly.dev";
    let tokenString: string | null = null;

    const isTokenExpired = (token: string): boolean => {
        const decodedToken = jwtDecode<JwtPayload>(token);
        const currentTime = Date.now() / 1000;
        return decodedToken.exp ? decodedToken.exp < currentTime : false;
    };

    const getTokenString = (): string | null => {
        const token = localStorage.getItem('token');
        if (token) {
            const tokenObject = JSON.parse(token);
            tokenString = tokenObject.token;
            if (tokenString && isTokenExpired(tokenString)) {
                logout().catch((e) => console.error(e));
                return null;
            }
        }
        return tokenString;
    };

    // create axios instance
    const api = axios.create({
        baseURL: baseUrl,
        withCredentials: true,
    });

    // intercept response to handle errors
    api.interceptors.response.use(undefined, error => {
        console.error(error);
        if (axios.isAxiosError(error)) {
            if (error.response) {
                console.error(error.response.data);
                console.error(error.response.status);
                console.error(error.response.headers);
            } else if (error.request) {
                console.error(error.request);
            } else {
                console.error('Error', error.message);
            }
        }
        return Promise.reject(error);
    });

    const login = async (username: string, password: string) => {
        try {
            const response = await api.post("/api/users/login", {
                username,
                password
            });
            localStorage.setItem('token', JSON.stringify(response.data));
            setUsername(username);
            return true;
        } catch (error) {
            return false;
        }
    }

    const logout = async () => {
        const token = getTokenString();
        return await api.post("/api/users/logout", undefined, {
            headers: {
                Authorization: `Bearer ${token}`,
            }
        }).then(() => {
            toast.info('YOU HAVE SUCCESSFULLY LOGGED OUT', {
                position: "top-center",
                autoClose: 1000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "colored",
            });
            setUser(null);
            window.location.reload()
            localStorage.removeItem('token');
            localStorage.removeItem('user');
            localStorage.removeItem('books');
        });
    }
    const createUser = async (newUser: UserModel) => {
        return await axios.post(baseUrl + "/api/users/signup", newUser, {
            withCredentials: true
        }).then(() => {
            return true;
        }).catch((error) => {
            if (error.response) {
                toast.error(error.response.data, {
                    position: "top-center",
                    autoClose: 1000,
                    hideProgressBar: false,
                    closeOnClick: true,
                    pauseOnHover: true,
                    draggable: true,
                    progress: undefined,
                    theme: "colored",
                })
                console.log(error.response.data);
            } else {
                // Handle other errors
            }
            console.error(error);
            return false;
        })
    }
    const loadUser = async (username: string) => {
        const token = getTokenString();
        if (token && !isTokenExpired(token)) {
            return await axios.get(baseUrl + `/api/users/${username}`, {
                withCredentials: true,
                headers: {
                    Authorization: `Bearer ${token}`,
                }
            }).then((response) => {
                setUser(response.data);
                localStorage.setItem('user', JSON.stringify(response.data));
            }).catch((error) => {
                console.error(error);
            });
        } else {
            // If token is expired or null, then logout
            await logout();
            toast.error('YOUR SESSION HAS EXPIRED. PLEASE LOG IN AGAIN.', {
                position: "top-center",
                autoClose: 1000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "colored",
            });
        }
    }

    const updateUser = async (user: User) => {
        const token = getTokenString();
        await axios.put(baseUrl + `/api/users/${user.username}`, user, {
            withCredentials: true,
            headers: {
                Authorization: `Bearer ${token}`,
            }
        }).then((response) => {
            setUser(response.data)
        }).catch(error => {
            console.error(error);
        })
    }
    return {
        user,
        username,
        error,
        setUser,
        login,
        logout,
        createUser,
        setError,
        loadUser,
        updateUser,
        getTokenString,
        isTokenExpired
    }
}





