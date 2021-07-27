package dev.five_star.mybooks.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.five_star.mybooks.Model.Book
import dev.five_star.mybooks.Model.Dummy
import dev.five_star.mybooks.Model.PagesEntry
import dev.five_star.mybooks.databinding.FragmentBookDetailBinding
import dev.five_star.mybooks.databinding.ItemBookPageBinding
import java.math.RoundingMode
import java.text.DecimalFormat

class DetailsFragment : Fragment() {

    private var _binding: FragmentBookDetailBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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
            adapter = PagesAdapter(Dummy.pageList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun Int.divideToPercent(divideTo: Int): Float {
        return if (divideTo == 0) 0f
        else (this / divideTo.toFloat()) * 100
    }

    private fun roundOffDecimal(number: Float): Float {
        val df = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.HALF_UP
        return df.format(number).toFloat()
    }
}

class PagesAdapter(private val pagesList: List<PagesEntry>) : RecyclerView.Adapter<PagesAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemBookPageBinding) : RecyclerView.ViewHolder(binding.root)  {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBookPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(pagesList[position]) {
                binding.enteredDate.text = this.date
                binding.page.text = this.page
            }
        }
    }

    override fun getItemCount() = pagesList.size

}