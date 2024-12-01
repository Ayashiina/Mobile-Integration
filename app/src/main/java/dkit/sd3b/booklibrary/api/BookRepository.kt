package dkit.sd3b.booklibrary.api

import dkit.sd3b.booklibrary.database.BookDao
import dkit.sd3b.booklibrary.model.Book
import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class BookRepository(private val bookDao: BookDao) {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://www.googleapis.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(GoogleBooksApi::class.java)

    // Fetch books from Google Books API and insert into Room database
    suspend fun fetchBooks(): List<Book> {
        val query = "novels"
        try {
            val response = api.searchBooks("q=$query")


            if (response.items.isEmpty()) {
                Log.d("BookRepository", "No books found for the query: $query")
                return emptyList() // Return an empty list if no books are found
            }

            val books = response.items.map { item ->
                val volumeInfo = item.volumeInfo

                Book(
                    title = volumeInfo.title ?: "No Title",
                    author = volumeInfo.authors?.joinToString(", ") ?: "Unknown Author",
                    description = volumeInfo.description,
                    isbn = volumeInfo.industryIdentifiers?.firstOrNull { it.type == "ISBN_13" }?.identifier,
                    releaseYear = volumeInfo.publishedDate,
                    genre = volumeInfo.categories?.joinToString(", "),
                    imageUrl = volumeInfo.imageLinks?.thumbnail
                )
            }
            bookDao.insertBooks(books)
            return books
        } catch (e: Exception) {
            Log.e("BookRepository", "Error fetching books", e)
            return emptyList()
        }
    }

    // Fetch all books stored in Room DB
    suspend fun getAllBooks(): List<Book> {
        return bookDao.getAllBooks()
    }

}

// Define the Google Books API interface
interface GoogleBooksApi {
    @GET("books/v1/volumes")
    suspend fun searchBooks(@Query("q") query: String): GoogleBooksResponse
}

// Response models for Google Books API
data class GoogleBooksResponse(
    val items: List<GoogleBookItem>
)

data class GoogleBookItem(
    val volumeInfo: VolumeInfo
)

data class VolumeInfo(
    val title: String?,
    val authors: List<String>?,
    val description: String?,
    val publishedDate: String?,
    val industryIdentifiers: List<IndustryIdentifier>?,
    val categories: List<String>?,
    val imageLinks: ImageLinks?
)

data class IndustryIdentifier(
    val type: String,
    val identifier: String
)

data class ImageLinks(
    val thumbnail: String?
)

