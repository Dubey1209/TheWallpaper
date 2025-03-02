import com.example.appwallpaper.data.PexelsResponse
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiService {

    @Headers("0i63jXLGsOK6MR6GGRP4tqjE-42E7qm7_Je8YfNja2E")
    @GET("v1/curated")
    suspend fun getWallpapers(
        @Query("per_page") perPage: Int,
        @Query("page") page: Int
    ): PexelsResponse

    companion object {
        fun create(): ApiService {
            val client = OkHttpClient.Builder()
                .addInterceptor(Interceptor { chain ->
                    val request = chain.request().newBuilder()
                        .addHeader("Authorization", "0i63jXLGsOK6MR6GGRP4tqjE-42E7qm7_Je8YfNja2E")
                        .build()
                    chain.proceed(request)
                })
                .build()

            return Retrofit.Builder()
                .baseUrl("https://api.pexels.com/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }
}
