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
import {useSelector} from "react-redux";
import {RootState} from "./store/AuthSlice";


function App() {
    const {login, logout, createUser} = useUser();
    const isAuthenticated = useSelector((state: RootState) => state.auth.isLoggedIn);
    console.log(isAuthenticated);


    return (
        <div className="App">
            <ToastContainer/>
            <BrowserRouter>
                <ResponsiveAppBar onLogout={logout}/>
                <Routes>
                    <Route path="/login" element={<LoginPage onLogin={login}/>}>
                    </Route>
                    <Route path="/signup" element={<SignUpPage createUser={createUser}/>}>
                    </Route>
                    {isAuthenticated && <Route path="/search" element={<SearchBooksPage/>}>
                    </Route>}
                    {isAuthenticated && <Route path="/dashboard/" element={<UserBookGallery/>}>
                    </Route>}
                    {isAuthenticated && <Route path="/" element={<Navigate to="/home"/>}>
                    </Route>}
                    {isAuthenticated && <Route path="/home/:id" element={<BookDetails/>}/>}
                    {isAuthenticated && <Route path="/dashboard/:id" element={<BookDetails/>}/>}
                </Routes>
            </BrowserRouter>
        </div>
    );
}

export default App;
