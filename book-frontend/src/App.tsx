import './App.css';
import {BrowserRouter, Route, Routes} from "react-router-dom";
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
import {HomePage} from "./componet/HomePage";

function App() {
    const {login, logout, createUser, username, loadUser, user, setUser} = useUser();
    const data = localStorage.getItem('token')
    const expiration = localStorage.getItem('expiration')

    function isLoggedIn() {
        return data !== null && expiration != null;
    }

    useEffect(() => {
        if (expiration && Date.now() > Number(expiration)) {
            localStorage.clear();
            window.location.reload();
        } else {

            if (data) {
                setUser(JSON.parse(data));
            }
        }//eslint-disable-next-line
    }, [data, expiration]);


    useEffect(() => {
        if (username) {
            loadUser(username).catch(
                (e) => console.error(e)
            );
        }//eslint-disable-next-line
    }, [username]);


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
                    <Route path="/" element={<HomePage/>}>
                    </Route>
                    {isLoggedIn() && <Route path="/search" element={<SearchBooksPage/>}>
                    </Route>}
                    {isLoggedIn() && <Route path="/account" element={<AccountPage user={user}/>}>
                    </Route>}
                    {isLoggedIn() && <Route path="/password" element={<ChangePasswordPage user={user}/>}>
                    </Route>}
                    {isLoggedIn() && <Route path="/mylibrary/" element={<UserBookGallery/>}>
                    </Route>}
                    {isLoggedIn() && <Route path="/home/:id" element={<BookDetails/>}/>}
                    {isLoggedIn() && <Route path="/mylibrary/:id" element={<BookDetails/>}/>}
                </Routes>
            </BrowserRouter>
        </div>
    );
}

export default App;
