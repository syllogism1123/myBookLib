import {Button, FormControl, IconButton, InputAdornment, OutlinedInput, TextField, Typography} from "@mui/material";
import React, {ChangeEvent, FormEvent, useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import axios from "axios";
import {User} from "../model/UserModel";
import {Visibility, VisibilityOff} from "@mui/icons-material";
import useUser from "../hook/useUser";

type Props = {
    user: User | null
}
export const AccountPage = (props: Props) => {
    const initialState: User = {id: "", username: "", password: "", firstname: "", lastname: ""};
    const navigate = useNavigate();
    const [user, setUser] = useState<User>(initialState);
    const {updateUser} = useUser();

    useEffect(() => {
        if (props.user) {
            axios.get(`http://localhost:8080/api/users/${props.user.username}`, {
                withCredentials: true
            })
                .then((response) => {
                    setUser(response.data)
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
        }
    }

    function onSubmit(event: FormEvent<HTMLFormElement>) {
        if (user?.password) {
            event.preventDefault();
            updateUser(user).then(() => navigate("/books")).catch((r) => console.error(r))

        }
    }

    useEffect(() => {
        const savedUser = localStorage.getItem('token');
        if (savedUser) {
            setUser(JSON.parse(savedUser));
        }
    }, []);

    const [showPassword, setShowPassword] = React.useState(false);

    const handleClickShowPassword = () => setShowPassword((show) => !show);

    const handleMouseDownPassword = (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
    };


    return (
        <div className="login-page-container">
            <FormControl component="form" onSubmit={onSubmit}>
                <Typography>UserName</Typography>
                <TextField
                    name="username"
                    type="text"
                    required
                    value={user?.username}
                    size="small"
                    style={{marginBottom: '5px'}}
                    inputProps={{style: {textAlign: 'center'}}}
                    disabled
                />
                <Typography>New Password</Typography>
                <OutlinedInput
                    id="outlined-adornment-password"
                    name="password"
                    required
                    type={showPassword ? 'text' : 'password'}
                    value={user?.password}
                    onChange={onChange}
                    endAdornment={
                        <InputAdornment position="end">
                            <IconButton
                                aria-label="toggle password visibility"
                                onClick={handleClickShowPassword}
                                onMouseDown={handleMouseDownPassword}
                                edge="end"
                            >
                                {showPassword ? <VisibilityOff/> : <Visibility/>}
                            </IconButton>
                        </InputAdornment>
                    }
                    style={{marginBottom: '5px'}}
                    inputProps={{style: {textAlign: 'center'}}}
                    size="small"
                />
                <Typography>FirstName</Typography>
                <TextField
                    name="firstname"
                    type="text"
                    required
                    value={user?.firstname}
                    size="small"
                    style={{marginBottom: '5px'}}
                    inputProps={{style: {textAlign: 'center'}}}
                    disabled
                />
                <Typography>LastName</Typography>
                <TextField
                    name="lastname"
                    type="text"
                    required
                    value={user?.lastname}
                    size="small"
                    style={{marginBottom: '5px'}}
                    inputProps={{style: {textAlign: 'center'}}}
                    disabled
                />
                <Button variant="contained" type="submit" size="small">
                    Save
                </Button>
            </FormControl>
        </div>
    );
}