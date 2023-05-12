import {Card, CardContent} from "@mui/material";
import {Book} from "../model/Book";
import {useNavigate} from "react-router-dom";
import Stack from "@mui/material/Stack";
import Rating from "@mui/material/Rating";
import * as React from "react";

type BookCardProps = {
    book: Book,
}
export default function BookCard(props: BookCardProps) {
    const navi = useNavigate();
    const toDetail = () => {
        navi("/home/" + props.book.googleBookId)
    }


    return (
        <Card className='bookCard' variant="elevation" style={{
            backgroundColor: 'cyan',
            marginTop: "20px"
        }}>
            <CardContent>
                <img id='book-img' src={props.book.imageUrl} alt={props.book.title} onClick={toDetail}
                     style={{cursor: 'pointer'}}/>
            </CardContent>

            <Stack spacing={1} className='rating'>
                <Rating name="half-rating-read" defaultValue={props.book.averageRating} precision={0.5} readOnly/>
            </Stack>
        </Card>
    )
}