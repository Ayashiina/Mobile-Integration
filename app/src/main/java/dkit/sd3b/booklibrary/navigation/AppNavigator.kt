package dkit.sd3b.booklibrary.navigation

import androidx.compose.animation.*
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
import dkit.sd3b.booklibrary.screen.*

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
            composable(
                "help",
                content = { HelpScreen(navController) },
                enterTransition = { fadeIn() },
                exitTransition = { fadeOut() }
            )
            composable(
                "search",
                content = { SearchScreen(viewModel, navController) },
                enterTransition = { slideInHorizontally(initialOffsetX = { 300 }) },
                exitTransition = { slideOutHorizontally(targetOffsetX = { -300 }) }
            )
            composable(
                "profile",
                content = { ProfileScreen() },
                enterTransition = { fadeIn() },
                exitTransition = { fadeOut() },
            )
            composable(
                "home",
                content = { HomeScreen(navController, viewModel) },
                enterTransition = { slideInHorizontally(initialOffsetX = { -300 }) },
                exitTransition = { slideOutHorizontally(targetOffsetX = { 300 }) }
            )
            composable(
                "favorites",
                content = { FavoriteScreen(viewModel, navController) },
                enterTransition = { fadeIn() },
                exitTransition = { fadeOut() }
            )
            composable(
                "recommendations",
                content = { RecommendationScreen(viewModel, navController) },
                enterTransition = { slideInHorizontally(initialOffsetX = { 300 }) },
                exitTransition = { slideOutHorizontally(targetOffsetX = { -300 }) }
            )
            composable(
                ScreenNavigation.BookDetail.ROUTE,
                arguments = listOf(navArgument("bookId") { type = NavType.IntType }),
                content = { backStackEntry ->
                    val bookId = backStackEntry.arguments?.getInt("bookId") ?: return@composable
                    BookDetails(
                        bookId = bookId,
                        viewModel = viewModel,
                        navController = navController
                    )
                },
                enterTransition = { scaleIn(initialScale = 0.8f) + fadeIn() },
                exitTransition = { scaleOut(targetScale = 1.2f) + fadeOut() }
            )
        }
    }
}
