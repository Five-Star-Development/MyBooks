package dev.five_star.mybooks.ui_common

import dev.five_star.mybooks.data.Book
import dev.five_star.mybooks.divideToPercent
import dev.five_star.mybooks.roundOffDecimal

data class BookItem(val id: Int, val title: String, val percentText: String, val bookProcess: Int)

fun Book.toBookItem(): BookItem {
    val percent = currentPage.divideToPercent(pages)
    return BookItem(id, title, "${roundOffDecimal(percent)} %", percent.toInt())
}