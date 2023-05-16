import {
    Button,
    Card,
    CardContent, CardHeader,
    FormControl,
    TextField,
    Typography
} from "@mui/material";
import React, {FormEvent, useEffect, useState} from "react";
import axios from "axios";
import {User} from "../model/UserModel";
import {useNavigate} from "react-router-dom";
import {toast} from "react-toastify";
import useUser from "../hook/useUser";

type Props = {
    user: User | null
}
export const ChangePasswordPage = (props: Props) => {

    const [oldPassword, setOldPassword] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const navigate = useNavigate();
    const {user, setUser} = useUser();
    const baseUrl = "http://localhost:8080";
    const onPasswordChangeSubmit = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        if (props.user) {
            axios.post(baseUrl + `/api/users/changePassword`, {
                    oldPassword: oldPassword,
                    newPassword: newPassword
                }, {withCredentials: true}
            )
                .then(() => {
                    setOldPassword("")
                    setNewPassword("")
                    toast.success('YOUR PASSWORD HAS BEEN SUCCESSFULLY CHANGED', {
                        position: "top-center",
                        autoClose: 2000,
                        hideProgressBar: false,
                        closeOnClick: true,
                        pauseOnHover: true,
                        draggable: true,
                        progress: undefined,
                        theme: "colored",
                    });
                    navigate("/mylibrary")
                })
                .catch(error => {
                    console.log(error);
                    toast.error('YOUR OLDPASSWORD IS INVALID', {
                        position: "top-center",
                        autoClose: 2000,
                        hideProgressBar: false,
                        closeOnClick: true,
                        pauseOnHover: true,
                        draggable: true,
                        progress: undefined,
                        theme: "colored",
                    });
                    setOldPassword("")
                    setNewPassword("")
                });
        }
    }


    useEffect(() => {
        if (props.user) {
            axios.get(baseUrl + `/api/users/${props.user.username}`, {
                withCredentials: true
            })
                .then((response) => {
                    setUser(response.data)
                    localStorage.setItem('token', JSON.stringify(response.data));
                })
                .catch((error) => {
                    console.error(error)
                })
        }
//eslint-disable-next-line
    }, [user])


    const handleCancel = (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
        console.log(props.user)
        setOldPassword("")
        setNewPassword("")
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
                    <FormControl component="form" onSubmit={onPasswordChangeSubmit}>

                        <TextField
                            id='oldPassword'
                            name='oldpassword'
                            type="text"
                            required
                            label="OldPassword"
                            value={oldPassword}
                            style={{marginBottom: '20px'}}
                            size="small"
                            onChange={(e) => setOldPassword(e.target.value)}/>
                        <TextField
                            id='newPassword'
                            name='newpassword'
                            type="text"
                            required
                            label="NewPassword"
                            value={newPassword}
                            size="small"
                            style={{marginBottom: '20px'}}
                            onChange={(e) => setNewPassword(e.target.value)}/>

                        <div className="button-container">
                            <Button id="cancel-button" variant="contained" type="button" size="small"
                                    onClick={handleCancel}>
                                Cancel
                            </Button>
                            <Button id="save-button" variant="contained" type="submit" size="small">
                                Save
                            </Button>
                        </div>
                    </FormControl>
                </CardContent>
            </Card>
        </div>
    );
}