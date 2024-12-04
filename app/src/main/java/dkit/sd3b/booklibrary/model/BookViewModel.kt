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

    private val _favoriteBooks = MutableLiveData<List<Book>>(emptyList())
    val favoriteBooks: LiveData<List<Book>> = _favoriteBooks

    private val _selectedBook = MutableLiveData<Book?>()
    val selectedBook: LiveData<Book?> get() = _selectedBook

    private fun fetchBooks(query: String, pageIndex: Int, pageSize: Int) {
        viewModelScope.launch {
            try {
                val fetchedBooks = bookRepository.fetchBooks(query, pageIndex, pageSize)
                _books.postValue(fetchedBooks)
            } catch (e: Exception) {
                Log.e("BookLog", "Error fetching books: ${e.message}", e)
            }
        }
    }

    fun setNewQueryAndRefresh(query: String) {
        viewModelScope.launch {
            try {
                fetchBooks(query, pageIndex = 10, pageSize = 40)
                val newBooks = _books.value ?: emptyList()
                bookRepository.refreshBooks(newBooks)
            } catch (e: Exception) {
                Log.e("BookLog", "Error fetching or refreshing books: ${e.message}", e)
            }
        }
    }

    fun fetchBookById(bookId: Int) {
        viewModelScope.launch {
            val book = bookRepository.getBookById(bookId)
            _selectedBook.value = book
        }
    }


    fun fetchBooksFromDatabase() {
        viewModelScope.launch {
            val booksList = bookRepository.fetchBooksFromDatabase()
            _books.value = booksList
        }
    }

    fun addToFavorites(book: Book) {
        _favoriteBooks.value = _favoriteBooks.value?.plus(book)
    }
}
