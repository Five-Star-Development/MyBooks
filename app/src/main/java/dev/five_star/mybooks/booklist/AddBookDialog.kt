package dev.five_star.mybooks.booklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import dev.five_star.mybooks.data.BookRepository
import dev.five_star.mybooks.databinding.DialogNewBookBinding

class AddBookDialog : DialogFragment() {

    private var _binding: DialogNewBookBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val repository = BookRepository()

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

            val bookTitle: String = binding.bookTitle.text.toString()
            val bookPagesInput: String = binding.pages.text.toString()
            val bookPages: Int = if (bookPagesInput.isEmpty()) 0 else bookPagesInput.toInt()

            if(bookTitle.isNotEmpty() && bookPages > 0) {
                repository.addBook(bookTitle, bookPages)
                dismiss()
            }

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}