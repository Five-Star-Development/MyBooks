package dev.five_star.mybooks.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dev.five_star.mybooks.Model.Dummy
import dev.five_star.mybooks.Model.PagesEntry
import dev.five_star.mybooks.databinding.FragmentBookDetailBinding
import dev.five_star.mybooks.databinding.ItemBookPageBinding

class DetailsFragment : Fragment() {

    private var _binding: FragmentBookDetailBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

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

        binding.itemBook.bookTitle.text = "Dummy Book"
        binding.itemBook.bookPercentText.text = "34%"
        binding.itemBook.bookProgressBar.progress = 34


        binding.pagesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = PagesAdapter(Dummy.pageList)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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