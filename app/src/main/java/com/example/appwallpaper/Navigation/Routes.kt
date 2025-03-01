package com.example.appwallpaper.navigation

sealed class Routes(val route: String) {

    object HomeScreen : Routes("home")
    object WallpaperDetailScreen : Routes("wallpaperDetail/{imageUrl}")

}
