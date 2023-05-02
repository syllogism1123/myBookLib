import {Alert, Button, Card, FormControl, TextField} from "@mui/material";
import React, {ChangeEvent, FormEvent, useState} from "react";
import {useNavigate} from "react-router-dom";
import {UserModel} from "../model/UserModel";
import useUser from "../hook/useUser";

type createUserProps = {
    createUser: (user: UserModel) => Promise<boolean>;
}

export const SignUpPage = (props: createUserProps) => {

    const initial: UserModel = {
        username: "", password: "", firstname: "", lastname: ""
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
                    navigate('/books');
                } else {
                    setError(true);
                    console.log("invalid")
                }
            })
        }
    }

    return (
        <div className="form">
            <Card variant="outlined" style={{backgroundColor: 'cyan'}} className="card">
                <FormControl component="form" onSubmit={onSubmit}>
                    <TextField
                        name="username"
                        type="text"
                        required
                        label="Username"
                        value={user.username}
                        onChange={onChange}
                        placeholder="UserName"
                        style={{marginBottom: '10px'}}
                    />
                    <TextField
                        name="password"
                        type="password"
                        required
                        label="Password"
                        value={user.password}
                        onChange={onChange}
                        placeholder="Password"
                        style={{marginBottom: '10px'}}
                    />
                    <TextField
                        name="firstname"
                        type="text"
                        required
                        label="Firstname"
                        value={user.firstname}
                        onChange={onChange}
                        placeholder="FirstName"
                        style={{marginBottom: '10px'}}
                    />
                    <TextField
                        name="lastname"
                        type="text"
                        required
                        label="Lastname"
                        value={user.lastname}
                        onChange={onChange}
                        placeholder="LastName"
                        style={{marginBottom: '10px'}}
                    />
                    <Button variant="contained" type="submit" size="small">
                        Sign Up
                    </Button>
                </FormControl>
                {error &&
                    <Alert severity="error" className="no-book-found">
                        <h3>The username already exists!</h3>
                    </Alert>
                }
            </Card>
        </div>
    );


}