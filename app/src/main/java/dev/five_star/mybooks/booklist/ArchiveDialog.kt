package dev.five_star.mybooks.booklist

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import dev.five_star.mybooks.R
import javax.inject.Inject

@AndroidEntryPoint
class ArchiveDialog : DialogFragment() {

    @Inject
    lateinit var viewModelAssistedFactory: ArchiveViewModel.Factory

    private val args: ArchiveDialogArgs by navArgs()

    private val viewModel: ArchiveViewModel by viewModels {
        ArchiveViewModel.provideFactory(viewModelAssistedFactory, args.book)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(String.format(resources.getString(R.string.dialog_archive, args.book.title)))
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    viewModel.accept()
                }
                .setNegativeButton(android.R.string.cancel) { _, _ ->
                    viewModel.cancel()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}

