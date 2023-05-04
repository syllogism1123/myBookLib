import {Button, Card, CardContent} from "@mui/material";
import {Book} from "../model/Book";
import {useNavigate} from "react-router-dom";
import {useBook} from "../hook/useBook";
import Stack from "@mui/material/Stack";
import Rating from "@mui/material/Rating";
import * as React from "react";

type BookCardProps = {
    book: Book,
}
export default function UserBookCard(props: BookCardProps) {
    const navi = useNavigate();
    const {deleteBook} = useBook();
    const toDetail = () => {
        navi("/books/" + props.book.googleBookId)
    }

    const removeFromLib = () => {
        deleteBook(props.book.id).then(() => {
                navi("/books/")
            }
        ).catch((r) => console.error(r));
    }


    return (
        <Card className='bookCard' variant="elevation" style={{backgroundColor: 'cyan', marginTop: "20px"}}>
            <CardContent>
                <img id='book-img' src={props.book.imageUrl} alt={props.book.title}/>
            </CardContent>
            <Button variant="text" type="submit" size="small" onClick={toDetail}>
                Details
            </Button>
            <Button variant="text" type="submit" size="small" onClick={removeFromLib}>
                Remove
            </Button>
            <Stack spacing={1} className='rating'>
                <Rating name="half-rating-read" defaultValue={props.book.averageRating} precision={0.5}/>
            </Stack>
        </Card>
    )
}