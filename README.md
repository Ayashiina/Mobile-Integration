### README

# BookSphere

**BookSphere** is a Kotlin-based Android application designed to offer both offline and online book exploration, allowing users to search, save, and browse books seamlessly. "BookSphere" reflects the app's ability to let users explore books in a limitless way, just like searching through a sphere or globe. The app allows you to find and store books locally, enabling offline access and giving you the freedom to explore books whenever and wherever you like.

---

### Features Implemented

This application incorporates a wide array of Android development techniques:

- **Composable Functions**: Intuitive and modular UI design using Jetpack Compose.
- **Logging**: Comprehensive logging for efficient debugging and monitoring.
- **Scrollable Lists with Card UI Components (Lazy)**: Smooth-scrolling lists with visually engaging card layouts.
- **Material Design**: Clean, consistent, and aesthetically pleasing UI adhering to Material Design principles.
- **Animation**: Subtle animations that enhance user engagement.
- **Architecture Components**: Robust app structure using ViewModels, LiveData, and lifecycle-aware components.
- **Navigation Between Screens**: Seamless navigation implemented with NavHostController and a sealed class (ScreenNavigation) to define and manage routes for bottom navigation.
- **Fetching Data Using Retrofit**: Integration with Google Books API for dynamic data fetching.
- **Adapting for Screen Sizes**: Fully responsive design for different screen sizes and orientations.
- **Loading Images with Coil**: Efficient image handling and caching using Coil.
- **Room Database and DataStore**: Persistent local storage for books and user preferences.

---

### Application Overview

The app consists of **six main screens**, each with unique functionality to enrich the user experience:

1. **Home Screen**:
    - Allows users to modify the search term for fetching books.
    - Includes a help icon that navigates to the **Help Screen** for guidance.

2. **Book Details Screen**:
    - Provides detailed information about a book when clicked from any list.

3. **Search Screen**:
    - Allows users to search through locally saved books in the database.

4. **Recommendations Screen**:
    - Fetches a curated list of books based on a default search term (*modern fantasy novels*).
    - Additional recommendations are loaded when scrolled to the end.

5. **Favorites Screen**:
    - Displays a list of books favorited by the user.
    - Includes options to manage and unfavorite books interactively.

6. **Profile Screen**:
    - Features a header with a **wallpaper, profile picture**, and username.
    - Displays a small bio ("About Me") where users can write about themselves.
    - Lists favorite books and genres, all of which can be edited by the user.

7. **Help Screen**:
    - Offers guidance to users who feel lost or want to learn more about the app's functionality.
    - Provides an overview of key features and instructions for each screen.

---

### Key Features

- **Offline Access**: Once a search term is loaded, users can access the corresponding books offline using the locally stored database.
- **Dynamic Book Fetching**: Books are fetched from the Google Books API. By default, the app fetches "modern fantasy novels," but users can modify the search term to their preference.
- **User Personalization**: The Profile Screen allows users to edit their profile details, bio, and favorite genres for a personalized experience.
- **Responsive Design**: All screens are scrollable and adapt seamlessly to various screen sizes and orientations.
- **User Assistance**: The Help Screen ensures users have a clear understanding of the app's features and navigation.

---

### Credits
Developed by: Aya and Eljesa  
BookSphere is a seamless blend of offline and online book exploration, providing users with a rich and personalized experience.  
