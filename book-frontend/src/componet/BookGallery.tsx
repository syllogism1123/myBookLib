import BookCard from "./BookCard";
import React, {ChangeEvent} from "react";
import {useBook} from "../hook/useBook";
import axios from "axios";
import {Book} from "../model/Book";


export default function BookGallery() {
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
            <input type="text" value={query} placeholder="Seach..." onChange={onTextChange} onKeyDown={onKeyPress}/>
            <div className="book-gallery">
                {
                    booksWithView.map((book) => {
                        return <BookCard key={book.id} book={book}/>
                    })
                }
            </div>
        </div>)
}