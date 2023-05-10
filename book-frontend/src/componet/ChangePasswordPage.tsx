import {
    Button,
    Card,
    CardContent, CardHeader,
    FormControl,
    TextField,
    Typography
} from "@mui/material";
import React, {FormEvent, useState} from "react";
import axios from "axios";
import {User} from "../model/UserModel";

type Props = {
    user: User | null
}
export const ChangePasswordPage = (props: Props) => {

    const [oldPassword, setOldPassword] = useState('');
    const [newPassword, setNewPassword] = useState('');

    const onPasswordChangeSubmit = (event: FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        if (props.user) {
            axios.post(`http://localhost:8080/api/users/changePassword`, {
                    oldPassword: oldPassword,
                    newPassword: newPassword
                }, {withCredentials: true}
            )
                .then(() => {
                })
                .catch(error => {
                    console.log(error);
                });
        }
    }

    const handleCancel = (event: React.MouseEvent<HTMLButtonElement>) => {
        event.preventDefault();
        console.log(props.user?.username)
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
                            name='oldpassword'
                            type="text"
                            required
                            label="OldPassword"
                            value={oldPassword}
                            style={{marginBottom: '20px'}}
                            size="small"
                            onChange={(e) => setOldPassword(e.target.value)}/>
                        <TextField
                            name='newpassword'
                            type="text"
                            required
                            label="NewPassword"
                            value={newPassword}
                            size="small"
                            style={{marginBottom: '20px'}}
                            onChange={(e) => setNewPassword(e.target.value)}/>

                        <div className="button-container">
                            <Button variant="contained" type="submit" size="small" onClick={handleCancel}>
                                Cancel
                            </Button>
                            <Button variant="contained" type="submit" size="small">
                                Save Password
                            </Button>
                        </div>
                    </FormControl>
                </CardContent>
            </Card>
        </div>
    );
}