package dev.five_star.mybooks.book_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.five_star.mybooks.database.PageEntry
import dev.five_star.mybooks.databinding.ItemBookPageBinding
import java.text.SimpleDateFormat

private object PagesDiffUtil : DiffUtil.ItemCallback<PageEntry>() {
    override fun areItemsTheSame(oldItem: PageEntry, newItem: PageEntry): Boolean {
        return oldItem == newItem //TODO compare the id as soon as we have a database
    }

    override fun areContentsTheSame(oldItem: PageEntry, newItem: PageEntry): Boolean {
        return (oldItem == newItem)
    }
}

class PagesAdapter : ListAdapter<PageEntry, PagesAdapter.ViewHolder>(PagesDiffUtil) {

    inner class ViewHolder(private val binding: ItemBookPageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindPagesEntry(entry: PageEntry) {
            val pattern = "dd.MM.yyyy"
            val simpleDateFormat = SimpleDateFormat(pattern)
            binding.enteredDate.text = simpleDateFormat.format(entry.date)
            binding.page.text = entry.pages.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemBookPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pagesEntry = getItem(position)
        holder.bindPagesEntry(pagesEntry)
    }

}