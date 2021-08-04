package dev.five_star.mybooks.View

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.five_star.mybooks.Model.Book
import dev.five_star.mybooks.Model.Dummy
import dev.five_star.mybooks.R
import dev.five_star.mybooks.databinding.FragmentMainBinding
import dev.five_star.mybooks.databinding.ItemBookCardBinding
import dev.five_star.mybooks.divideToPercent
import dev.five_star.mybooks.roundOffDecimal
import java.math.RoundingMode
import java.text.DecimalFormat

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val bookAdapter = BookAdapter() {
        val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(it)
        findNavController().navigate(action)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bookList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = bookAdapter
            bookAdapter.submitList(Dummy.bookList)
        }

        binding.addBook.setOnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_addBookDialog)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

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

    inner class ViewHolder(private val binding: ItemBookCardBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindBook(book: Book) {
            //TODO create ui model for book
            binding.itemBook.bookTitle.text = book.title
            val percent = book.currentPage.divideToPercent(book.pages)
            binding.itemBook.bookPercentText.text = "${roundOffDecimal(percent)} %"
            binding.itemBook.bookProgressBar.progress = percent.toInt()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBookCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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