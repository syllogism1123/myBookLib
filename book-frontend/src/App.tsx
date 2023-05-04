import './App.css';
import {BrowserRouter, Navigate, Route, Routes} from "react-router-dom";
import {LogoutPage} from "./componet/LogoutPage";
import {SignUpPage} from "./componet/SignUpPage";
import {LoginPage} from "./componet/LoginPage";
import useUser from "./hook/useUser";
import Header from "./componet/Header";
import BookGallery from "./componet/BookGallery";
import React from "react";
import {BookDetails} from "./componet/BookDetails";
import UserBookGallery from "./componet/UserBookGallery";
import {ToastContainer} from "react-toastify";

function App() {
    const {login, logout, createUser} = useUser();

    return (
        <div className="App">
            <ToastContainer />
            <BrowserRouter>
                <Header/>
                <Routes>
                    <Route path="/login" element={<LoginPage onLogin={login}/>}>
                    </Route>
                    <Route path="/signup" element={<SignUpPage createUser={createUser}/>}>
                    </Route>
                    <Route path="/home" element={<BookGallery/>}>
                    </Route>
                    <Route path="/books" element={<UserBookGallery/>}>
                    </Route>
                    <Route path="/" element={<Navigate to="/home"/>}>
                    </Route>
                    <Route path="/home/:id" element={<BookDetails/>}/>
                    <Route path="/logout" element={<LogoutPage onLogout={logout}/>}>
                    </Route>
                </Routes>
            </BrowserRouter>
        </div>
    );
}

export default App;
