package dev.five_star.mybooks.booklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import dev.five_star.mybooks.data.BookRepository
import dev.five_star.mybooks.databinding.DialogNewBookBinding

const val TAG = "AddBookDialog"
class AddBookDialog : DialogFragment() {

    private var _binding: DialogNewBookBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel : MainViewModel by activityViewModels {
        MainViewModelFactory(BookRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogNewBookBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addButton.setOnClickListener {
            val bookTitleInput: String = binding.bookTitle.text.toString()
            val bookPagesInput: String = binding.pages.text.toString()
            viewModel.dataInput(MainViewModel.Input.AddBook(bookTitleInput, bookPagesInput))
        }

        viewModel.dialogEffect.observe(viewLifecycleOwner) {
            when(it) {
                MainViewModel.DialogEffect.CloseAddBook -> dismiss()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}