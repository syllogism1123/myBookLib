import './App.css';
import {BrowserRouter, Navigate, Route, Routes} from "react-router-dom";
import {SignUpPage} from "./componet/SignUpPage";
import {LoginPage} from "./componet/LoginPage";
import useUser from "./hook/useUser";
import SearchBooksPage from "./componet/SearchBooksPage";
import React from "react";
import {BookDetails} from "./componet/BookDetails";
import UserBookGallery from "./componet/UserBookGallery";
import {ToastContainer} from "react-toastify";
import ResponsiveAppBar from "./componet/ResponsiveAppBar";
import {AccountPage} from "./componet/AccountPage";

function App() {
    const {login, logout, createUser,username} = useUser();

    function isLoggedIn() {
        return localStorage.getItem('token') !== "";
    }

    console.log(isLoggedIn())
    console.log(username)


    return (
        <div className="App">
            <ToastContainer/>
            <BrowserRouter>
                <ResponsiveAppBar onLogout={logout} isLoggedIn={isLoggedIn}/>
                <Routes>
                    <Route path="/login" element={<LoginPage onLogin={login}/>}>
                    </Route>
                    <Route path="/signup" element={<SignUpPage createUser={createUser}/>}>
                    </Route>
                    {isLoggedIn() && <Route path="/search" element={<SearchBooksPage/>}>
                    </Route>}
                    {isLoggedIn() && <Route path="/account" element={<AccountPage username={username}/>}>
                    </Route>}
                    {isLoggedIn() && <Route path="/mylibrary/" element={<UserBookGallery/>}>
                    </Route>}
                    {isLoggedIn() && <Route path="/" element={<Navigate to="/home"/>}>
                    </Route>}
                    {isLoggedIn() && <Route path="/home/:id" element={<BookDetails/>}/>}
                    {isLoggedIn() && <Route path="/mylibrary/:id" element={<BookDetails/>}/>}
                </Routes>
            </BrowserRouter>
        </div>
    );
}

export default App;
