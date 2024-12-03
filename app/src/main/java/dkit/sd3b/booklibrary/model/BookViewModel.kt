package dkit.sd3b.booklibrary.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dkit.sd3b.booklibrary.api.BookRepository
import kotlinx.coroutines.launch

class BookViewModel(private val bookRepository: BookRepository) : ViewModel() {

    private val _books = MutableLiveData<List<Book>>(emptyList())
    val books: LiveData<List<Book>> = _books

    val genres: LiveData<List<String>> = bookRepository.getGenres()

    private val _readingList = MutableLiveData<Set<Int>>(emptySet())
    val readingList: LiveData<Set<Int>> = _readingList

    init {
        "modern novels".fetchBooks(10, 30)
    }

    private fun String.fetchBooks(minRating: Int, maxResults: Int) {
        viewModelScope.launch {
            try {
                val fetchedBooks = bookRepository.fetchBooks(this@fetchBooks, minRating, maxResults)
                _books.postValue(fetchedBooks)
            } catch (e: Exception) {
                Log.e("BookViewModel", "Error fetching books: ${e.message}", e)
            }
        }
    }

    fun refreshBooks() {
        viewModelScope.launch {
            try {
                val allBooks = bookRepository.getAllBooks()
                _books.postValue(allBooks)
            } catch (e: Exception) {
                Log.e("BookViewModel", "Error refreshing books: ${e.message}", e)
            }
        }
    }

    fun getBookById(bookId: Int): Book? {
        return _books.value?.find { it.id == bookId }
    }

    fun addToList(bookId: Int) {
        val currentList = _readingList.value.orEmpty()
        _readingList.postValue(currentList + bookId)
    }
}
