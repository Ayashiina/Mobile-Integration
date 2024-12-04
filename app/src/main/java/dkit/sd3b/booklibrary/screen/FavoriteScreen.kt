package dkit.sd3b.booklibrary.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
            horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()
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
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = " ${book.title}", style = MaterialTheme.typography.titleSmall)
                    Text(text = " ${book.author}", style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
    }
}
