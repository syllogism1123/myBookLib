import * as React from 'react';
import AppBar from '@mui/material/AppBar';
import Box from '@mui/material/Box';
import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import Typography from '@mui/material/Typography';
import Menu from '@mui/material/Menu';
import MenuIcon from '@mui/icons-material/Menu';
import Container from '@mui/material/Container';
import Avatar from '@mui/material/Avatar';
import Button from '@mui/material/Button';
import Tooltip from '@mui/material/Tooltip';
import MenuItem from '@mui/material/MenuItem';
import AutoStoriesIcon from '@mui/icons-material/AutoStories';
import {useNavigate} from "react-router-dom";
import {toast} from "react-toastify";

const pages = ['Login', 'Home', 'Search'];
const settings = ['Password', 'Account', 'MyLibrary', 'Logout'];

type Props = {
    onLogout: () => Promise<void>
    isLoggedIn: () => boolean
}

function ResponsiveAppBar(props: Props) {
    const [anchorElNav, setAnchorElNav] = React.useState<null | HTMLElement>(null);
    const [anchorElUser, setAnchorElUser] = React.useState<null | HTMLElement>(null);

    const navi = useNavigate();
    const handleOpenNavMenu = (event: React.MouseEvent<HTMLElement>) => {
        setAnchorElNav(event.currentTarget);
    };
    const handleOpenUserMenu = (event: React.MouseEvent<HTMLElement>) => {
        if (props.isLoggedIn()) {
            setAnchorElUser(event.currentTarget);
        }
    };

    const handleCloseNavMenu = () => {
        setAnchorElNav(null);
    };

    const handleCloseUserMenu = () => {
        setAnchorElUser(null);
    };

    const onClickLogOut = () => {
        if (props.isLoggedIn()) {
            props.onLogout().then(() => {
                navi("/login")
                setAnchorElUser(null);
                toast.info('YOU HAVE SUCCESSFULLY LOGGED OUT', {
                    position: "top-center",
                    autoClose: 1000,
                    hideProgressBar: false,
                    closeOnClick: true,
                    pauseOnHover: true,
                    draggable: true,
                    progress: undefined,
                    theme: "colored",
                });
            }).catch((error) => {
                console.error(error);
            });
        }
    };

    return (
        <AppBar position="static">
            <Container maxWidth="xl">
                <Toolbar disableGutters>
                    <AutoStoriesIcon sx={{display: {xs: 'none', md: 'flex'}, mr: 2, color: 'palegreen'}}/>
                    <Typography
                        variant="h4"
                        noWrap
                        component="a"
                        href="/"

                        sx={{
                            mr: 2,
                            display: {xs: 'none', md: 'flex'},
                            fontFamily: 'monospace',
                            fontWeight: 700,
                            letterSpacing: '.3rem',
                            color: 'cyan',
                            textDecoration: 'none',
                        }}
                    >
                        BOOK LIBRARY
                    </Typography>

                    <Box sx={{flexGrow: 1, display: {xs: 'flex', md: 'none'}}}>
                        <IconButton
                            size="large"
                            aria-label="account of current user"
                            aria-controls="menu-appbar"
                            aria-haspopup="true"
                            onClick={handleOpenNavMenu}
                            color="inherit"
                        >
                            <MenuIcon/>
                        </IconButton>
                        <Menu
                            id="menu-appbar"
                            anchorEl={anchorElNav}
                            anchorOrigin={{
                                vertical: 'bottom',
                                horizontal: 'left',
                            }}
                            keepMounted
                            transformOrigin={{
                                vertical: 'top',
                                horizontal: 'left',
                            }}
                            open={Boolean(anchorElNav)}
                            onClose={handleCloseNavMenu}
                            sx={{
                                display: {xs: 'block', md: 'none'},
                            }}
                        >
                            {pages.map((page) => (
                                <MenuItem id="menu-page" key={page} onClick={() => {
                                    navi(`/${page.toLowerCase()}`);
                                    handleCloseNavMenu();
                                }}>
                                    <Typography textAlign="center">{page}</Typography>
                                </MenuItem>
                            ))}
                        </Menu>
                    </Box>
                    <AutoStoriesIcon sx={{display: {xs: 'flex', md: 'none'}, mr: 1}}/>
                    <Typography
                        variant="h5"
                        noWrap
                        component="a"
                        href=""
                        sx={{
                            mr: 2,
                            display: {xs: 'flex', md: 'none'},
                            flexGrow: 1,
                            fontFamily: 'monospace',
                            fontWeight: 700,
                            letterSpacing: '.3rem',
                            color: 'inherit',
                            textDecoration: 'none',
                        }}
                    >
                        BOOK LIBRARY
                    </Typography>
                    <Box sx={{flexGrow: 1, display: {xs: 'none', md: 'flex'}}}>
                        {pages.map((page) => (
                            <Button
                                key={page}
                                onClick={() => navi(`/${page.toLowerCase()}`)}
                                sx={{my: 2, color: 'cyan', display: 'block'}}
                            >
                                {page}
                            </Button>
                        ))}
                    </Box>

                    <Box sx={{flexGrow: 0}}>
                        <Tooltip title="Open settings">
                            <IconButton id="settings" onClick={handleOpenUserMenu} sx={{p: 0}}>
                                {props.isLoggedIn() ? <Avatar alt="My Avatar" src="/static/images/avatar/2.png"/>
                                    : <Avatar alt="My Avatar" src="/static/images/avatar/xxxx.png"/>}
                            </IconButton>
                        </Tooltip>

                        <Menu
                            sx={{mt: '45px'}}
                            id="menu-appbar"
                            anchorEl={anchorElUser}
                            anchorOrigin={{
                                vertical: 'top',
                                horizontal: 'right',
                            }}
                            keepMounted
                            transformOrigin={{
                                vertical: 'top',
                                horizontal: 'right',
                            }}
                            open={Boolean(anchorElUser)}
                            onClose={handleCloseUserMenu}
                        >

                            {settings.map((setting) => {
                                if (setting === 'Logout') {
                                    return (
                                        <MenuItem id="logout-btn" key={setting} onClick={onClickLogOut}>
                                            <Typography textAlign="center">{setting}</Typography>
                                        </MenuItem>
                                    );
                                }
                                return (
                                    <MenuItem key={setting} onClick={() => {
                                        navi(`lib/${setting.toLowerCase()}`);
                                        setAnchorElUser(null);
                                    }
                                    }>
                                        <Typography textAlign="center">{setting}</Typography>
                                    </MenuItem>
                                );
                            })}
                        </Menu>
                    </Box>
                </Toolbar>
            </Container>
        </AppBar>
    )
        ;
}

export default ResponsiveAppBar;