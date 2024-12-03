package dkit.sd3b.booklibrary

import dkit.sd3b.booklibrary.navigation.AppNavigator
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.room.Room
import dkit.sd3b.booklibrary.database.BookDao
import dkit.sd3b.booklibrary.database.BookDatabase
import dkit.sd3b.booklibrary.model.BookViewModel
import dkit.sd3b.booklibrary.model.BookViewModelFactory

class MainActivity : ComponentActivity() {
    private lateinit var bookDao: BookDao
    private lateinit var bookDatabase: BookDatabase

    private val bookViewModel: BookViewModel by viewModels {
        BookViewModelFactory(bookDao)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bookDatabase = Room.databaseBuilder(
            applicationContext,
            BookDatabase::class.java, "book-database"
        ).build()

        bookDao = bookDatabase.bookDao()

        setContent {
            MaterialTheme {
                AppNavigator(bookViewModel)
            }
        }
    }
}
