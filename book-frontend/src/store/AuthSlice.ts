import {createSlice} from "@reduxjs/toolkit";

interface AuthState {
    isLoggedIn: boolean;
}

export interface RootState {
    auth: AuthState;
}

const initialState: AuthState = {
    isLoggedIn: false
};

const AuthSlice = createSlice({
    name: 'auth',
    initialState: initialState,
    reducers: {
        login(state: AuthState) {
            state.isLoggedIn = true;
        },
        logout(state: AuthState) {
            state.isLoggedIn = false;
        }
    }
})

export const authAction = AuthSlice.actions;

export default AuthSlice;