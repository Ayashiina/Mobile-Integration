package dkit.sd3b.booklibrary.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "books")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String?,
    val isbn: String?,
    val author: String?,
    val releaseYear: String?,
    val imageUrl: String?,
    val rating: Float? = null,
    val genre: String? = null,
    val description: String? = null,
    val profilePicture: String? = null,
    val wallpaperUrl: String? = null,
)


