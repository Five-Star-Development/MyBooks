package dev.five_star.mybooks.booklist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dev.five_star.mybooks.R
import dev.five_star.mybooks.booklist.MainViewModel.Event
import dev.five_star.mybooks.databinding.FragmentMainBinding
import dev.five_star.mybooks.requireMyBookApplication

private const val TAG = "MainFragment"

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val bookAdapter = BookAdapter { bookId ->
        //TODO use the id and get the book from db
        viewModel.dataInput(Event.SelectItem(bookId))
    }

    private val viewModel: MainViewModel by navGraphViewModels(R.id.nav_graph) {
        MainViewModelFactory(requireMyBookApplication().bookRepository)
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
            viewModel.dataInput(Event.ShowAddBook)
        }

        viewModel.bookList.observe(viewLifecycleOwner) { bookList ->
            bookAdapter.submitList(bookList)
        }

        viewModel.effect.observe(viewLifecycleOwner) { effect ->
            val action = when(effect) {
                is MainViewModel.Effect.ShowDetails -> {
                    val bookId = effect.bookId
                    MainFragmentDirections.actionMainFragmentToDetailsFragment(bookId)
                }
                MainViewModel.Effect.AddBook -> {
                   MainFragmentDirections.actionMainFragmentToAddBookDialog()
                }
            }
            findNavController().navigate(action)
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}