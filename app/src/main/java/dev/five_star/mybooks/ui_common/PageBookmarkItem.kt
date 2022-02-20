package dev.five_star.mybooks.ui_common

import android.text.format.DateUtils
import androidx.annotation.StringRes
import dev.five_star.mybooks.R
import dev.five_star.mybooks.data.PageBookmark
import dev.five_star.mybooks.utils.uiFormat

data class PageBookmarkItem(val date: DateViewItem, val page: Int)

sealed class DateViewItem {
    data class FormattedDate(val text: String) : DateViewItem()
    data class Reference(@StringRes val res: Int) : DateViewItem()
}

fun PageBookmark.toItem(): PageBookmarkItem {
    val dateViewItem = when {
        DateUtils.isToday(date.time) -> {
            DateViewItem.Reference(R.string.today)
        }
        else -> {
            DateViewItem.FormattedDate(date.uiFormat())
        }
    }
    return PageBookmarkItem(dateViewItem, page)
}
