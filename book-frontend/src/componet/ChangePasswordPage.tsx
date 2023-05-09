import {
    Button,
    Card,
    CardContent, CardHeader,
    FormControl,
    IconButton,
    InputAdornment,
    OutlinedInput,
    Typography
} from "@mui/material";
import React, {ChangeEvent, FormEvent, useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import axios from "axios";
import {User} from "../model/UserModel";
import {Visibility, VisibilityOff} from "@mui/icons-material";
import useUser from "../hook/useUser";

type Props = {
    user: User | null
}
export const ChangePasswordPage = (props: Props) => {
    const initialState: User = {id: "", username: "", password: "", firstname: "", lastname: "", email: ""};
    const navigate = useNavigate();
    const [user, setUser] = useState<User>(initialState);
    const {updateUser} = useUser();
    const [initialUser, setInitialUser] = useState<User>(initialState);

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

    const [showPassword, setShowPassword] = React.useState(false);

    const handleClickShowPassword = () => setShowPassword((show) => !show);

    const handleMouseDownPassword = (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
    };


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
                            Change Your Password
                        </Typography>
                    }
                />
                <CardContent>
                    <FormControl component="form" onSubmit={onSubmit}>
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
                            style={{marginBottom: '10px', backgroundColor: "white"}}
                            inputProps={{style: {textAlign: 'center'}}}
                            size="small"
                            disabled
                        />

                        <div className="button-container">
                            <Button variant="contained" type="submit" size="small" onClick={handleCancel}>
                                Cancel
                            </Button>
                            <Button variant="contained" type="submit" size="small" onClick={handleCancel}>
                                Save Password
                            </Button>
                        </div>
                    </FormControl>
                </CardContent>
            </Card>
        </div>
    );
}