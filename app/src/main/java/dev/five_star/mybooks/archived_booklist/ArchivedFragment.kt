package dev.five_star.mybooks.archived_booklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.five_star.mybooks.R
import dev.five_star.mybooks.booklist.BookAdapter
import dev.five_star.mybooks.databinding.FragmentArchivedBinding
import dev.five_star.mybooks.requireMyBookApplication

private const val TAG = "ArchivedFragment"

@AndroidEntryPoint
class ArchivedFragment : Fragment() {

    private var _binding: FragmentArchivedBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: ArchivedBooksViewModel by navGraphViewModels(R.id.nav_graph) {
        ArchivedBooksViewModelFactory(requireMyBookApplication().bookRepository)
    }

    private val bookAdapter = BookAdapter {
        //TODO: no clicks possible
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArchivedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bookList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = bookAdapter
        }

        viewModel.archivedBookList.observe(viewLifecycleOwner) { bookList ->
            bookAdapter.submitList(bookList)
        }


    }
}