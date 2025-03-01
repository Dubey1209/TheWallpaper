package com.example.appwallpaper.viewmodel

import androidx.lifecycle.ViewModel
import com.example.appwallpaper.R
import com.example.appwallpaper.data.Wallpaper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class WallpaperViewModel : ViewModel() {

    private val _wallpapers = MutableStateFlow(
        listOf(
            Wallpaper(id = "1", imageRes = R.drawable.europe_nature_4k_wallpaper_desktop_backgrounds_a580),
            Wallpaper(id = "2", imageRes = R.drawable.wallpaper2),
            Wallpaper(id = "3", imageRes = R.drawable.wallpaper3),
            Wallpaper(id = "4", imageRes = R.drawable.wallpaper2),
            Wallpaper(id = "5", imageRes = R.drawable.wallpaper)
        )
    )

    val wallpapers: StateFlow<List<Wallpaper>> = _wallpapers
}
