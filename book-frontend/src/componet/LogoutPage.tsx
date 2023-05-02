import {FormEvent} from "react";
import {useNavigate} from "react-router-dom";
import {Button, FormControl} from "@mui/material";
import {toast} from "react-toastify";

type Props = {
    onLogout: () => Promise<void>
}
export const LogoutPage = (props: Props) => {

    const navigate = useNavigate();

    function onSubmit(event: FormEvent<HTMLFormElement>) {
        event.preventDefault();
        props.onLogout().then(() => {
            toast.success('You have successfully logged out!', {
                position: "top-center",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "light",
            });
            navigate("/login")

        })
    }

    return (
        <FormControl component="form" onSubmit={onSubmit}>
            <Button variant="contained" type="submit" size="large">Logout</Button>
        </FormControl>
    )

}