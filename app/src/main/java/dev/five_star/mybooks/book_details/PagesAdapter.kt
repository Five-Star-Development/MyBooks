package dev.five_star.mybooks.book_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.five_star.mybooks.databinding.ItemBookPageBinding
import dev.five_star.mybooks.ui_common.DateViewItem
import dev.five_star.mybooks.ui_common.PageBookmarkItem

private object PagesDiffUtil : DiffUtil.ItemCallback<PageBookmarkItem>() {
    override fun areItemsTheSame(oldItem: PageBookmarkItem, newItem: PageBookmarkItem): Boolean {
        return oldItem.date == newItem.date
    }

    override fun areContentsTheSame(oldItem: PageBookmarkItem, newItem: PageBookmarkItem): Boolean {
        return (oldItem == newItem)
    }
}

class PagesAdapter : ListAdapter<PageBookmarkItem, PagesAdapter.ViewHolder>(PagesDiffUtil) {

    inner class ViewHolder(private val binding: ItemBookPageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindPagesEntry(entry: PageBookmarkItem) {
            val dateText = when (entry.date) {
                is DateViewItem.Reference -> {
                    itemView.context.getString(entry.date.res)
                }
                is DateViewItem.FormattedDate -> {
                    entry.date.text
                }
            }
            binding.enteredDate.text = dateText
            binding.page.text = entry.page.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemBookPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val pagesEntry: PageBookmarkItem = getItem(position)
        holder.bindPagesEntry(pagesEntry)
    }
}
