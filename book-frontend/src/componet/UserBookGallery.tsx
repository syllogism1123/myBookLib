import {useBook} from "../hook/useBook";
import React, {useEffect} from "react";
import UserBookCard from "./UserBookCard";


export default function UserBookGallery() {
    const {books, loadAllBooks} = useBook();
    useEffect(() => {
        loadAllBooks().catch(
            (e) => console.error(e)
        )
    }, [books]);

    return (
        <div>
            <div className="book-gallery">
                {
                    books.map((book) => {
                        return <UserBookCard key={book.id} book={book}/>
                    })
                }
            </div>
        </div>)
}