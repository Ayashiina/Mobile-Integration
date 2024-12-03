package dkit.sd3b.booklibrary.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ProfileScreen(navController: NavController) {
    val username by remember { mutableStateOf("John Doe") }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("User Profile", style = TextStyle(fontSize = 24.sp))

        Spacer(modifier = Modifier.height(16.dp))

        Text("Username: $username", style = TextStyle(fontSize = 18.sp))

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = { /* Logout functionality */ }) {
            Text("Logout")
        }
    }
}
