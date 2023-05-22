import {Navigate, Outlet} from "react-router-dom";
import React from "react";

type Props = {
    isLoggedIn: () => boolean
}
export default function ProtectedRoutes(props: Props) {

    return (
        props.isLoggedIn() ? <Outlet/> : <Navigate to={"/login"}/>
    )
}