import {configureStore} from "@reduxjs/toolkit";
import AuthSlice from "./AuthSlice";

const store = configureStore({
    reducer: {
        auth: AuthSlice.reducer,
    }
});
export type RootState = ReturnType<typeof store.getState>;
export default store;