package dkit.sd3b.booklibrary.screen

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.HelpOutline
import androidx.compose.material.icons.filled.Edit
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
fun HomeScreen(navController: NavController, viewModel: BookViewModel) {
    val books by viewModel.books.observeAsState(emptyList())
    val categories by viewModel.genres.observeAsState(emptyList())

    var showDialog by remember { mutableStateOf(false) }
    var queryText by remember { mutableStateOf("recommendations") }


    LaunchedEffect(Unit) {
        viewModel.fetchBooksFromDatabase()
    }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {
        Header(navController)
        Spacer(modifier = Modifier.height(20.dp))

        // Categories Section
        if (categories.isNotEmpty()) {
            Text(
                "Browse by Categories", style = TextStyle(fontSize = 22.sp, color = Color.Black)
            )
            LazyRow(modifier = Modifier.fillMaxWidth()) {
                items(categories) { category ->
                    CategoryItem(category)
                }
            }
        }

        // Spacer between sections
        Spacer(modifier = Modifier.height(20.dp))

        // Recommended Books Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Recommended Books",
                style = TextStyle(fontSize = 22.sp, color = Color.Black),
                modifier = Modifier.weight(1f)
            )

            IconButton(
                onClick = {
                    showDialog = true
                },
                modifier = Modifier
                    .size(40.dp)
                    .animateContentSize()
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Set New Query",
                    tint = Color.Black
                )
            }

            if (showDialog) {
                NewQueryDialog(
                    onDismiss = { showDialog = false },
                    onQueryChanged = { newQuery ->
                        queryText = newQuery
                    },
                    onSubmit = {
                        viewModel.setNewQueryAndRefresh(queryText)
                        showDialog = false
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        BookGrid(books.take(6)) { book ->
            navController.navigate(ScreenNavigation.BookDetail.createRoute(book))
        }
        TextButton(
            onClick = { navController.navigate(ScreenNavigation.Recommendations.route) },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Text(
                "See More",
                style = TextStyle(textAlign = TextAlign.Center, fontSize = 15.sp)

            )
        }
    }
}

@Composable
fun BookGrid(books: List<Book>, onBookClick: (Int) -> Unit) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 160.dp),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.heightIn(max = 500.dp)
    ) {
        items(books) { book ->
            BookItem(book = book, onClick = {
                onBookClick(book.id)
                Log.d("BookLog", "Book ID clicked: ${book.id}")
            })
        }
    }
}

@Composable
fun Header(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = rememberDrawablePainter(
                drawable = getDrawable(
                    LocalContext.current, R.drawable.home_animation
                ),
            ),
            contentDescription = "Library Animation",
        )
        Text(
            text = "Welcome to Book Sphere!",
            style = TextStyle(fontSize = 20.sp, textAlign = TextAlign.Center),
            modifier = Modifier.align(Alignment.BottomCenter)
        )
        IconButton(
            onClick = { navController.navigate(ScreenNavigation.Help.route) },
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Outlined.HelpOutline,
                contentDescription = "Help",
                modifier = Modifier.size(25.dp),
                tint = Color.Black
            )
        }
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
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(8.dp),
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

@Composable
fun NewQueryDialog(onDismiss: () -> Unit, onQueryChanged: (String) -> Unit, onSubmit: () -> Unit) {
    var textFieldValue by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Set a New Query") },
        text = {
            Column {
                Text("Enter your desired query below:")
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = textFieldValue,
                    onValueChange = {
                        textFieldValue = it
                        onQueryChanged(it)
                    },
                    label = { Text("New Query") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onSubmit()
                }
            ) {
                Text("Submit")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismiss()
                }
            ) {
                Text("Cancel")
            }
        }
    )
}
