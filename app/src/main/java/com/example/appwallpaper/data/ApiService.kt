package com.example.appwallpaper.data.remote

import com.example.appwallpaper.data.PexelsResponse  // ✅ IMPORT ADDED
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {
    @Headers("0i63jXLGsOK6MR6GGRP4tqjE-42E7qm7_Je8YfNja2E")  // 🔥 Yahan apni API key paste karo
    @GET("v1/curated")  // ✅ Pexels API se curated wallpapers lene ke liye
    fun getWallpapers(
        @Query("per_page") perPage: Int = 20,
        @Query("page") page: Int = 1
    ): Call<PexelsResponse>  // ✅ Ensure PexelsResponse is correctly used
}
