package dkit.sd3b.booklibrary.screen

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun SearchScreen(navController: NavController) {
    var searchQuery by remember { mutableStateOf("") }

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

        Button(onClick = {performSearch(searchQuery)}, modifier = Modifier.fillMaxWidth()) {
            Text("Search")
        }
    }
}

fun performSearch(searchQuery: String) {
    Log.d("SearchScreen", "Searching for: $searchQuery")
}
