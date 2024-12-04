package dkit.sd3b.booklibrary.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import dkit.sd3b.booklibrary.R
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry = navController.currentBackStackEntryAsState()

    BottomAppBar(
        modifier = Modifier.padding(8.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            BottomNavItem(
                icon = painterResource(id = R.drawable.book_education_outline),
                label = "Home",
                selected = navBackStackEntry.value?.destination?.route == ScreenNavigation.Home.route,
                onClick = {
                    navController.navigate(ScreenNavigation.Home.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
            BottomNavItem(
                icon = painterResource(id = R.drawable.book_search_outline),
                label = "Search",
                selected = navBackStackEntry.value?.destination?.route == ScreenNavigation.Search.route,
                onClick = {
                    navController.navigate(ScreenNavigation.Search.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
            BottomNavItem(
                icon = painterResource(id = R.drawable.book_spark_outline),
                label = "Recommendations",
                selected = navBackStackEntry.value?.destination?.route == ScreenNavigation.Recommendations.route,
                onClick = {
                    navController.navigate(ScreenNavigation.Recommendations.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
            BottomNavItem(
                icon = painterResource(id = R.drawable.book_heart_outline),
                label = "Favorites",
                selected = navBackStackEntry.value?.destination?.route == ScreenNavigation.Favorites.route,
                onClick = {
                    navController.navigate(ScreenNavigation.Favorites.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
            BottomNavItem(
                icon = painterResource(id = R.drawable.book_account_outline),
                label = "Profile",
                selected = navBackStackEntry.value?.destination?.route == ScreenNavigation.Profile.route,
                onClick = {
                    navController.navigate(ScreenNavigation.Profile.route) {
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun BottomNavItem(
    icon: Painter,
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
    ) {
        val iconColor =
            if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
        Icon(
            painter = icon,
            contentDescription = label,
            modifier = Modifier.size(38.dp),
            tint = iconColor
        )
    }
}
