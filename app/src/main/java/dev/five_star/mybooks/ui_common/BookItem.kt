package dev.five_star.mybooks.ui_common

import dev.five_star.mybooks.data.Book
import dev.five_star.mybooks.divideToPercent
import dev.five_star.mybooks.roundOffDecimal

data class BookItem(
    val id: Int,
    val title: String,
    val totalPages: Int,
    val currentPage: Int,
    val percentText: String,
    val bookProcess: Int,
    val bookmarkList: List<PageBookmarkItem>
)

fun Book.toItem(): BookItem {
    val lastPage = if (bookmarks.isNotEmpty()) {
        bookmarks.last().page
    } else 0
    val percent = lastPage.divideToPercent(pages)

    val filteredBookmarks = bookmarks.reversed()
//        .filterIndexed { index, pageBookmark ->
//        if (index > 0) {
//            val previousDate = bookmarks[index - 1].date
//            Log.d("Book.toItem", pageBookmark.date.uiFormat())
//            Log.d("Book.toItem", previousDate.uiFormat())
//            (pageBookmark.date.uiFormat() != previousDate.uiFormat())
//        } else {
//            true
//        }
//    }

    return BookItem(
        id,
        title,
        pages,
        lastPage,
        "${roundOffDecimal(percent)} %",
        percent.toInt(),
        filteredBookmarks.map { it.toItem() }
    )
}