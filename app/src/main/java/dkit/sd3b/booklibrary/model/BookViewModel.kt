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

    private val _favoriteBooks = MutableLiveData<List<Book>>()
    val favoriteBooks: LiveData<List<Book>> = _favoriteBooks

    private val _selectedBook = MutableLiveData<Book?>()
    val selectedBook: LiveData<Book?> get() = _selectedBook

    init {
        fetchBooks("modern fantasy novels", 10, 40)
    }


    private fun fetchBooks(query: String, pageIndex: Int, pageSize: Int) {
        viewModelScope.launch {
            try {
                val fetchedBooks = bookRepository.fetchBooks(query, pageIndex, pageSize)
                _books.postValue(fetchedBooks)
            } catch (e: Exception) {
                Log.e("BookLog-View", "Error fetching books: ${e.message}", e)
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
                Log.e("BookLog-View", "Error fetching or refreshing books: ${e.message}", e)
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
    fun searchBooks(query: String) {
        viewModelScope.launch {
            try {
                val results = bookRepository.searchBooks(query)
                _books.value = results
            } catch (e: Exception) {
                Log.e("BookViewModel", "Error searching books: ${e.message}", e)
            }
        }
    }

    fun addToFavorites(bookId: Int, isFavorite: Boolean) {
        viewModelScope.launch {
            if (isFavorite) {
                bookRepository.addBookToFavorites(bookId)
            } else {
                bookRepository.removeBookFromFavorites(bookId)
            }
            fetchFavoriteBooks()
        }
    }

    fun fetchFavoriteBooks() {
        viewModelScope.launch {
            val favorites = bookRepository.getFavoriteBooks()
            _favoriteBooks.value = favorites
        }
    }
}
