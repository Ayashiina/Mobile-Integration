package dkit.sd3b.booklibrary.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import dkit.sd3b.booklibrary.model.Book

@Dao
interface BookDao {
    @Query("SELECT * FROM books")
    suspend fun getAllBooks(): List<Book>

    @Query("SELECT DISTINCT genre FROM books")
    fun getDistinctGenres(): LiveData<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooks(books: List<Book>)

    @Query("SELECT * FROM books WHERE id = :bookId")
    suspend fun getBookById(bookId: Int): Book?

    @Query("DELETE FROM books")
    suspend fun deleteAllBooks()

    @Transaction
    suspend fun refreshBooks(books: List<Book>) {
        deleteAllBooks()
        insertBooks(books)
    }
}
