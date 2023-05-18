import React, {FormEvent, useState} from "react";
import {useNavigate} from "react-router-dom";
import {Alert, Button, Card, CardContent, CardHeader, FormControl, TextField, Typography} from "@mui/material";
import 'react-toastify/dist/ReactToastify.css';
import useUser from "../hook/useUser";
import {toast} from "react-toastify";
import SendIcon from '@mui/icons-material/Send';
import Stack from "@mui/material/Stack";

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
                navigate("/search");
                toast.success('YOU HAVE SUCCESSFULLY LOGGED IN', {
                    position: "top-center",
                    autoClose: 1000,
                    hideProgressBar: false,
                    closeOnClick: true,
                    pauseOnHover: true,
                    draggable: true,
                    progress: undefined,
                    theme: "colored",
                });
            } else {
                setError(true);
            }
        }).catch((error) => {
            console.error(error);
        });
    }

    return (
        <div className="login-page-container">
            <Card variant="outlined" className="card-container">
                <CardHeader
                    title={
                        <Typography variant="h5" component="div">
                            Sign In Your Account
                        </Typography>
                    }
                />
                <CardContent>
                    <FormControl component="form" onSubmit={onSubmit}>
                        <TextField
                            id="inputUsername"
                            name='username'
                            type="text"
                            required
                            label="Username"
                            value={username}
                            placeholder='username'
                            style={{marginBottom: '20px'}}
                            size="small"
                            onChange={(e) => setUsername(e.target.value)}/>
                        <TextField
                            id="inputPassword"
                            name='password'
                            type="password"
                            required
                            label="Password"
                            value={password}
                            placeholder='password'
                            size="small"
                            style={{marginBottom: '20px'}}
                            onChange={(e) => setPassword(e.target.value)}/>
                        <Button id="submit-button" variant="contained" type="submit" size="small">Login</Button>
                        <Stack direction="row" spacing={2}>
                            <Typography style={{marginTop: '20px'}}>
                                create a new account
                            </Typography>
                            <Button variant="contained" size="small" endIcon={<SendIcon/>} onClick={() => {
                                navigate("/signup")
                            }}>
                                Signup
                            </Button>
                        </Stack>
                        {error &&
                            <Alert id="error-msg" severity="error" className="no-book-found">
                                <h3>Invalid Username or Password!</h3>
                            </Alert>
                        }
                    </FormControl>
                </CardContent>
            </Card>
        </div>
    )
}