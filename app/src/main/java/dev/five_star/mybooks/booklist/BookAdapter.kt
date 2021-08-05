package dev.five_star.mybooks.booklist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.five_star.mybooks.Model.ui_model.BookItem
import dev.five_star.mybooks.databinding.ItemBookCardBinding

private object BookDiffUtil : DiffUtil.ItemCallback<BookItem>() {
    override fun areItemsTheSame(oldItem: BookItem, newItem: BookItem): Boolean {
        return oldItem == newItem //TODO compare the id as soon as we have a database
    }

    override fun areContentsTheSame(oldItem: BookItem, newItem: BookItem): Boolean {
        return (oldItem == newItem)
    }
}

class BookAdapter(val itemClick: (position: Int) -> Unit) :
    ListAdapter<BookItem, BookAdapter.ViewHolder>(BookDiffUtil) {

    inner class ViewHolder(private val binding: ItemBookCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindBook(bookItem: BookItem) {
            binding.itemBook.bookTitle.text = bookItem.title
            binding.itemBook.bookPercentText.text = bookItem.percentText
            binding.itemBook.bookProgressBar.progress = bookItem.bookProcess
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
            itemClick(position)
        }

    }

}