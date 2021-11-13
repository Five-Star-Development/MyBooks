package dev.five_star.mybooks.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.five_star.mybooks.divideToPercent
import dev.five_star.mybooks.model.ui_model.BookItem
import dev.five_star.mybooks.roundOffDecimal

@Entity(tableName = "book_table")
data class Book(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val pages: Int,
    val currentPage: Int = 0
)

fun Book.toBookItem(): BookItem {
    val percent = currentPage.divideToPercent(pages)
    return BookItem(id, title, "${roundOffDecimal(percent)} %", percent.toInt())
}
