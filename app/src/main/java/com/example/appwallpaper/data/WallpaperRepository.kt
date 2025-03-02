package com.example.appwallpaper.data

import com.example.appwallpaper.data.remote.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WallpaperRepository(private val apiService: ApiService) {

    suspend fun getWallpapers(perPage: Int = 20, page: Int = 1): List<Wallpaper> {
        return try {
            // ✅ Retrofit API call ko directly `suspend` function se call karo
            val response = apiService.getWallpapers(perPage, page)

            response.photos.map { photo ->
                Wallpaper(
                    id = photo.id.toString(),
                    imageUrl = photo.src.original,
                    imageRes = null  // ✅ Ensure imageRes is handled properly
                )
            }
        } catch (e: Exception) {
            emptyList() // ❌ API fail ho toh empty list return karo
        }
    }
}
