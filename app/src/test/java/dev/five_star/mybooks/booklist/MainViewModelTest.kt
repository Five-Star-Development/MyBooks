package dev.five_star.mybooks.booklist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import dev.five_star.mybooks.data.BookRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
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

    val dispatcher = TestCoroutineDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    lateinit var testBookRepo : BookRepository
    lateinit var testMainViewModel: MainViewModel

    @Before
    fun testSetup() {
        testBookRepo = MockedBookRepo()
        testMainViewModel = MainViewModel(testBookRepo)
    }

    @Test
    fun `show add book dialog`() {
        testMainViewModel.dataInput(MainViewModel.Event.ShowAddBook)
        assertThat(testMainViewModel.effect.value, `is` (MainViewModel.Effect.BookAdded))
    }

    @Test
    fun `open selected item`() {
        val testBookId = 1
        testMainViewModel.dataInput(MainViewModel.Event.SelectItem(1))
        val effect = testMainViewModel.effect.value
        if (effect is MainViewModel.Effect.ShowDetails) {
            assertThat(effect.bookId, `is` (testBookId))
        }
    }

    @Test
    fun `add correct book`() = runBlockingTest {
        val bookTitle = "my_book_title"
        val bookPages = "99"
        val addBookEvent = MainViewModel.Event.AddBook(bookTitle, bookPages)
        testMainViewModel.dataInput(addBookEvent)
        assertThat(testMainViewModel.dialogEffect.value, `is` (MainViewModel.DialogEffect.CloseAddBook))
    }

}