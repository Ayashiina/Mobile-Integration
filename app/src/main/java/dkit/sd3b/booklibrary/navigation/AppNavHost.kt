package dkit.sd3b.booklibrary.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dkit.sd3b.booklibrary.model.BookViewModel
import dkit.sd3b.booklibrary.screen.BookDetails
import dkit.sd3b.booklibrary.screen.BookList

@Composable
fun NavHost(navController: NavHostController, viewModel: BookViewModel) {
    LaunchedEffect(Unit) {
        viewModel.fetchBooks()
    }

    NavHost(navController, startDestination = "bookList") {
        composable("bookList") {
            BookList(viewModel = viewModel, navController = navController)
        }
        composable("bookDetails/{bookId}") { backStackEntry ->
            val bookId = backStackEntry.arguments?.getString("bookId")?.toIntOrNull()
            bookId?.let { id ->
                BookDetails(
                    bookId = id,
                    fetchBook = { viewModel.getBookById(it) },
                    onAddToListClick = { viewModel.addToReadingList(it) }
                )
            } ?: Text("Invalid Book ID", modifier = Modifier.padding(16.dp))
        }
    }
}
