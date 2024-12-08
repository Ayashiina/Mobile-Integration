package dkit.sd3b.booklibrary.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import dkit.sd3b.booklibrary.model.Book
import dkit.sd3b.booklibrary.model.BookViewModel
import dkit.sd3b.booklibrary.navigation.ScreenNavigation

@Composable
fun SearchScreen(viewModel: BookViewModel, navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }
    val searchResults by viewModel.books.observeAsState(emptyList())
    var performSearch by remember { mutableStateOf(false) }
    var noResultsFound by remember { mutableStateOf(false) }

    Log.d("BookLog-Search", "Current search query: $searchQuery")

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        item {
            Text(
                "Search for Books",
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Search Field Section
        item {
            TextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Search books") },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(8.dp)),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Search Button Section
        item {
            Button(
                onClick = {
                    if (searchQuery.isNotEmpty()) {
                        Log.d("BookLog-Search", "Searching for books with query: $searchQuery")
                        viewModel.searchBooks(searchQuery)
                        performSearch = true
                        noResultsFound = false
                    } else {
                        Log.d("BookLog-Search", "Search query is empty, no action taken")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                Text("Search", style = MaterialTheme.typography.bodyMedium)
            }
            Spacer(modifier = Modifier.height(16.dp))
        }

        if (performSearch && searchQuery.isNotEmpty()) {
            if (searchResults.isEmpty()) {
                item {
                    Text(
                        text = "No books found. Please try a different search.",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                noResultsFound = true
            } else {
                items(searchResults) { book ->
                    SearchItems(book, onClick = {
                        Log.d("BookLog-Search", "Navigating to details for book: ${book.title}")
                        navController.navigate(ScreenNavigation.BookDetail.createRoute(book.id))
                    })
                }
            }
        }
    }
}

@Composable
fun SearchItems(book: Book, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(book.thumbnail),
                contentDescription = book.title,
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = book.title ?: "Unknown Title",
                    style = MaterialTheme.typography.headlineSmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = book.author ?: "Unknown Author",
                    style = MaterialTheme.typography.bodyLarge,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = "Navigate to details",
                modifier = Modifier.size(24.dp),
                tint = MaterialTheme.colorScheme.primary
            )
        }
    }
}
