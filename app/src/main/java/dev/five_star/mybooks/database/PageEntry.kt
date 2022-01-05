package dev.five_star.mybooks.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "pages_table")
data class PageEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val bookId: Int,
    val date: Date?,
    val page: Int
)
