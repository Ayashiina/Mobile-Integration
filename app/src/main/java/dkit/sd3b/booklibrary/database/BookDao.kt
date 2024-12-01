package dkit.sd3b.booklibrary.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dkit.sd3b.booklibrary.model.Book

@Dao
interface BookDao {

    @Query("SELECT * FROM books")
    suspend fun getAllBooks(): List<Book>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooks(books: List<Book>)

    @Query("SELECT * FROM books WHERE id = :bookId")
    suspend fun getBookById(bookId: Int): Book?
}
