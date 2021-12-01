package dev.five_star.mybooks.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(val id: Int, val title: String, val pages: Int, val currentPage: Int = 0) : Parcelable

