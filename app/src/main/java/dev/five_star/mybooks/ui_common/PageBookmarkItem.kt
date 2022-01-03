package dev.five_star.mybooks.ui_common

import android.text.format.DateUtils
import dev.five_star.mybooks.data.PageBookmark
import java.text.SimpleDateFormat

data class PageBookmarkItem(val date: String, val page: Int)

fun PageBookmark.toItem(): PageBookmarkItem {
    val dateText = if (DateUtils.isToday(date.time)) {
        "Today"
    } else {
        val pattern = "dd.MM.yyyy"
        val simpleDateFormat = SimpleDateFormat(pattern)
        simpleDateFormat.format(date)
    }
    return PageBookmarkItem(dateText, page)
}
