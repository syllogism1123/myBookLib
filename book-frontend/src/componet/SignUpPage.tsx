import {Alert, Button, Card, CardContent, CardHeader, FormControl, TextField, Typography} from "@mui/material";
import React, {ChangeEvent, FormEvent, useState} from "react";
import {useNavigate} from "react-router-dom";
import {UserModel} from "../model/UserModel";
import useUser from "../hook/useUser";
import {toast} from "react-toastify";

type createUserProps = {
    createUser: (user: UserModel) => Promise<boolean>;
}

export const SignUpPage = (props: createUserProps) => {

    const initial: UserModel = {
        username: "", password: "", firstname: "", lastname: "", email: ""
    }
    const [user, setUser] = useState<UserModel>(initial);
    const {error, setError} = useUser();
    const navigate = useNavigate();

    function onChange(event: ChangeEvent<HTMLInputElement>) {
        const targetName: string = event.target.name;
        const value: string = event.target.value;
        setUser({
            ...user,
            [targetName]: value
        })
    }


    function onSubmit(event: FormEvent<HTMLFormElement>) {
        if (user.username && user.password && user.firstname && user.lastname) {
            event.preventDefault();
            props.createUser(user).then((s) => {
                if (s) {
                    setUser(initial);
                    navigate('/search');
                    toast.success('YOU HAVE SUCCESSFULLY SIGNED UP', {
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
                    console.log("invalid")
                }
            }).catch((error) => {
                console.error(error);
            });
        }
    }

    return (
        <div className="login-page-container">
            <Card variant="outlined" className="card-container">
                <CardHeader
                    title={
                        <Typography variant="h5" component="div">
                            Personal Information
                        </Typography>
                    }
                />
                <CardContent>
                    <FormControl component="form" onSubmit={onSubmit}>
                        <TextField
                            id="inputUsername"
                            name="username"
                            type="text"
                            required
                            label="Username"
                            value={user.username}
                            onChange={onChange}
                            placeholder="UserName"
                            size="small"
                            style={{marginBottom: '20px'}}
                        />
                        <TextField
                            id="inputPassword"
                            name="password"
                            type="password"
                            required
                            label="Password"
                            value={user.password}
                            onChange={onChange}
                            placeholder="Password"
                            size="small"
                            style={{marginBottom: '20px'}}
                        />
                        <TextField
                            id="inputFirstName"
                            name="firstname"
                            type="text"
                            required
                            label="Firstname"
                            value={user.firstname}
                            onChange={onChange}
                            placeholder="FirstName"
                            size="small"
                            style={{marginBottom: '20px'}}
                        />
                        <TextField
                            id="inputLastName"
                            name="lastname"
                            type="text"
                            required
                            label="Lastname"
                            value={user.lastname}
                            onChange={onChange}
                            placeholder="LastName"
                            size="small"
                            style={{marginBottom: '20px'}}
                        />
                        <TextField
                            id="inputEmail"
                            name="email"
                            type="email"
                            required
                            label="Email"
                            value={user.email}
                            onChange={onChange}
                            placeholder="email@example.com"
                            size="small"
                            style={{marginBottom: '20px'}}
                        />
                        <Button id="submit-button" variant="contained" type="submit" size="small">
                            Sign Up
                        </Button>
                        {error &&
                            <Alert id="error-msg" severity="error" className="no-book-found">
                                <h3>The username already exists!</h3>
                            </Alert>
                        }
                    </FormControl>
                </CardContent>
            </Card>
        </div>
    );


}