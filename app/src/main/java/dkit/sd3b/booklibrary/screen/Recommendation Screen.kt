package dkit.sd3b.booklibrary.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dkit.sd3b.booklibrary.model.Book

@Composable
fun RecommendationScreen(navController: NavController) {
    var recommendedBooks = listOf(
        Book(1, "Author 1", "Description", "https://via.placeholder.com/150","",""),
        Book(2, "Author 2", "Description", "https://via.placeholder.com/150","","")
    )

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Recommended Books", style = TextStyle(fontSize = 24.sp))

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(recommendedBooks) { book ->
               // BookItem(book = book, onClick = { /* Handle click */ })
            }
        }
    }
}
