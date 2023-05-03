import {Card, Typography} from "@mui/material";
import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import axios from "axios";
import {BookModel} from "../model/Book";

export const BookDetails = () => {
    const [book, setBook] = useState<BookModel>()
    const {id} = useParams<{ id: string }>();

    const loadBookById = (id: string) => {
        axios.get(`http://localhost:8080/api/books/search/` + id, {
            withCredentials: true
        })
            .then((response) => {
                setBook(response.data)
            })
            .catch((error) => {
                console.error(error)
            })
    }
    useEffect(() => {
        if (id) {
            loadBookById(id)
        }
    }, [])

    return (
        <div>
            <Card className='book-details' variant="elevation" style={{backgroundColor: 'cyan', marginTop: "20px"}}>
                <Typography className="p-font">Title: {book?.title}</Typography>
                <Typography className="p-font">Authors: {book?.authors}</Typography>
                <Typography className="p-font">Publisher: {book?.publisher}</Typography>
                <Typography
                    className="p-font">PublisherDate: {book?.publishedDate}</Typography>
                <img src={book?.imageUrl} alt={book?.title}/>
                <Typography className="p-font">Description: {book?.description.replace(/<[^>]*>/g, "")}</Typography>
                <Typography className="p-font">AverageRating: {book?.averageRating}</Typography>
            </Card>
        </div>
    );
}