package dkit.sd3b.booklibrary.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dkit.sd3b.booklibrary.api.BookRepository
import dkit.sd3b.booklibrary.database.BookDao

class BookViewModelFactory(private val bookDao: BookDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookViewModel::class.java)) {
            return BookViewModel(BookRepository(bookDao)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
