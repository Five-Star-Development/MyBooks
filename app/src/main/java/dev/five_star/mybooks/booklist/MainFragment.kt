package dev.five_star.mybooks.booklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dev.five_star.mybooks.booklist.MainViewModel.Input
import dev.five_star.mybooks.data.BookRepository
import dev.five_star.mybooks.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val bookAdapter = BookAdapter { bookId ->
        //TODO use the id and get the book from db
        viewModel.dataInput(Input.SelectItem(bookId))
    }

    private val viewModel: MainViewModel by activityViewModels {
        MainViewModelFactory(BookRepository)
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
        }

        binding.addBookButton.setOnClickListener {
            viewModel.dataInput(Input.ShowAddBook)
        }

        viewModel.bookList.observe(viewLifecycleOwner) { bookList ->
            bookAdapter.submitList(bookList)
        }

        viewModel.effect.observe(viewLifecycleOwner) { effect ->
            val action = when(effect) {
                is MainViewModel.Effect.ShowDetails -> {
                    val bookId = effect.selectedBook
                    MainFragmentDirections.actionMainFragmentToDetailsFragment(bookId)
                }
                MainViewModel.Effect.AddBook -> {
                   MainFragmentDirections.actionMainFragmentToAddBookDialog()
                }
            }
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}