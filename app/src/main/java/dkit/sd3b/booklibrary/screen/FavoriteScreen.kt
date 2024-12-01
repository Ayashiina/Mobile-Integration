//package dkit.sd3b.booklibrary.book
//
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.Button
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.collectAsState
//import androidx.compose.runtime.getValue
//import dkit.sd3b.booklibrary.model.BookViewModel
//
//@Composable
//fun FavoriteBooksScreen(viewModel: BookViewModel) {
//    val favoriteBooks by viewModel.books.collectAsState()
//
//    LazyColumn {
//        items(favoriteBooks) { book ->
//            Text(text = book.title)
//            Text(text = "Author: ${book.author}")
//            // Add more UI components to display book details
//        }
//    }
//}
//
//@Composable
//fun ToggleFavoriteButton(bookId: Int, viewModel: BookViewModel) {
//    Button(onClick = { viewModel.toggleFavorite(bookId) }) {
//        Text("Toggle Favorite")
//    }
//}
