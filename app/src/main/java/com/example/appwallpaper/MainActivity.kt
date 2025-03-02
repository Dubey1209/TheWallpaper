package com.example.appwallpaper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.appwallpaper.navigation.NavGraph
import com.example.appwallpaper.ui.theme.AppWallpaperTheme
import com.skydoves.landscapist.glide.GlideImage

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppWallpaperTheme {
                val navController = rememberNavController()

                Scaffold { innerPadding ->
                    NavGraph(navController = navController, modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
