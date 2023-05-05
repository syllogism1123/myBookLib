import BookCard from "./BookCard";
import React, {ChangeEvent} from "react";
import {useBook} from "../hook/useBook";
import axios from "axios";
import {Book} from "../model/Book";
import {Box, TextField} from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";

export default function SearchBooksPage() {
    const {books, setBooks, query, setQuery} = useBook();
    const onTextChange = (event: ChangeEvent<HTMLInputElement>) => {
        setQuery(event.target.value);
    }
    const onKeyPress = async (event: React.KeyboardEvent<HTMLInputElement>) => {
        if (event.key === 'Enter') {
            await axios.get(`http://localhost:8080/api/books/search?query=${query}`, {
                withCredentials: true
            }).then((response) => {
                setBooks(response.data)
                setQuery("")
            })
                .catch((error) => {
                    console.error(error);
                })
        }
    }
    const booksWithView: Book[] = books.filter((book) => (book.imageUrl !== null && book.description !== null));
    return (
        <div>
            <div className="search">
                <Box
                    sx={{
                        width: 500,
                        maxWidth: '100%',
                        position: 'relative'
                    }}
                ><TextField fullWidth id="search-book" value={query} placeholder="    Search Books..."
                            onChange={onTextChange} onKeyDown={onKeyPress}/>
                    <SearchIcon className="search-icon"/>
                </Box>

            </div>
            <div className="book-gallery">
                {
                    booksWithView.map((book) => {
                        return <BookCard key={book.googleBookId} book={book}/>
                    })
                }
            </div>
        </div>)
}