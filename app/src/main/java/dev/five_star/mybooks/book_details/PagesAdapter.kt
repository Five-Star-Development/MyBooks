package dev.five_star.mybooks.book_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.five_star.mybooks.model.PagesEntry
import dev.five_star.mybooks.databinding.ItemBookPageBinding

private object PagesDiffUtil : DiffUtil.ItemCallback<PagesEntry>() {
    override fun areItemsTheSame(oldItem: PagesEntry, newItem: PagesEntry): Boolean {
        return oldItem == newItem //TODO compare the id as soon as we have a database
    }

    override fun areContentsTheSame(oldItem: PagesEntry, newItem: PagesEntry): Boolean {
        return (oldItem == newItem)
    }
}

class PagesAdapter : ListAdapter<PagesEntry, PagesAdapter.ViewHolder>(PagesDiffUtil) {

    inner class ViewHolder(private val binding: ItemBookPageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindPagesEntry(entry: PagesEntry) {
            binding.enteredDate.text = entry.date
            binding.page.text = entry.page
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