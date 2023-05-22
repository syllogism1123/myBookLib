import './App.css';
import {BrowserRouter, Navigate, Route, Routes} from "react-router-dom";
import {SignUpPage} from "./componet/SignUpPage";
import {LoginPage} from "./componet/LoginPage";
import useUser from "./hook/useUser";
import SearchBooksPage from "./componet/SearchBooksPage";
import React, {useEffect} from "react";
import {BookDetails} from "./componet/BookDetails";
import UserBookGallery from "./componet/UserBookGallery";
import {ToastContainer} from "react-toastify";
import ResponsiveAppBar from "./componet/ResponsiveAppBar";
import {AccountPage} from "./componet/AccountPage";
import {ChangePasswordPage} from "./componet/ChangePasswordPage";
import 'react-toastify/dist/ReactToastify.css';
import ProtectedRoutes from "./componet/ProtectedRoutes";

function App() {
    const {
        login,
        logout,
        createUser,
        username,
        loadUser, user,
        isTokenExpired
    } = useUser();
    const getToken = () => localStorage.getItem('token');

    function isLoggedIn() {
        const token = getToken();
        return token !== null && !isTokenExpired(token);
    }

    useEffect(() => {
        if (isLoggedIn() && username) {
            loadUser(username).catch(
                (e) => console.error(e)
            );
        }
        //eslint-disable-next-line
    }, [isLoggedIn, username]);


    return (
        <div className="App">
            <ToastContainer/>
            <BrowserRouter>
                <ResponsiveAppBar onLogout={logout} isLoggedIn={isLoggedIn}/>
                <Routes>
                    {!isLoggedIn() &&
                        <Route path="/login" element={<LoginPage onLogin={login}/>}>
                        </Route>}
                    {!isLoggedIn() &&
                        <Route path="/signup" element={<SignUpPage createUser={createUser}/>}>
                        </Route>}
                    <Route element={<Navigate to='/home'/>}/>

                    <Route element={<ProtectedRoutes isLoggedIn={isLoggedIn}/>}>
                        <Route path="/search" element={<SearchBooksPage/>}>
                        </Route>
                        <Route path="/account" element={<AccountPage user={user}/>}>
                        </Route>
                        <Route path="/password" element={<ChangePasswordPage user={user}/>}>
                        </Route>
                        <Route path="/mylibrary/" element={<UserBookGallery/>}>
                        </Route>
                        <Route path="/home/:id" element={<BookDetails/>}/>
                        <Route path="/mylibrary/:id" element={<BookDetails/>}/>
                    </Route>
                </Routes>
            </BrowserRouter>
        </div>
    );
}

export default App;
