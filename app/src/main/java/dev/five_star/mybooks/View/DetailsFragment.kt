package dev.five_star.mybooks.View

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.five_star.mybooks.Model.Book
import dev.five_star.mybooks.Model.Dummy
import dev.five_star.mybooks.Model.PagesEntry
import dev.five_star.mybooks.databinding.FragmentBookDetailBinding
import dev.five_star.mybooks.databinding.ItemBookPageBinding
import dev.five_star.mybooks.divideToPercent
import dev.five_star.mybooks.roundOffDecimal

class DetailsFragment : Fragment() {

    private var _binding: FragmentBookDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val pagesEntryAdapter = PagesAdapter()
    private val args: DetailsFragmentArgs by navArgs()
    private lateinit var book: Book


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        book = args.book

        binding.itemBook.bookTitle.text = book.title
        val percent = book.currentPage.divideToPercent(book.pages)
        binding.itemBook.bookPercentText.text = "${roundOffDecimal(percent)} %"
        binding.itemBook.bookProgressBar.progress = percent.toInt()

        binding.pagesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = pagesEntryAdapter
            pagesEntryAdapter.submitList(Dummy.pageList)
        }

        binding.pagesEntry.setOnKeyListener { view, keyCode, keyEvent ->
            if (view !is TextView) {
                return@setOnKeyListener false
            }
            if (keyEvent.action == KeyEvent.ACTION_DOWN &&
                keyCode == KeyEvent.KEYCODE_ENTER
            ) {
                Dummy.pageList.add(
                    PagesEntry("today", view.text.toString())
                )
                view.text = null
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

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