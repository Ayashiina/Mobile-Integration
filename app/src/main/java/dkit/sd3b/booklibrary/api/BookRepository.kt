package dkit.sd3b.booklibrary.api

import dkit.sd3b.booklibrary.database.BookDao
import dkit.sd3b.booklibrary.model.Book
import android.util.Log
import androidx.lifecycle.LiveData
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
    suspend fun fetchBooks(query: String, pageIndex: Int, pageSize: Int): List<Book> {
        // Check for empty or invalid query
        if (query.isEmpty()) {
            Log.e("BookRepository", "Query string is empty")
            return emptyList()
        }

        // Validate maxResults
        if (pageSize !in 1..40) {
            Log.e("BookRepository", "Invalid pageSize: $pageSize, must be between 1 and 40")
            return emptyList()
        }

        // Calculate startIndex based on pageIndex and pageSize
        val startIndex = pageIndex * pageSize
        Log.d(
            "BookRepository",
            "Fetching books with query: $query, pageSize: $pageSize, startIndex: $startIndex"
        )

        try {
            val response = api.searchBooks(query, pageSize, startIndex)

            if (response.items.isEmpty()) {
                Log.d("BookRepository", "No books found for the query: $query")
                return emptyList()
            }

            val books = response.items.map { item ->
                val volumeInfo = item.volumeInfo

                // Ensure the image URL ends with ".jpg"
                val imageUrl =
                    volumeInfo.imageLinks?.thumbnail?.replace("http://", "https://")?.let {
                        if (!it.endsWith(".jpg")) {
                            "$it.jpg"
                        } else {
                            it
                        }
                    }

                Book(
                    title = volumeInfo.title ?: "No Title",
                    author = volumeInfo.authors?.joinToString(", ") ?: "Unknown Author",
                    description = volumeInfo.description,
                    isbn = volumeInfo.industryIdentifiers?.firstOrNull { it.type == "ISBN_13" }?.identifier,
                    releaseYear = volumeInfo.publishedDate,
                    genre = volumeInfo.categories?.joinToString(", "),
                    imageUrl = imageUrl
                )
            }

            // Insert books into the Room database
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

    fun getGenres(): LiveData<List<String>> {
        return bookDao.getDistinctGenres()
    }
}

// Define the Google Books API interface
interface GoogleBooksApi {
    @GET("books/v1/volumes")
    suspend fun searchBooks(
        @Query("q") query: String,
        @Query("maxResults") maxResults: Int,
        @Query("startIndex") startIndex: Int
    ): GoogleBooksResponse
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