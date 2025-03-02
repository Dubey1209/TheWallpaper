package com.example.appwallpaper.data

import ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.util.Log

class WallpaperRepository(private val apiService: ApiService) {

    suspend fun getWallpapers(perPage: Int = 20, page: Int = 1): List<Wallpaper> {
        return try {
            val response = withContext(Dispatchers.IO) { apiService.getWallpapers(perPage, page) }

            Log.d("WallpaperRepository", "API Response: $response")

            response.photos.map { photo ->
                Wallpaper(
                    id = photo.id.toString(),
                    imageUrl = photo.src.original,
                    imageRes = null  // Ensure imageRes is handled properly
                )
            }
        } catch (e: Exception) {
            Log.e("WallpaperRepository", "API Error: ${e.message}", e)
            emptyList()
        }
    }
}
