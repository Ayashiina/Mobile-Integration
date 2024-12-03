package dkit.sd3b.booklibrary.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicText
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import dkit.sd3b.booklibrary.model.Book

@Composable
fun BookDetails(
    bookId: Int,
    fetchBook: (Int) -> Book?,
    onAddToListClick: (Int) -> Unit,
    navController: NavController
) {
    // Remember the fetched book for the given ID
    val book = remember { fetchBook(bookId) }

    // Show book details if found
    book?.let {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Title: ${it.title}", style = MaterialTheme.typography.h5)
            Text(text = "Author: ${it.author}", style = MaterialTheme.typography.h6)
            Text(text = "Year: ${it.releaseYear}", style = MaterialTheme.typography.body1)
            Text(
                text = "Description: ${it.description}",
                style = MaterialTheme.typography.body2
            )

            Button(
                onClick = { onAddToListClick(it.id) },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Add to List")
            }
        }
    } ?: run {
        // If the book is not found, show a message
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Book not found", style = MaterialTheme.typography.h6)
            Button(onClick = { navController.popBackStack() }) {
                Text("Go Back")
            }
        }
    }
}
