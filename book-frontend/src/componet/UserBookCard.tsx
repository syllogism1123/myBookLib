import {Button, Card, CardContent} from "@mui/material";
import {Book} from "../model/Book";
import {useNavigate} from "react-router-dom";
import {useBook} from "../hook/useBook";
import Stack from "@mui/material/Stack";
import Rating from "@mui/material/Rating";
import * as React from "react";
import {ChangeEvent, useState} from "react";
import {toast} from "react-toastify";


type BookCardProps = {
    book: Book,
}
export default function UserBookCard(props: BookCardProps) {
    const navi = useNavigate();
    const {deleteBook, updateBook} = useBook();
    const toDetail = () => {
        navi("/mylibrary/" + props.book.googleBookId)
    }

    const removeFromLib = () => {
        deleteBook(props.book.id).then(() => {
                toast.success(props.book.title + ' was successfully remove from your library', {
                    position: "top-center",
                    autoClose: 2000,
                    hideProgressBar: false,
                    closeOnClick: true,
                    pauseOnHover: true,
                    draggable: true,
                    progress: undefined,
                    theme: "colored",
                });
                navi("/mylibrary")
                window.location.reload()
            }
        ).catch((r) => console.error(r));
    }

    const [newRating, setNewRating] = useState<number>(props.book.averageRating);

    const handleRatingChange = (event: ChangeEvent<{}>, newValue: number | null) => {
        if (newValue !== null) {
            setNewRating(newValue);
        }

    }

    const saveRating = () => {
        updateBook({...props.book, id: props.book.id, averageRating: newRating}).then(() => {
        }).catch((r) => console.error(r));
    }


    return (
        <Card className='bookCard' variant="elevation" style={{backgroundColor: 'cyan', marginTop: "20px"}}>
            <CardContent>
                <img id='book-img' src={props.book.imageUrl} alt={props.book.title} onClick={toDetail}
                     style={{cursor: 'pointer'}}/>
            </CardContent>
            <Button id="save-btn" className='detail-btn' variant="text" type="submit" size="small" onClick={saveRating}>
                Save
            </Button>
            <Button id="remove-btn" className='detail-btn' variant="text" type="submit" size="small"
                    onClick={removeFromLib}>
                Remove
            </Button>
            <Stack spacing={1} className='rating'>
                <Rating name="half-rating-read" value={newRating} precision={0.5}
                        onChange={handleRatingChange}/>
            </Stack>
        </Card>
    )
}