import {Button, Card, CardContent} from "@mui/material";
import {Book} from "../model/Book";
import React from "react";
import {useNavigate} from "react-router-dom";

type BookCardProps = {
    book: Book,
}
export default function BookCard(props: BookCardProps) {
    const navi = useNavigate();
    const toDetail = () => {
        navi("/home/" + props.book.googleBookId)
    }


    return (
        <Card className='bookCard' variant="elevation" style={{backgroundColor: 'cyan', marginTop: "20px"}}>
            <CardContent>
                <img id='book-img' src={props.book.imageUrl} alt={props.book.title}/>
            </CardContent>
            <Button variant="text" type="submit" size="small" onClick={toDetail}>
                Details
            </Button>
            <Button variant="text" type="submit" size="small">
                Add to Lib
            </Button>
        </Card>
    )
}