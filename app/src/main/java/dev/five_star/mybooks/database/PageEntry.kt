package dev.five_star.mybooks.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "pages_table")
data class PageEntry(
    val bookId: Int,
    @PrimaryKey val date: Date,
    val page: Int
)
