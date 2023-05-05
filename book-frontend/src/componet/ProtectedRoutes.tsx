import {Navigate, Outlet} from "react-router-dom";


type Props = {
    username: string|undefined
}
export default function ProtectedRoutes(props: Props) {
    const isAuthenticated = props.username != undefined && props.username != 'anonymousUser'
    return (
        isAuthenticated ? <Outlet/> : <Navigate to={"/login"}/>
    )
}
