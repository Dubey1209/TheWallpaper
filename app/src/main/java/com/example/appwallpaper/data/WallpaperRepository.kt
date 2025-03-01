package com.example.appwallpaper.data

import com.example.appwallpaper.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WallpaperRepository {
    fun getWallpapers(): Flow<List<Wallpaper>> = flow {
        val wallpapers = listOf(
            Wallpaper("1", R.drawable.europe_nature_4k_wallpaper_desktop_backgrounds_a580),  // ✅ Use R.drawable
            Wallpaper("2", R.drawable.wallpaper),
            Wallpaper("3", R.drawable.wallpaper2),
            Wallpaper("4", R.drawable.wallpaper3)
        )
        delay(1000)  // Simulate loading time
        emit(wallpapers)
    }
}
