package dkit.sd3b.booklibrary.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.getDrawable
import androidx.navigation.NavController
import com.google.accompanist.drawablepainter.rememberDrawablePainter
import dkit.sd3b.booklibrary.R
import dkit.sd3b.booklibrary.navigation.Screen

@Composable
fun HelpScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Animation()

        SectionCard(
            title = "What is Book Library?",
            description = "Book Library helps you discover, organize, and enjoy books. Browse popular genres, save favorites, and get personalized recommendations."
        )

        Spacer(modifier = Modifier.height(16.dp))

        SectionCard(
            title = "How to Use the App",
            description = """
                1. Use the Home screen to explore books.
                2. Search for specific titles or authors.
                3. Add books to your favorites for easy access.
                4. Check out personalized recommendations.
            """.trimIndent()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Tips Section
        SectionCard(
            title = "Tips & Tricks",
            description = "Swipe left or right on book covers to quickly add them to favorites or remove them. Try the recommendation feature for hidden gems!"
        )

        Spacer(modifier = Modifier.height(24.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            androidx.compose.material3.Button(
                onClick = { navController.navigate(Screen.Home.route) }
            ) {
                Text("Got It!")
            }
        }
    }
}

@Composable
fun Animation() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = rememberDrawablePainter(
                drawable = getDrawable(
                    LocalContext.current,
                    R.drawable.book_animation
                ),
            ),
            contentDescription = "Help Icon",
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
fun SectionCard(title: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = androidx.compose.material3.CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = title,
                style = androidx.compose.material3.MaterialTheme.typography.titleMedium,
                color = androidx.compose.material3.MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                style = androidx.compose.material3.MaterialTheme.typography.bodyMedium,
                color = androidx.compose.material3.MaterialTheme.colorScheme.onBackground
            )
        }
    }
}
