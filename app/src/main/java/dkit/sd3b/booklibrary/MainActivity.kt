package dkit.sd3b.booklibrary

import dkit.sd3b.booklibrary.navigation.AppNavigator
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.room.Room
import dkit.sd3b.booklibrary.database.BookDao
import dkit.sd3b.booklibrary.database.BookDatabase
import dkit.sd3b.booklibrary.model.BookViewModel
import dkit.sd3b.booklibrary.model.BookViewModelFactory
import dkit.sd3b.booklibrary.ui.theme.BookSphereTheme

class MainActivity : ComponentActivity() {
    private lateinit var bookDao: BookDao
    private lateinit var bookDatabase: BookDatabase

    private val bookViewModel: BookViewModel by viewModels {
        BookViewModelFactory(bookDao)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        bookDatabase = Room.databaseBuilder(
            applicationContext,
            BookDatabase::class.java, "book-database"
        ).fallbackToDestructiveMigration().build()

        bookDao = bookDatabase.bookDao()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        setContent {
            BookSphereTheme {
              AppNavigator(bookViewModel)
            }
        }
    }

}
