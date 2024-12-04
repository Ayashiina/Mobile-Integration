package dkit.sd3b.booklibrary.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import dkit.sd3b.booklibrary.model.BookViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookDetails(bookId: Int, navController: NavController, viewModel: BookViewModel) {
    val selectedBook by viewModel.selectedBook.observeAsState()
    var isFavorite by remember { mutableStateOf(false) }

    LaunchedEffect(bookId) {
        viewModel.fetchBookById(bookId)
    }

    selectedBook?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Top Bar with Back Button and Title
            TopAppBar(title = {
                Text(
                    text = it.title.toString(), style = MaterialTheme.typography.headlineSmall
                )
            }, navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }, modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Book Thumbnail
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = rememberAsyncImagePainter(it.imageUrl),
                    contentDescription = "Book Thumbnail",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                )
                IconButton(
                    onClick = {
                        isFavorite = !isFavorite
                        if (isFavorite) {
                            viewModel.addToFavorites(it)
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .offset(y = 16.dp)
                ) {
                    Icon(
                        imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Favorite",
                        tint = if (isFavorite) Color.Red else Color.Gray
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Book Details Section
            Column(modifier = Modifier.fillMaxWidth()) {
                // Title
                Text(
                    text = it.title.toString(),
                    style = MaterialTheme.typography.headlineSmall.copy(fontSize = 28.sp),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Author
                Text(
                    text = it.author ?: "Unknown Author",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Release Year
                Text(
                    text = "Released in: ${it.releaseYear}",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Description
                Text(
                    text = it.description ?: "No description available.",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Justify
                )
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    } ?: run {
        Column(modifier = Modifier.padding(16.dp)) {
            Text("Book not found", style = MaterialTheme.typography.headlineSmall)
            Button(onClick = { navController.popBackStack() }) {
                Text("Go Back")
            }
        }
    }
}
