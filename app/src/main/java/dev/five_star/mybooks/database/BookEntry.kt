package dev.five_star.mybooks.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_table")
data class BookEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val pages: Int,
)

//fun Book.toBookItem(): BookItem {
//    val percent = currentPage.divideToPercent(pages)
//    return BookItem(id, title, "${roundOffDecimal(percent)} %", percent.toInt())
//}
