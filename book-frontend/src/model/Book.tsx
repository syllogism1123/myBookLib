export  type Book = {
    id: string,
    googleBookId: string,
    title: string,
    authors: string[],
    publisher: string,
    publishedDate: string,
    description: string
    imageUrl: string,
    userId: string

}


export  type BookModel = {
    googleBookId: string,
    title: string,
    authors: string[],
    publisher: string,
    publishedDate: string,
    description: string
    imageUrl: string,
    userId: string
}