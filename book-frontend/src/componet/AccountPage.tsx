import {Button, FormControl, IconButton, InputAdornment, OutlinedInput, TextField} from "@mui/material";
import React, {ChangeEvent, FormEvent, useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import axios from "axios";
import {User} from "../model/UserModel";
import {Visibility, VisibilityOff} from "@mui/icons-material";
import useUser from "../hook/useUser";

type Props = {
    username: string | undefined
}
export const AccountPage = (props: Props) => {

    const navigate = useNavigate();
    const [user, setUser] = useState<User>();
    const {updateUser} = useUser();

    useEffect(() => {
        axios.get(`http://localhost:8080/api/users/${props.username}`, {
            withCredentials: true
        })
            .then((response) => {
                setUser(response.data)
                console.log(user)
            })
            .catch((error) => {
                console.error(error)
            })
    }, [props.username])

    function onChange(event: ChangeEvent<HTMLInputElement>) {
        const targetName: string = event.target.name;
        const value: string = event.target.value;
        if (user?.id) {
            setUser({
                ...user, id: user.id,
                [targetName]: value
            })
        }
    }

    function onSubmit(event: FormEvent<HTMLFormElement>) {
        if (user?.password) {
            event.preventDefault();
            updateUser(user).then(() => navigate("/books")).catch((r) => console.error(r))

        }
    }

    const [showPassword, setShowPassword] = React.useState(false);

    const handleClickShowPassword = () => setShowPassword((show) => !show);

    const handleMouseDownPassword = (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
    };

    return (
        <div className="login-page-container">
            <FormControl component="form" onSubmit={onSubmit}>
                <TextField
                    name="username"
                    type="text"
                    required
                    value={user?.username}
                    size="small"
                    style={{marginBottom: '5px'}}
                    disabled
                />
                <OutlinedInput
                    id="outlined-adornment-password"
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
                    size="small"
                />
                <TextField
                    name="firstname"
                    type="text"
                    required
                    value={user?.firstname}
                    size="small"
                    style={{marginBottom: '5px'}}
                    disabled
                />
                <TextField
                    name="lastname"
                    type="text"
                    required
                    value={user?.lastname}
                    size="small"
                    style={{marginBottom: '5px'}}
                    disabled
                />
                <Button variant="contained" type="submit" size="small">
                    Save
                </Button>
            </FormControl>
        </div>
    );
}