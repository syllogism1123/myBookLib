import {Navigate, Outlet} from "react-router-dom";
import {User} from "../model/UserModel";

type Props = {
    user: User | null
}
export default function ProtectedRoutes(props: Props) {
    const isAuthenticated = props.user != null;

    return (
        isAuthenticated ? <Outlet/> : <Navigate to={"/login"}/>
    )
}
