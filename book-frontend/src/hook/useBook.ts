import axios from "axios";
import {useState} from "react";
import {toast} from "react-toastify";
import {Book, BookModel} from "../model/Book";

export const useBook = () => {
    const [books, setBooks] = useState<Book[]>([]);
    const baseUrl = "http://localhost:8080";
    const loadAllBooks = async () => {
        await axios.get(baseUrl + "/api/books", {
            withCredentials: true
        }).then((response) => {
            setBooks(response.data)
        })
            .catch((error) => {
                console.error(error);
            })
    };
    const addBook = async (newBook: BookModel) => {
        await axios.post(baseUrl + "/api/books", newBook, {
            withCredentials: true
        }).then((response) => {
            setBooks([...books, response.data])
            toast.success(newBook.title + ' was successfully added to your library', {
                position: "top-center",
                autoClose: 2000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "colored",
            });
        }).catch((error) => {
            toast.error(newBook.title + " is already in your Library " + error.response.statusText, {
                position: "top-center",
                autoClose: 1000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                theme: "light",
            })
        })
    }

    const updateBook = async (book: Book) => {
        await axios.put(baseUrl + `/api/books/${book.id}`, book, {
            withCredentials: true
        }).then((response) => {
            setBooks(books.map((currentBook) => {
                if (currentBook.id === book.id) {
                    return response.data;
                } else {
                    return currentBook;
                }
            }))
        }).catch(error => {
            console.error(error);
        })
    }

    const deleteBook = async (id: string) => {
        await axios.delete(baseUrl + `/api/books/${id}`, {
            withCredentials: true
        }).then(() => {
            setBooks(books.filter((book) => book.id !== id));
            /*   window.location.reload()*/
        }).catch((error) => {
            console.error(error)
        })
    };

    const [query, setQuery] = useState<string>('');

    return {
        books,
        query,
        setQuery,
        setBooks,
        addBook,
        loadAllBooks,
        deleteBook,
        updateBook,
    };
}