package dev.five_star.mybooks.booklist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.navigation.navGraphViewModels
import dev.five_star.mybooks.R
import dev.five_star.mybooks.databinding.DialogNewBookBinding
import dev.five_star.mybooks.requireMyBookApplication

private const val TAG = "AddBookDialog"
class AddBookDialog : DialogFragment() {

    private var _binding: DialogNewBookBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by navGraphViewModels(R.id.nav_graph) {
        MainViewModelFactory(requireMyBookApplication().bookRepository)
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
            viewModel.dataInput(MainViewModel.Event.AddBook(bookTitleInput, bookPagesInput))
        }

        viewModel.dialogEffect.observe(viewLifecycleOwner) {
            when (it) {
                MainViewModel.DialogEffect.CloseAddBook -> dismiss()
                MainViewModel.DialogEffect.InputError -> showErrorMessage()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // This is a workaround for the issue that match_parent did not work from xml
        // see: https://medium.com/@lovejjfg/android-dialog-layout-match-parent-not-work-789cd23bc0de
        dialog?.window?.setLayout(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showErrorMessage() {
        val errorMessage = Toast.makeText(context, R.string.book_insert_error, Toast.LENGTH_LONG)
        errorMessage.show()
    }
}
