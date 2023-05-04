import './App.css';
import {BrowserRouter, Navigate, Route, Routes} from "react-router-dom";
import {LogoutPage} from "./componet/LogoutPage";
import {SignUpPage} from "./componet/SignUpPage";
import {LoginPage} from "./componet/LoginPage";
import useUser from "./hook/useUser";
import SearchBooksPage from "./componet/SearchBooksPage";
import React from "react";
import {BookDetails} from "./componet/BookDetails";
import UserBookGallery from "./componet/UserBookGallery";
import {ToastContainer} from "react-toastify";
import ResponsiveAppBar from "./componet/ResponsiveAppBar";

function App() {
    const {login, logout, createUser} = useUser();


    return (
        <div className="App">
            <ToastContainer/>
            <BrowserRouter>
                {/*   <Header/>*/}
                <ResponsiveAppBar/>
                <Routes>
                    <Route path="/login" element={<LoginPage onLogin={login}/>}>
                    </Route>
                    <Route path="/signup" element={<SignUpPage createUser={createUser}/>}>
                    </Route>

                    {/* <Route element={<ProtectedRoutes user={user}/>}>*/}

                    <Route path="/search" element={<SearchBooksPage/>}>
                    </Route>

                    <Route path="/dashboard/" element={<UserBookGallery/>}>
                    </Route>

                    <Route path="/" element={<Navigate to="/home"/>}>
                    </Route>

                    <Route path="/home/:id" element={<BookDetails/>}/>

                    <Route path="/dashboard/:id" element={<BookDetails/>}/>

                    <Route path="/logout" element={<LogoutPage onLogout={logout}/>}>
                    </Route>
                    {/*     </Route>*/}

                </Routes>
            </BrowserRouter>
        </div>
    );
}

export default App;
