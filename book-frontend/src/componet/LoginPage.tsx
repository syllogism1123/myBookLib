import React, {FormEvent, useState} from "react";
import {useNavigate} from "react-router-dom";
import {Alert, Button, FormControl, TextField} from "@mui/material";
import 'react-toastify/dist/ReactToastify.css';
import useUser from "../hook/useUser";
import {toast} from "react-toastify";


type Props = {
    onLogin: (username: string, password: string) => Promise<boolean>
}

export const LoginPage = (props: Props) => {

    const [username, setUsername] = useState<string>('');
    const [password, setPassword] = useState<string>('');
    const {error, setError} = useUser();
    const navigate = useNavigate();

    function onSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();
        props.onLogin(username, password).then((s) => {
            if (s) {
                navigate("/search")
                toast.success('You have successfully logged in!', {
                    position: "top-right",
                    autoClose: 5000,
                    hideProgressBar: false,
                    closeOnClick: true,
                    pauseOnHover: true,
                    draggable: true,
                    progress: undefined,
                    theme: "light",
                });
            } else {
                setError(true);
                console.log("invalid")
            }
        }).catch((error) => {
            console.error(error);
        });
    }

    return (
        <div className="login-page-container">
            <FormControl component="form" onSubmit={onSubmit}>
                <TextField
                    name='username'
                    type="text"
                    required
                    label="Username"
                    value={username}
                    placeholder='username'
                    style={{marginBottom: '5px'}}
                    size="small"
                    onChange={(e) => setUsername(e.target.value)}/>
                <TextField
                    name='password'
                    type="password"
                    required
                    label="Password"
                    value={password}
                    placeholder='password'
                    size="small"
                    style={{marginBottom: '5px'}}
                    onChange={(e) => setPassword(e.target.value)}/>
                <Button variant="contained" type="submit" size="small">Login</Button>
            </FormControl>
            {error &&
                <Alert severity="error" className="no-book-found">
                    <h3>Invalid Username or Password!</h3>
                </Alert>
            }
        </div>
    )
}