package dkit.sd3b.booklibrary.navigation

sealed class ScreenNavigation(val route: String) {
    data object Help : ScreenNavigation("help")
    data object Home : ScreenNavigation("home")
    data object Favorites : ScreenNavigation("favorites")
    data object Profile : ScreenNavigation("profile")
    data object Search : ScreenNavigation("search")
    data object Recommendations : ScreenNavigation("recommendations")
    data object BookDetail {
        const val ROUTE = "bookDetail/{bookId}"
        fun createRoute(bookId: Int): String = "bookDetail/$bookId"
    }

}
