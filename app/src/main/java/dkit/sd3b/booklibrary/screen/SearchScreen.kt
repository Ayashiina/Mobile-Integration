package dkit.sd3b.booklibrary.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dkit.sd3b.booklibrary.model.Book
import dkit.sd3b.booklibrary.model.BookViewModel

private var <E> List<E>.value: List<E>
    get() {
        return this
    }
    set(value) {}

@Composable
fun SearchScreen(viewModel: BookViewModel, navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }
    var searchResults by remember { mutableStateOf(listOf<Book>()) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Search for Books", style = TextStyle(fontSize = 24.sp))

        Spacer(modifier = Modifier.height(16.dp))

        // Search Field
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            label = { Text("Search books") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { performSearch(searchQuery, viewModel, searchResults) }, modifier = Modifier.fillMaxWidth()) {
            Text("Search")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(searchResults) { book ->
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = book.title, style = MaterialTheme.typography.titleSmall)//check this
                    Text(text = book.author, style = MaterialTheme.typography.bodyLarge)//check this
                }
            }
        }
    }
}

fun performSearch(query: String, viewModel: BookViewModel, searchResults: List<Book>) {
    // Implement your search logic here
    viewModel.searchBooks(query) { results ->
        searchResults.value = results
    }
}