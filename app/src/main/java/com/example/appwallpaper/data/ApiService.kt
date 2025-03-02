package com.example.appwallpaper.data.remote

import com.example.appwallpaper.data.PexelsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v1/curated")
    suspend fun getWallpapers(
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): PexelsResponse

    companion object {
        private const val BASE_URL = "https://api.pexels.com/"

        fun create(): ApiService {
            return retrofit2.Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}
