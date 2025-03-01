package com.example.appwallpaper.navigation

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.appwallpaper.ui_screens.HomeScreen
import com.example.appwallpaper.ui_screens.WallpaperDetailScreen

@SuppressLint("SuspiciousIndentation")
@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {

    NavHost(
        navController = navController,
        startDestination = Routes.HomeScreen.route,
        modifier = modifier
    ) {
        composable(Routes.HomeScreen.route) {
            HomeScreen(navController)
        }


        composable("wallpaperDetail/{imageRes}") { backStackEntry ->
            val imageRes = backStackEntry.arguments?.getString("imageRes")?.toIntOrNull() ?: 0
            WallpaperDetailScreen(imageRes)
        }
    }
}
