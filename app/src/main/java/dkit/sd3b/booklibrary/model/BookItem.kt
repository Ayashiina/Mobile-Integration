package dkit.sd3b.booklibrary.model

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BookItem(
    book: Book,
    onAddToList: (() -> Unit)? = null,
    onMarkFavorite: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(book.title ?: "Unknown Title", style = TextStyle(fontSize = 18.sp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(book.author ?: "Unknown Author", style = TextStyle(fontSize = 14.sp))
            Spacer(modifier = Modifier.height(16.dp))

            if (onAddToList != null || onMarkFavorite != null) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    onAddToList?.let {
                        Button(onClick = it) {
                            Text("Add to List")
                        }
                    }
                    onMarkFavorite?.let {
                        Button(onClick = it) {
                            Text("Mark as Favorite")
                        }
                    }
                }
            }
        }
    }
}
