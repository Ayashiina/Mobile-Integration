package dkit.sd3b.booklibrary.screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import dkit.sd3b.booklibrary.navigation.Screen
import dkit.sd3b.booklibrary.navigation.Screen.BookDetail.createRoute
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(navController: NavController, viewModel: BookViewModel) {
    val books by viewModel.books.observeAsState(emptyList())
    val categories by viewModel.genres.observeAsState(emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        AnimatedHeader()

        Spacer(modifier = Modifier.height(20.dp))

        // Categories Section
        if (categories.isNotEmpty()) {
            Text("Browse by Categories", style = TextStyle(fontSize = 22.sp, color = Color.Black))
            LazyRow(modifier = Modifier.fillMaxWidth()) {
                items(categories) { category ->
                    CategoryItem(category)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Popular Books Section
        Text("Popular Books", style = TextStyle(fontSize = 22.sp, color = Color.Black))
        LazyRow(modifier = Modifier.fillMaxWidth()) {
            items(books) { book ->
                BookItem(book = book, onClick = {
                    navController.navigate(Screen.BookDetail.createRoute(book.id))
                })
            }
        }
    }
}

@Composable
fun AnimatedHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(230.dp), contentAlignment = Alignment.BottomEnd
    ) {
        Image(
            painter = rememberDrawablePainter(
                drawable = getDrawable(
                    LocalContext.current, R.drawable.home_animation
                ),
            ), contentDescription = "Library Icon", modifier = Modifier.size(300.dp)
        )
        Text(
            text = "Welcome to Book Library!",
            style = TextStyle(fontSize = 20.sp, textAlign = TextAlign.Center),
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

@Composable
fun CategoryItem(category: String) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .animateContentSize()
            .clickable { /* Action */ },
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .background(MaterialTheme.colorScheme.secondaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Text(
                category, style = TextStyle(
                    fontSize = 16.sp, color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            )
        }
    }
}

@Composable
fun BookItem(book: Book, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(150.dp)
            .height(220.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(book.imageUrl),
                contentDescription = book.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = book.title ?: "Unknown Title",
                style = TextStyle(fontSize = 16.sp, color = Color.Black),
                maxLines = 1
            )
            Text(
                text = book.author ?: "Unknown Author",
                style = TextStyle(fontSize = 12.sp, color = Color.Gray),
                maxLines = 1
            )
        }
    }
}
