import {Breadcrumbs, Typography} from "@mui/material";
import {Link} from "react-router-dom";
import React from "react";

export default function Header() {
    return (
        <div>
            <Breadcrumbs maxItems={4} aria-label="breadcrumb" className='link'>
                <Link to='/books'>Book Library</Link>
                <Link to='/home'>Home</Link>
                <Link to='/login'>Login</Link>
                <Link to='/logout'>Logout</Link>
                <Link to='/signup'>Signup</Link>
                <Typography color="text.primary"></Typography>
            </Breadcrumbs>
            <Typography variant="h1" style={{color: "brown"}}>
                BOOK LIBRARY
            </Typography>
        </div>
    )
}