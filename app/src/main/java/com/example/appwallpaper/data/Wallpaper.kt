package com.example.appwallpaper.data

data class Wallpaper(
    val id: String,
    val imageUrl: String? = null,  // ✅ API images ke liye
    val imageRes: Int? = null      // ✅ Local images ke liye
)
