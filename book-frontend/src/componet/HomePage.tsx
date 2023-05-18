import React, {useState} from 'react';
import {Container, Typography, Box, Grid, Card, CardMedia, CardContent, TextField, Button} from '@mui/material';
import {Book} from "../model/Book";

export const HomePage = () => {

    // Replace these with actual data
    const [latestBooks, setLatestBooks] = useState<Book[]>([]);
    const [popularBooks, setPopularBooks] = useState<Book[]>([]);


    return (
        <Container>
            <Typography variant="h2" gutterBottom>Welcome to Our Book Library</Typography>

            <Box sx={{display: 'flex', justifyContent: 'center', marginBottom: '30px'}}>
                <TextField label="Search for books" variant="outlined"/>
                <Button variant="contained" color="primary">Search</Button>
            </Box>

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

            <Typography variant="h4" gutterBottom>Popular Books</Typography>
            <Grid container spacing={3}>
                {popularBooks.map((book) => (
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
