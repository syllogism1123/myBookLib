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
                <img id='book-img-details' src={book?.imageUrl} alt={book?.title}/>
                <div className="details">
                    <Typography>Title: {book?.title}</Typography>
                    <Typography>Authors: {book?.authors.join(', ')}</Typography>
                    <Typography>Publisher: {book?.publisher}</Typography>
                    <Typography>PublisherDate: {book?.publishedDate}</Typography>
                    <Typography>Description: {book?.description.replace(/<[^>]*>/g, "")}</Typography>
                    <Typography>AverageRating: {book?.averageRating.toPrecision(2)}</Typography>
                </div>
            </Card>
        </div>
    );
}