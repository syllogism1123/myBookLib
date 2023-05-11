import {
    Button,
    Card,
    CardContent, CardHeader,
    FormControl,
    TextField,
    Typography
} from "@mui/material";
import React, {ChangeEvent, FormEvent, useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import axios from "axios";
import {User} from "../model/UserModel";
import useUser from "../hook/useUser";

type Props = {
    user: User | null
}
export const AccountPage = (props: Props) => {
    const initial: User = {id: "", username: "", password: "", firstname: "", lastname: "", email: ""};
    const navigate = useNavigate();
    const [user, setUser] = useState<User>(initial);
    const {updateUser} = useUser();
    const [initialUser, setInitialUser] = useState<User>(initial);

    useEffect(() => {
        if (props.user) {
            axios.get(`http://localhost:8080/api/users/${props.user.username}`, {
                withCredentials: true
            })
                .then((response) => {
                    setUser(response.data)
                    setInitialUser(response.data);
                    console.log(user)
                })
                .catch((error) => {
                    console.error(error)
                })
        }

    }, [props.user])

    function onChange(event: ChangeEvent<HTMLInputElement>) {
        const targetName: string = event.target.name;
        const value: string = event.target.value;
        if (user?.id) {
            const updatedUser = {
                ...user,
                id: user.id,
                [targetName]: value
            };
            setUser(updatedUser);
            localStorage.setItem('token', JSON.stringify(updatedUser));
        }
    }

    function onSubmit(event: FormEvent<HTMLFormElement>) {
        if (user?.password) {
            event.preventDefault();
            updateUser(user).then(() => navigate("/mylibrary")).catch((r) => console.error(r))

        }
    }

    useEffect(() => {
        const savedUser = localStorage.getItem('token');
        if (savedUser) {
            setUser(JSON.parse(savedUser));
        }
    }, []);


    const handleCancel = (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
        setUser(initialUser);
    };

    return (

        <div className="login-page-container">
            <Card variant="outlined" className="card-container">
                <CardHeader
                    title={
                        <Typography variant="h5" component="div">
                            Basic Information
                        </Typography>
                    }
                />
                <CardContent>
                    <FormControl component="form" onSubmit={onSubmit}>
                        <TextField
                            name="username"
                            label="Username"
                            type="text"
                            value={user?.username}
                            size="small"
                            style={{marginBottom: '20px'}}
                            inputProps={{style: {textAlign: 'center', backgroundColor: "white"}}}
                            disabled
                        />

                        <TextField
                            name="firstname"
                            label="Firstname"
                            type="text"
                            value={user?.firstname}
                            size="small"
                            style={{marginBottom: '20px'}}
                            inputProps={{style: {textAlign: 'center', backgroundColor: "white"}}}
                            onChange={onChange}
                        />

                        <TextField
                            name="lastname"
                            label="Lastname"
                            type="text"
                            value={user?.lastname}
                            size="small"
                            style={{marginBottom: '20px'}}
                            inputProps={{style: {textAlign: 'center', backgroundColor: "white"}}}
                            onChange={onChange}
                        />
                        <TextField
                            name="email"
                            label="Email"
                            type="email"
                            value={user?.email}
                            size="small"
                            style={{marginBottom: '20px'}}
                            inputProps={{style: {textAlign: 'center', backgroundColor: "white"}}}
                            onChange={onChange}
                        />
                        <div className="button-container">
                            <Button variant="contained" type="button" size="small" onClick={handleCancel}>
                                Cancel
                            </Button>
                            <Button variant="contained" type="submit" size="small">
                                Save
                            </Button>
                        </div>
                    </FormControl>
                </CardContent>
            </Card>
        </div>
    );
}