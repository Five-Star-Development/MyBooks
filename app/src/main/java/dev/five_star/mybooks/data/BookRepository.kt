package dev.five_star.mybooks.data

import dev.five_star.mybooks.model.Book
import dev.five_star.mybooks.model.PagesEntry

object BookRepository {

    private val bookList = mutableListOf(
        Book(1,"Book Number 1", 300, 3),
        Book(2, "Book Number 2", 300, 30),
        Book(3,"Book Number 3", 300, 233),
        Book(4,"Book Number 4", 300, 113),
        Book(5,"Book Number 5", 300, 299),
        Book(6,"Book Number 6", 300, 300),
        Book(7,"Book Number 7", 300, 100),
    )

    private val pageList = mutableListOf(
        PagesEntry("13.03.2021", "13"),
        PagesEntry("14.03.2021", "35"),
        PagesEntry("15.03.2021", "67"),
        PagesEntry("16.03.2021", "89"),
        PagesEntry("17.03.2021", "111"),
    )

    fun getAllBooks(): List<Book> {
        return bookList
    }

    // Faaaake the id just the position for now
    fun getBook(id: Int): Book {
        return bookList[id]
    }

    fun addBook(bookTitle: String,  bookPages: Int) : Boolean {
        val newFakeId = (bookList.last().id + 1)
        return bookList.add(
            Book(
                newFakeId,
                bookTitle,
                bookPages,
            )
        )
    }

    // Haha this is part of the faknes ;-)
    fun getPagesForBook(book: Book): List<PagesEntry> {
        return pageList
    }

    fun addPageEntry(enteredPage: String) {
        pageList.add(PagesEntry("today", enteredPage))
    }

}