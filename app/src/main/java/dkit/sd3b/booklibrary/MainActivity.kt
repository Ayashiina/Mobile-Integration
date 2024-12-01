package dkit.sd3b.booklibrary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import dkit.sd3b.booklibrary.api.BookRepository
import dkit.sd3b.booklibrary.model.BookViewModel
import dkit.sd3b.booklibrary.database.BookDatabase
import dkit.sd3b.booklibrary.navigation.NavHost

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = Room.databaseBuilder(
            applicationContext,
            BookDatabase::class.java,
            "book_database"
        ).fallbackToDestructiveMigration().build()
        val repository = BookRepository(database.bookDao())
        val viewModel = BookViewModel(repository)

        setContent {
            MaterialTheme {
                val navController = rememberNavController()
                NavHost(navController, viewModel)
            }
        }
    }
}
