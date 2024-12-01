package dkit.sd3b.booklibrary.screen

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import dkit.sd3b.booklibrary.R
import dkit.sd3b.booklibrary.model.Book
import dkit.sd3b.booklibrary.model.BookViewModel


@Composable
fun BookList(viewModel: BookViewModel, navController: NavHostController) {
    // Collect the list of books from the ViewModel
    val books by viewModel.books.collectAsState()

    // Display a list of books
    LazyColumn {
        items(books) { book ->
            BookItem(book = book) {
                Log.d("BookListScreen", "Clicked on book: ${book.title}")
                navController.navigate("bookDetails/${book.id}")
            }
        }
    }
}

@Composable
fun BookItem(book: Book, onBookClick: (Book) -> Unit) {
    Row(
        modifier = Modifier
            .clickable { onBookClick(book) }
            .padding(16.dp)
    ) {
        AsyncImage(
            model = book.imageUrl,
            contentDescription = book.title,
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp)),
            onError = { exception ->
                Log.e("BookItem", "Error loading image: $exception")
            },
            placeholder = painterResource(R.drawable.img), // Optional placeholder
            error = painterResource(R.drawable.img_1)
        )
        Log.d("BookItem", "Image URL: ${book.imageUrl}")

        Column(modifier = Modifier.padding(8.dp)) {
            Text(text = book.title.toString(), style = MaterialTheme.typography.bodyLarge)
            Text(text = "Author: ${book.author}", style = MaterialTheme.typography.bodyMedium)

        }
    }
}
