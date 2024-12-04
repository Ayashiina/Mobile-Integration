package dkit.sd3b.booklibrary.navigation

import dkit.sd3b.booklibrary.screen.HelpScreen
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dkit.sd3b.booklibrary.model.BookViewModel
import dkit.sd3b.booklibrary.screen.BookDetails
import dkit.sd3b.booklibrary.screen.RecommendationScreen
import dkit.sd3b.booklibrary.screen.FavoriteScreen
import dkit.sd3b.booklibrary.screen.HomeScreen
import dkit.sd3b.booklibrary.screen.ProfileScreen
import dkit.sd3b.booklibrary.screen.SearchScreen

@Composable
fun AppNavigator(viewModel: BookViewModel) {
    val navController = rememberNavController()
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    Scaffold(bottomBar = {
        if (currentRoute in listOf(
                ScreenNavigation.Home.route,
                ScreenNavigation.Search.route,
                ScreenNavigation.Recommendations.route,
                ScreenNavigation.Favorites.route,
                ScreenNavigation.Profile.route,
            )
        ) {
            BottomNavigationBar(navController)
        }
    }) { padding ->
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
            composable("profile") {
                ProfileScreen(navController)
            }
            composable("home") {
                HomeScreen(navController, viewModel)
            }
            composable("favorites") {
                FavoriteScreen(viewModel, navController)
            }
            composable("recommendations") {
                RecommendationScreen(viewModel, navController)
            }
            composable(
                ScreenNavigation.BookDetail.ROUTE,
                arguments = listOf(navArgument("bookId") { type = NavType.IntType })
            ) { backStackEntry ->
                val bookId = backStackEntry.arguments?.getInt("bookId") ?: return@composable
                BookDetails(
                    bookId = bookId,
                    viewModel = viewModel,
                    navController = navController
                )
            }
        }
    }
}
