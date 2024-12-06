package dkit.sd3b.booklibrary.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dkit.sd3b.booklibrary.model.Book


@Database([Book::class], version = 5, exportSchema = false)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
}
