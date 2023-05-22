import BookCard from "./BookCard";
import React, {ChangeEvent, useEffect} from "react";
import {useBook} from "../hook/useBook";
import axios from "axios";
import {Book} from "../model/Book";
import {Box, TextField} from "@mui/material";
import SearchIcon from "@mui/icons-material/Search";
import useUser from "../hook/useUser";

export default function SearchBooksPage() {
    const {books, setBooks, query, setQuery} = useBook();
    const {getTokenString} = useUser();
    const baseUrl = "https://my-booklibrary.fly.dev";
    const token = getTokenString();
    const onTextChange = (event: ChangeEvent<HTMLInputElement>) => {
        setQuery(event.target.value);
    }

    useEffect(() => {
        const data = localStorage.getItem("books")
        if (data) {
            const storedBooks = JSON.parse(data);
            if (storedBooks) {
                setBooks(storedBooks);
            }
        }//eslint-disable-next-line
    }, []);

    const onKeyPress = async (event: React.KeyboardEvent<HTMLInputElement>) => {
        if (event.key === 'Enter') {
            await axios.get(baseUrl + `/api/books/search?query=${query}`, {
                withCredentials: true,
                headers: {
                    Authorization: `Bearer ${token}`,
                }

            }).then((response) => {
                setBooks(response.data)
                setQuery("")
                localStorage.setItem("books", JSON.stringify(response.data));
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