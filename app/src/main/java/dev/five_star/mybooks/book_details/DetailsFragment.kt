package dev.five_star.mybooks.book_details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dev.five_star.mybooks.MyBookApplication
import dev.five_star.mybooks.databinding.FragmentBookDetailBinding

class DetailsFragment : Fragment() {

    private var _binding: FragmentBookDetailBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val pagesEntryAdapter = PagesAdapter()
    private val args: DetailsFragmentArgs by navArgs()


    private val viewModel: DetailsViewModel by viewModels {
        DetailsViewModelFactory(args.bookId, (requireActivity().application as MyBookApplication).bookRepository)
    }

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

        binding.pagesList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = pagesEntryAdapter
        }

        viewModel.bookDetails.observe(viewLifecycleOwner, { bookItem ->
            binding.itemBook.bookTitle.text = bookItem.title
            binding.itemBook.bookPercentText.text = bookItem.percentText
            binding.itemBook.bookProgressBar.progress = bookItem.bookProcess
        })

        viewModel.pagesList.observe(viewLifecycleOwner, { pageList ->
            pagesEntryAdapter.submitList(pageList)
        })

        viewModel.pageEntry.observe(viewLifecycleOwner, {
            binding.pagesEntry.setText(it)
        })

        binding.pagesEntry.setOnKeyListener { view, keyCode, keyEvent ->
            return@setOnKeyListener viewModel.verifyEnter(view, keyEvent, keyCode)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}