package dev.five_star.mybooks.ui_common

import dev.five_star.mybooks.data.Book
import dev.five_star.mybooks.utils.divideToPercent
import dev.five_star.mybooks.utils.roundOffDecimal

data class BookItem(
    val id: Int,
    val title: String,
    val totalPages: Int,
    val currentPage: Int,
    val percentText: String,
    val bookProcess: Int = 0,
    val bookmarkList: List<PageBookmarkItem> = emptyList()
)

fun Book.toItem(): BookItem {
    val lastPage = if (bookmarks.isNotEmpty()) { bookmarks.last().page } else 0
    val filteredBookmarks = if (bookmarks.isNotEmpty()) {bookmarks.sortedByDescending { it.date } } else emptyList()
    val percent = lastPage.divideToPercent(pages)
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
