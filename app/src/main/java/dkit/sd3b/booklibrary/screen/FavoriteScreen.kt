package dkit.sd3b.booklibrary.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import dkit.sd3b.booklibrary.model.BookViewModel

@Composable
fun FavoriteScreen(viewModel: BookViewModel, navController: NavController) {
    val favoriteBooks by viewModel.books.observeAsState(emptyList())
    LazyColumn {
        items(favoriteBooks) { book ->
            Text(text = book.title.toString())
            Text(text = "Author: ${book.author}")
        }
    }
}
