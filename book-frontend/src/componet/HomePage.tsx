import React, {ChangeEvent, useEffect, useState} from 'react';
import {Container, Typography, Box, Grid, Card, CardMedia, CardContent, TextField, Button} from '@mui/material';
import {Book} from "../model/Book";
import axios from 'axios';


export const HomePage = () => {
    const [latestBooks, setLatestBooks] = useState<Book[]>([]);
    const [query, setQuery] = useState<string>('');
    const [error, setError] = useState<string | null>(null);
    const baseUrl = "https://my-booklibrary.fly.dev";

    useEffect(() => {
        const data = localStorage.getItem("books")
        if (data) {
            const storedBooks = JSON.parse(data);
            if (storedBooks) {
                setLatestBooks(storedBooks);
            }
        }
    }, []);

    const onTextChange = (event: ChangeEvent<HTMLInputElement>) => {
        setQuery(event.target.value);
    }

    const onSearch = async () => {
        try {
            const response = await axios.get(baseUrl + `/api/books/search?query=${query}`, {
                withCredentials: true
            });
            setLatestBooks(response.data);
            setQuery("");
            localStorage.setItem("books", JSON.stringify(response.data));
            setError(null); // clear error if the search was successful
        } catch (error) {
            console.error(error);
            setError('An error occurred while searching for books. Please try again.');
        }
    }

    return (
        <Container>
            <Typography variant="h2" gutterBottom>Welcome to Our Book Library</Typography>

            <Box sx={{display: 'flex', justifyContent: 'center', marginBottom: '30px'}}>
                <TextField label="Search for books" variant="outlined" onChange={onTextChange} value={query}/>
                <Button variant="contained" color="primary" onClick={onSearch}>Search</Button>
            </Box>

            {error && <p>{error}</p>}

            <Typography variant="h4" gutterBottom>Latest Books</Typography>
            <Grid container spacing={3}>
                {latestBooks.map((book) => (
                    <Grid item key={book.id} xs={12} md={6} lg={4}>
                        <Card>
                            <CardMedia
                                component="img"
                                height="140"
                                image={book.imageUrl}
                                alt={book.title}
                            />
                            <CardContent>
                                <Typography variant="h5" component="div">{book.title}</Typography>
                            </CardContent>
                        </Card>
                    </Grid>
                ))}
            </Grid>
        </Container>
    )
}