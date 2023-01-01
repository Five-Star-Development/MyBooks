package dev.five_star.mybooks.data

import android.os.Parcelable
import dev.five_star.mybooks.database.PageEntry
import java.util.*
import kotlinx.parcelize.Parcelize

@Parcelize
data class PageBookmark(val date: Date, val page: Int) : Parcelable

fun PageEntry.toPageBookmark(): PageBookmark {
    return PageBookmark(date = date, page = page)
}
