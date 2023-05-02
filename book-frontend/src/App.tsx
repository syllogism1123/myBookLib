import './App.css';
import {BrowserRouter, Navigate, Route, Routes} from "react-router-dom";
import {LogoutPage} from "./componet/LogoutPage";
import {SignUpPage} from "./componet/SignUpPage";
import {LoginPage} from "./componet/LoginPage";
import useUser from "./hook/useUser";
import Header from "./componet/Header";
import BooKGallery from "./componet/BooKGallery";
import React from "react";
import {BookDetails} from "./componet/BookDetails";

function App() {
    const {login, logout, createUser} = useUser();

    return (
        <div className="App">
          {/*  <PrimarySearchAppBar text={query} onTextChange={onTextChange}/>*/}
            <BrowserRouter>
                <Header/>
                <Routes>
                    <Route path="/login" element={<LoginPage onLogin={login}/>}>
                    </Route>
                    <Route path="/signup" element={<SignUpPage createUser={createUser}/>}>
                    </Route>
                    <Route path="/home" element={<BooKGallery/>}>
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
