package dev.five_star.mybooks.booklist

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import dev.five_star.mybooks.R

class ArchiveDialog(val title: String, var archive: () -> Unit, var cancel: () -> Unit) :
    DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setMessage(String.format(resources.getString(R.string.dialog_archive, title)))
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    archive.invoke()
                }
                .setNegativeButton(android.R.string.cancel) { _, _ ->
                    cancel.invoke()
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}