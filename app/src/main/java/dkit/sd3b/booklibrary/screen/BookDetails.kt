package dkit.sd3b.booklibrary.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dkit.sd3b.booklibrary.model.Book

@Composable
fun BookDetails(
    bookId: Int,
    fetchBook: (Int) -> Book?,
    onAddToListClick: (Int) -> Unit
) {
    val book = remember { fetchBook(bookId) }

    book?.let {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Title: ${it.title}", style = MaterialTheme.typography.headlineMedium)
            Text(text = "Author: ${it.author}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Year: ${it.releaseYear}", style = MaterialTheme.typography.bodyLarge)
            Text(
                text = "Description: ${it.description}",
                style = MaterialTheme.typography.bodyLarge
            )

            Button(onClick = { onAddToListClick(it.id) }) {
                Text("Add to List")
            }
        }
    } ?: Text("Book not found", modifier = Modifier.padding(16.dp))
}

