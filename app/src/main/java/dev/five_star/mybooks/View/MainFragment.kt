package dev.five_star.mybooks.View

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.five_star.mybooks.Model.Book
import dev.five_star.mybooks.Model.Dummy
import dev.five_star.mybooks.R
import dev.five_star.mybooks.databinding.FragmentMainBinding
import dev.five_star.mybooks.databinding.ItemBookCardBinding
import java.math.RoundingMode
import java.text.DecimalFormat

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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
            adapter = BookAdapter(Dummy.bookList) {
                findNavController().navigate(R.id.action_mainFragment_to_detailsFragment)
            }
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

class BookAdapter(private val books: List<Book>, val itemClick: (book: Book) -> Unit) : RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemBookCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBookCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(books[position]) {
                binding.itemBook.bookTitle.text = this.title
                val percent = this.currentPage.divideToPercent(this.pages)
                binding.itemBook.bookPercentText.text = "${roundOffDecimal(percent)} %"
                binding.itemBook.bookProgressBar.progress = percent.toInt()
                binding.itemBook.itemBookLayout.setOnClickListener {
                    itemClick(this)
                }
            }
        }
    }

    override fun getItemCount() = books.size

    private fun Int.divideToPercent(divideTo: Int): Float {
        return if (divideTo == 0) 0f
        else (this / divideTo.toFloat()) * 100
    }

    private fun roundOffDecimal(number: Float): Float? {
        val df = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.HALF_UP
        return df.format(number).toFloat()
    }

}