package com.example.appwallpaper.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.appwallpaper.data.Wallpaper
import com.example.appwallpaper.data.WallpaperRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

class WallpaperViewModel(private val repository: WallpaperRepository) : ViewModel() {

    private val _wallpapers = MutableStateFlow<List<Wallpaper>>(emptyList())
    val wallpapers: StateFlow<List<Wallpaper>> = _wallpapers

    init {
        fetchWallpapers()
    }

    private fun fetchWallpapers() {
        viewModelScope.launch {
            repeat(5) {
                val fetchedWallpapers = repository.getWallpapers()
                if (fetchedWallpapers.isNotEmpty()) {
                    _wallpapers.value = fetchedWallpapers
                    return@launch
                }
                delay(4000)
            }
        }
    }
}
