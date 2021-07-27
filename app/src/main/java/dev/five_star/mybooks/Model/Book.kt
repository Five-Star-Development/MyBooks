package dev.five_star.mybooks.Model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Book(val title: String, val pages: Int, val currentPage: Int) : Parcelable