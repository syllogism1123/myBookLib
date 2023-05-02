import {Button, Card, CardContent} from "@mui/material";
import {Book} from "../model/Book";
import {useNavigate} from "react-router-dom";
import {useBook} from "../hook/useBook";

type BookCardProps = {
    book: Book,
}
export default function UserBookCard(props: BookCardProps) {
    const navi = useNavigate();
    const {deleteBook} = useBook();
    const toHome = () => {
        navi("/home/")
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
            <Button variant="text" type="submit" size="small" onClick={toHome}>
                Home
            </Button>
            <Button variant="text" type="submit" size="small" onClick={removeFromLib}>
                Remove
            </Button>
        </Card>
    )
}