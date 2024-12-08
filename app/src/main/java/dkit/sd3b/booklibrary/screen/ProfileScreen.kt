package dkit.sd3b.booklibrary.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import dkit.sd3b.booklibrary.ui.theme.LightPurple
import dkit.sd3b.booklibrary.ui.theme.TransparentStatusBar

@Composable
fun ProfileScreen() {
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val wallpaperHeight = screenHeight * 0.35f

    var username by remember { mutableStateOf("Nene") }
    var isEditingUsername by remember { mutableStateOf(false) }

    var aboutMe by remember { mutableStateOf("I'm an avid book lover who enjoys exploring new worlds through literature.") }
    var isEditingAboutMe by remember { mutableStateOf(false) }

    var genres by remember {
        mutableStateOf(listOf("Mystery", "Fantasy", "Non-Fiction", "Science Fiction"))
    }
    var isEditingGenres by remember { mutableStateOf(false) }

    var books by remember {
        mutableStateOf(listOf("The Hobbit", "1984", "The Great Gatsby", "Sapiens"))
    }
    var isEditingBooks by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        TransparentStatusBar()

        // Wallpaper and Profile Picture
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(wallpaperHeight)
        ) {
            // Wallpaper Image
            Image(
                painter = rememberAsyncImagePainter("https://img.freepik.com/premium-photo/panda-bear-wearing-sweater-that-says-panda-it_900370-43132.jpg"),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(wallpaperHeight)
            )

            // Profile Picture
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .offset(y = 60.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .border(width = 3.dp, color = LightPurple, shape = CircleShape)
                ) {
                    Image(
                        painter = rememberAsyncImagePainter("https://aiartshop.com/cdn/shop/files/anime-girl-reading-in-bookstore-ai-artwork-332.webp?v=1704453136"),
                        contentDescription = null,
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(top = 62.dp)
        ) {
            // Username
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (isEditingUsername) {
                        TextField(
                            value = username,
                            onValueChange = { username = it },
                            modifier = Modifier
                                .width(200.dp)
                                .padding(start = 40.dp),
                            textStyle = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp),
                            singleLine = true
                        )
                    } else {
                        Text(
                            text = username,
                            style = MaterialTheme.typography.titleLarge.copy(fontSize = 24.sp),
                            textAlign = TextAlign.Center
                        )
                    }
                    IconButton(onClick = { isEditingUsername = !isEditingUsername }) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "Edit Username"
                        )
                    }
                }
            }

            // About Me Section
            EditableField(
                label = "About Me",
                value = aboutMe,
                isEditing = isEditingAboutMe,
                onEditClick = { isEditingAboutMe = !isEditingAboutMe },
                onValueChange = { aboutMe = it },

            )

            // Favorite Genres Section
            EditableListSection(
                title = "Favorite Genres",
                items = genres,
                isEditing = isEditingGenres,
                onEditClick = { isEditingGenres = !isEditingGenres },
                onItemsChange = { genres = it }
            )

            // Favorite Books Section
            EditableListSection(
                title = "Favorite Books",
                items = books,
                isEditing = isEditingBooks,
                onEditClick = { isEditingBooks = !isEditingBooks },
                onItemsChange = { books = it }
            )
        }
    }
}

@Composable
fun EditableField(
    label: String,
    value: String,
    isEditing: Boolean,
    onEditClick: () -> Unit,
    onValueChange: (String) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.medium)
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp)
                )
                IconButton(onClick = onEditClick) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit $label"
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            if (isEditing) {
                TextField(
                    value = value,
                    onValueChange = onValueChange,
                    modifier = Modifier.fillMaxWidth(),
                    textStyle = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp)
                )
            } else {
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp),
                )
            }
        }
    }
}

@Composable
fun EditableListSection(
    title: String,
    items: List<String>,
    isEditing: Boolean,
    onEditClick: () -> Unit,
    onItemsChange: (List<String>) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.medium)
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 20.sp)
                )
                IconButton(onClick = onEditClick) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit $title"
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))

            if (isEditing) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    items.forEachIndexed { index, item ->
                        TextField(
                            value = item,
                            onValueChange = { newItem ->
                                onItemsChange(items.toMutableList().apply { set(index, newItem) })
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            } else {
                items.forEach { item ->
                    Text(
                        text = "• $item",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 18.sp),
                    )
                }
            }
        }
    }
}