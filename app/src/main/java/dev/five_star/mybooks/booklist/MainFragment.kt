package dev.five_star.mybooks.booklist

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import dev.five_star.mybooks.R
import dev.five_star.mybooks.booklist.MainViewModel.Event
import dev.five_star.mybooks.databinding.FragmentMainBinding
import dev.five_star.mybooks.requireMyBookApplication
import dev.five_star.mybooks.utils.ArchiveEvent
import dev.five_star.mybooks.utils.EventBus
import javax.inject.Inject

private const val TAG = "MainFragment"

@AndroidEntryPoint
class MainFragment : Fragment() {

    @Inject lateinit var archiveBus: EventBus<ArchiveEvent>

    private var _binding: FragmentMainBinding? = null
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by navGraphViewModels(R.id.nav_graph) {
        MainViewModelFactory(requireMyBookApplication().bookRepository, archiveBus)
    }

    private val bookAdapter = BookAdapter { bookId ->
        viewModel.dataInput(Event.SelectItem(bookId))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
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
            when(effect) {
                MainViewModel.Effect.RefreshList -> {
                    bookAdapter.notifyDataSetChanged()
                }
                is MainViewModel.Effect.UndoMessage -> {
                    Snackbar
                        .make(binding.bookList, String.format(resources.getString(R.string.book_archived, effect.title)), Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.undo)) {
                            viewModel.dataInput(Event.ActivateBook(effect.bookId))
                        }
                        .show()
                }
                is MainViewModel.Effect.Navigate -> {
                    val action = when (effect.action) {
                        is MainViewModel.Action.ShowDetails -> {
                            val bookId = effect.action.bookId
                            MainFragmentDirections.actionMainFragmentToDetailsFragment(bookId)
                        }
                        MainViewModel.Action.BookAdded -> {
                            MainFragmentDirections.actionMainFragmentToAddBookDialog()
                        }
                        is MainViewModel.Action.ShowArchive -> {
                            val book = effect.action.book
                            MainFragmentDirections.actionMainFragmentToArchiveDialog(book)
                        }                        }
                    findNavController().navigate(action)
                }
            }
        }
        setSwipeToDelete()
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setSwipeToDelete() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val bookId = bookAdapter.getBookId(viewHolder.adapterPosition)
                viewModel.dataInput(Event.ArchiveBook(bookId))
            }
        }).attachToRecyclerView(binding.bookList)
    }
}