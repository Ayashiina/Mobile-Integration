package dkit.sd3b.booklibrary.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import dkit.sd3b.booklibrary.model.Book


@Database(entities = [Book::class], version = 3, exportSchema = false)
abstract class BookDatabase : RoomDatabase() {
    abstract fun bookDao(): BookDao
}
