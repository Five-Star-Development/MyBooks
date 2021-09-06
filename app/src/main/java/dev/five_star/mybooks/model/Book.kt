package dev.five_star.mybooks.model

import android.os.Parcelable
import dev.five_star.mybooks.divideToPercent
import dev.five_star.mybooks.model.ui_model.BookItem
import dev.five_star.mybooks.roundOffDecimal
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(val id: Int, val title: String, val pages: Int, val currentPage: Int = 0) : Parcelable

fun Book.toBookItem(): BookItem {
    val percent = currentPage.divideToPercent(pages)
    return BookItem(id, title, "${roundOffDecimal(percent)} %", percent.toInt())
}

