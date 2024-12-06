package dkit.sd3b.booklibrary.screen

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.getDrawable
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import dkit.sd3b.booklibrary.R
import dkit.sd3b.booklibrary.model.Book
import dkit.sd3b.booklibrary.model.BookViewModel
import dkit.sd3b.booklibrary.navigation.ScreenNavigation

@Composable
fun FavoriteScreen(viewModel: BookViewModel, navController: NavController) {
    val favoriteBooks by viewModel.favoriteBooks.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.fetchFavoriteBooks()
        Log.d("BookLog-Favorite", "LaunchedEffect triggered")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "You have ${favoriteBooks.size} favorite books",
            style = MaterialTheme.typography.headlineMedium.copy(fontSize = 18.sp),
            modifier = Modifier
                .padding(bottom = 16.dp)
                .align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center
        )

        if (favoriteBooks.isEmpty()) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                Image(
                    painter = rememberDrawablePainter(
                        drawable = getDrawable(
                            LocalContext.current, R.drawable.favorite_animation
                        ),
                    ),
                    contentDescription = "Favorites Animation",
                )
                Text(
                    text = "No favorites yet",
                    style = TextStyle(fontSize = 20.sp, textAlign = TextAlign.Center),
                )
                Button(
                    onClick = {
                        navController.navigate(ScreenNavigation.Recommendations.route)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    ),
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Add Now",
                        color = Color.White
                    )
                }
            }
        } else {
            LazyColumn {
                items(favoriteBooks) { book ->
                    FavoriteBookItem(book, viewModel, navController)
                }
            }
        }
    }
}

@Composable
fun FavoriteBookItem(book: Book, viewModel: BookViewModel, navController: NavController) {
    var isFavorite by remember { mutableStateOf(true) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier
                .clickable {
                    navController.navigate(ScreenNavigation.BookDetail.createRoute(book.id))
                }
                .weight(1f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Book Thumbnail
            Image(
                painter = rememberAsyncImagePainter(model = book.thumbnail),
                contentDescription = "Book Thumbnail",
                modifier = Modifier.size(50.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            // Book Title and Author
            Column {
                Text(
                    text = book.title ?: "Unknown Title",
                    style = MaterialTheme.typography.bodyLarge
                )
                Text(
                    text = book.author ?: "Unknown Author",
                    style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray)
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Favorite Icon
        Icon(
            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
            contentDescription = "Favorite Icon",
            tint = if (isFavorite) Color.Red else Color.Gray,
            modifier = Modifier
                .size(30.dp)
                .clickable {
                    isFavorite = !isFavorite
                    if (isFavorite) {
                        viewModel.addToFavorites(book.id, true)
                    } else {
                        viewModel.addToFavorites(book.id, false)
                    }
                }
        )
    }
}