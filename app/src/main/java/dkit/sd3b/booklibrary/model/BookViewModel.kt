package dkit.sd3b.booklibrary.model

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dkit.sd3b.booklibrary.api.BookRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.collections.List
import kotlinx.coroutines.launch

class BookViewModel(private val bookRepository: BookRepository) : ViewModel() {
    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books: StateFlow<List<Book>> = _books

    private val _readingList = MutableStateFlow<Set<Int>>(emptySet())
    val readingList: StateFlow<Set<Int>> = _readingList

    init {
        viewModelScope.launch {
            try {
                _books.value = bookRepository.getAllBooks()
            } catch (e: Exception) {
                Log.e("BookViewModel", "Error fetching books in init: ${e.message}", e)
            }
        }
    }

    fun fetchBooks() {
        viewModelScope.launch {
            try {
                val allBooks = bookRepository.getAllBooks()
                _books.value = allBooks
            } catch (e: Exception) {
                Log.e("BookViewModel", "Error fetching books: ${e.message}", e)
            }
        }
    }

    fun getBookById(bookId: Int): Book? {
        return _books.value.find { it.id == bookId }
    }

    fun addToReadingList(bookId: Int) {
        _readingList.value += bookId
    }
}

