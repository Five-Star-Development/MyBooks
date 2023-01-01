package dev.five_star.mybooks.booklist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.five_star.mybooks.MainCoroutineRule
import dev.five_star.mybooks.data.BookRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class MainViewModelTest {

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    lateinit var testBookRepo: BookRepository
    lateinit var testMainViewModel: MainViewModel

    @Before
    fun testSetup() {
        testBookRepo = MockedBookRepo()
        testMainViewModel = MainViewModel(testBookRepo, mainCoroutineRule.dispatcher)
    }

    @Test
    fun `show add book dialog`() {
        testMainViewModel.dataInput(MainViewModel.Event.ShowAddBook)
        val mostRecentEffect = testMainViewModel.effect.value
        if (mostRecentEffect is MainViewModel.Effect.Navigate) {
            assertThat(mostRecentEffect.action, `is`(MainViewModel.Action.BookAdded))
        }
    }

    @Test
    fun `fail by adding empty book title`() {
        val bookTitle = ""
        val bookPages = "77"
        val addBookEvent = MainViewModel.Event.AddBook(bookTitle, bookPages)
        testMainViewModel.dataInput(addBookEvent)
        assertThat(
            testMainViewModel.dialogEffect.value,
            `is`(MainViewModel.DialogEffect.InputError)
        )
    }

    @Test
    fun `fail by adding 0 pages`() {
        val bookTitle = "my_book_title"
        val bookPages = "0"
        val addBookEvent = MainViewModel.Event.AddBook(bookTitle, bookPages)
        testMainViewModel.dataInput(addBookEvent)
        assertThat(
            testMainViewModel.dialogEffect.value,
            `is`(MainViewModel.DialogEffect.InputError)
        )
    }

    @Test
    fun `open selected item`() {
        val testBookId = 1
        testMainViewModel.dataInput(MainViewModel.Event.SelectItem(testBookId))
        val effect = testMainViewModel.effect.value
        if (effect is MainViewModel.Effect.Navigate) {
            val action = effect.action
            if (action is MainViewModel.Action.ShowDetails) {
                assert(action.bookId == testBookId) {
                    "error message here"
                }
            } else {
                assert(false)
            }
        } else {
            assert(false)
        }
    }

    @Test
    fun `add correct book`() = runTest {
        val bookTitle = "my_book_title"
        val bookPages = "77"
        val addBookEvent = MainViewModel.Event.AddBook(bookTitle, bookPages)
        testMainViewModel.dataInput(addBookEvent)
        assertThat(
            testMainViewModel.dialogEffect.value,
            `is`(MainViewModel.DialogEffect.CloseAddBook)
        )
    }

    @Test
    fun `archive book`() = runTest {
        val books = testBookRepo.getAllBooks().first()
        val book = books[0]
        val archiveEvent = MainViewModel.Event.ArchiveBook(book.id)
        testMainViewModel.dataInput(archiveEvent)
        val newBooks = testBookRepo.getAllBooks().first()
        assert(!newBooks.contains(book))
    }

    @Test
    fun `activate archived book`() = runTest {
        val books = testBookRepo.getAllBooks().first()
        val book = books[0]
        val archiveEvent = MainViewModel.Event.ArchiveBook(book.id)
        testMainViewModel.dataInput(archiveEvent)
        val booksWithArchived = testBookRepo.getAllBooks().first()
        assert(!booksWithArchived.contains(book))

        val activateEvent = MainViewModel.Event.ActivateBook(book.id)
        testMainViewModel.dataInput(activateEvent)
        val booksWithActivated = testBookRepo.getAllBooks().first()
        assert(booksWithActivated.contains(book))
    }

    @Test
    fun `notify undo message`() = runTest {
        val bookId = 2
        val archiveBookEvent = MainViewModel.Event.ArchiveBook(bookId)
        testMainViewModel.dataInput(archiveBookEvent)
        val effect = testMainViewModel.effect.value
        if (effect is MainViewModel.Effect.UndoMessage) {
            assert(effect.bookId == bookId)
        } else {
            assert(false)
        }
    }
}
