import {Button, Card, Typography} from "@mui/material";
import React, {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import axios from "axios";
import {Book} from "../model/Book";
import {useBook} from "../hook/useBook";
import useUser from "../hook/useUser";

export const BookDetails = () => {
    const [book, setBook] = useState<Book>()
    const {id} = useParams<{ id: string }>();
    const {addBook} = useBook();
    const navi = useNavigate();
    const {getTokenString} = useUser();
    const baseUrl = "https://my-booklibrary.fly.dev";
    const token = getTokenString();
    const loadBookById = (id: string) => {
        axios.get(baseUrl + `/api/books/search/` + id, {
            withCredentials: true,
            headers: {
                Authorization: `Bearer ${token}`,
            }
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
        }//eslint-disable-next-line
    }, [id])
    const addToLib = () => {
        if (book) {
            addBook(book).then(() => {
                    navi("../lib/mylibrary")
                }
            ).catch((r) => console.error(r));
        }
    }


    return (
        <div style={{fontSize: '18px'}} className="book-details-wrapper">
            <Card className='book-details' variant="elevation" style={{backgroundColor: 'cyan', marginTop: "20px"}}>
                <img id='book-img-details' src={book?.imageUrl} alt={book?.title}/>
                <div className="details" style={{fontFamily: 'Arial', color: 'black', lineHeight: '1.6'}}>
                    <Typography style={{fontSize: '24px', fontWeight: 'bold'}}>{book?.title}</Typography>
                    <Typography>Authors: {book?.authors.join(', ')}</Typography>
                    <Typography>Publisher: {book?.publisher}</Typography>
                    <Typography>PublisherDate: {book?.publishedDate}</Typography>
                    <Typography>Description: {book?.description.replace(/<[^>]*>/g, "")}</Typography>
                    <Typography>AverageRating: {book?.averageRating.toPrecision(2)}</Typography>
                </div>
                <div className='card-button'>
                    <Button id='add-button' variant="contained" color="secondary" type="submit" size="small"
                            onClick={addToLib}>
                        Add to Lib
                    </Button>
                </div>
            </Card>
        </div>
    );
}