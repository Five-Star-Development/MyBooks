package dev.five_star.mybooks.booklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dev.five_star.mybooks.Model.Dummy
import dev.five_star.mybooks.R
import dev.five_star.mybooks.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val bookAdapter = BookAdapter {
        //TODO use the id and get the book from db
        val selectedBook = Dummy.bookList[it]
        val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(selectedBook)
        findNavController().navigate(action)
        //TODO why does this not work?
//        viewModel.itemSelected(it)
    }

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory()
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
            findNavController().navigate(R.id.action_mainFragment_to_addBookDialog)
        }

        viewModel.bookList.observe(viewLifecycleOwner) { bookList ->
            bookAdapter.submitList(bookList)
        }

        viewModel.openBook.observe(viewLifecycleOwner) { book ->
            val action = MainFragmentDirections.actionMainFragmentToDetailsFragment(book)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}