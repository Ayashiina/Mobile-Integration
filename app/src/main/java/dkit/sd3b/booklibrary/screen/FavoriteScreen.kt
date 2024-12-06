package dkit.sd3b.booklibrary.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import coil.compose.rememberImagePainter
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import dkit.sd3b.booklibrary.R
import dkit.sd3b.booklibrary.model.BookViewModel
import dkit.sd3b.booklibrary.navigation.ScreenNavigation

@Composable

fun FavoriteScreen(viewModel: BookViewModel, navController: NavController) {
    val favoriteBooks by viewModel.favoriteBooks.observeAsState(emptyList())

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
                }, colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                ), modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Add Now", color = Color.White
                )
            }
        }
    } else {
        LazyColumn {
            items(favoriteBooks) { book ->
                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = rememberImagePainter(data = book.thumbnailUrl),
                        contentDescription = "Book Thumbnail",
                        modifier = Modifier.size(50.dp)
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = book.title, style = MaterialTheme.typography.titleSmall)//check
                        Text(text = book.author, style = MaterialTheme.typography.bodyLarge)//check
                    }
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favorite Icon",
                        tint = Color.Red
                    )
                }
            }
        }
    }