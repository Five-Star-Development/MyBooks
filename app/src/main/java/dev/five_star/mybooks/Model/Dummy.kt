package dev.five_star.mybooks.Model

class Dummy {

    companion object {
        val bookList = mutableListOf(
            Book(1,"Book Number 1", 300, 3),
            Book(2, "Book Number 2", 300, 30),
            Book(3,"Book Number 3", 300, 233),
            Book(4,"Book Number 4", 300, 113),
            Book(5,"Book Number 5", 300, 299),
            Book(6,"Book Number 6", 300, 300),
            Book(7,"Book Number 7", 300, 100),
        )

        val pageList = mutableListOf(
            PagesEntry("13.03.2021", "13"),
            PagesEntry("14.03.2021", "35"),
            PagesEntry("15.03.2021", "67"),
            PagesEntry("16.03.2021", "89"),
            PagesEntry("17.03.2021", "111"),
        )
    }

}