package dev.five_star.mybooks.book_details

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dev.five_star.mybooks.databinding.FragmentBookDetailBinding
import dev.five_star.mybooks.requireMyBookApplication

class DetailsFragment : Fragment() {

    private var _binding: FragmentBookDetailBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val pagesEntryAdapter = PagesAdapter()
    private val args: DetailsFragmentArgs by navArgs()

    private val viewModel: DetailsViewModel by viewModels {
        DetailsViewModelFactory(args.bookId, requireMyBookApplication().bookRepository)
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

        viewModel.bookData.observe(viewLifecycleOwner) { bookItem ->
            binding.itemBook.bookTitle.text = bookItem.title
            binding.itemBook.bookPercentText.text = bookItem.percentText
            binding.itemBook.bookProgressBar.progress = bookItem.bookProcess

            pagesEntryAdapter.submitList(bookItem.bookmarkList)
        }

        viewModel.pageEntry.observe(viewLifecycleOwner) {
            binding.pagesEntry.setText(it)
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { errorRes ->
            Snackbar.make(binding.root, errorRes, Snackbar.LENGTH_INDEFINITE)
                .setAction(android.R.string.ok) {
                    val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.showSoftInput(binding.pagesEntry, InputMethodManager.SHOW_IMPLICIT)
                }.show()
        }

        binding.pagesEntry.setOnKeyListener { view, keyCode, keyEvent ->
            if ((keyEvent.action == KeyEvent.ACTION_DOWN
                        && keyCode == KeyEvent.KEYCODE_ENTER)
                        && view is TextView) {
                viewModel.verifyEnter(view.text?.toString())
                false
            } else {
                false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}