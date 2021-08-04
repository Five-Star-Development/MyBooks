package dev.five_star.mybooks.booklist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.five_star.mybooks.Model.Book
import dev.five_star.mybooks.databinding.ItemBookCardBinding
import dev.five_star.mybooks.divideToPercent
import dev.five_star.mybooks.roundOffDecimal

private object BookDiffUtil : DiffUtil.ItemCallback<Book>() {
    override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
        return oldItem == newItem //TODO compare the id as soon as we have a database
    }

    override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
        return (oldItem == newItem)
    }
}

class BookAdapter(val itemClick: (book: Book) -> Unit) :
    ListAdapter<Book, BookAdapter.ViewHolder>(BookDiffUtil) {

    inner class ViewHolder(private val binding: ItemBookCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindBook(book: Book) {
            //TODO create ui model for book
            binding.itemBook.bookTitle.text = book.title
            val percent = book.currentPage.divideToPercent(book.pages)
            binding.itemBook.bookPercentText.text = "${roundOffDecimal(percent)} %"
            binding.itemBook.bookProgressBar.progress = percent.toInt()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemBookCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val book = getItem(position)
        holder.bindBook(book)

        holder.itemView.setOnClickListener {
            itemClick(book)
        }

    }

}