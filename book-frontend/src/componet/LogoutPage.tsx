import {FormEvent} from "react";
import {useNavigate} from "react-router-dom";
import {Button, FormControl} from "@mui/material";

type Props = {
    onLogout: () => Promise<void>
}
export const LogoutPage = (props: Props) => {

    const navigate = useNavigate();

    function onSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();
        props.onLogout().then(() => {
            navigate("/login")
        }).catch((error) => {
            console.error(error);
        });
    }

    return (
        <FormControl component="form" onSubmit={onSubmit}>
            <Button variant="contained" type="submit" size="large">Logout</Button>
        </FormControl>
    )

}