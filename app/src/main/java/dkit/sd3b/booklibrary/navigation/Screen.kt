package dkit.sd3b.booklibrary.navigation

sealed class Screen(val route: String) {
    data object Help : Screen("help")
    data object Home : Screen("home")
    data object Favorites : Screen("favorites")
    data object Profile : Screen("profile")
    data object Search : Screen("search")
    data object Recommendations : Screen("recommendations")
    data object BookDetail : Screen("details/{bookId}")

    fun Screen.BookDetail.createRoute(bookId: Int): String {
        return "details/$bookId"
    }

}
