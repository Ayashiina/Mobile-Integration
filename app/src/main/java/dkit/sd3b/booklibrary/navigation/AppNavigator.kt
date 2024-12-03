package dkit.sd3b.booklibrary.navigation

import dkit.sd3b.booklibrary.screen.HelpScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dkit.sd3b.booklibrary.model.BookViewModel
import dkit.sd3b.booklibrary.screen.BookDetails
import dkit.sd3b.booklibrary.screen.FavoriteScreen
import dkit.sd3b.booklibrary.screen.HomeScreen
import dkit.sd3b.booklibrary.screen.ProfileScreen
import dkit.sd3b.booklibrary.screen.RecommendationScreen
import dkit.sd3b.booklibrary.screen.SearchScreen

@Composable
fun AppNavigator(viewModel: BookViewModel) {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute in listOf(Screen.Home.route, Screen.Favorites.route)) {
                BottomNavigationBar(navController)
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "home",
            modifier = Modifier.padding(padding)
        ) {
            composable("help") {
                HelpScreen(navController)
            }
            composable("search") {
                SearchScreen(navController)
            }
            composable("recommendations") {
                RecommendationScreen(navController)
            }
            composable("profile") {
                ProfileScreen(navController)
            }
            composable("home") {
                HomeScreen(navController,viewModel)
            }
            composable("favorites") {
                FavoriteScreen(viewModel, navController)
            }
            composable("details/{bookId}") { backStackEntry ->
                val bookId = backStackEntry.arguments?.getString("bookId")?.toInt() ?: 0
                BookDetails(
                    bookId = bookId,
                    fetchBook = { id -> viewModel.getBookById(id) },
                    onAddToListClick = { id -> viewModel.addToList(id) },
                    navController = navController
                )
            }
        }
    }
}
