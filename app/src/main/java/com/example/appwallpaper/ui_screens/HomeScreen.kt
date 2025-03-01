package com.example.appwallpaper.ui_screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.appwallpaper.viewmodel.WallpaperViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val viewModel: WallpaperViewModel = viewModel()
    val wallpapers by viewModel.wallpapers.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Wallpapers") })
        }
    ) { padding ->
        if (wallpapers.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2), // 2 images per row
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(wallpapers) { wallpaper ->
                    Image(
                        painter = painterResource(id = wallpaper.imageRes),  // ✅ Use local image
                        contentDescription = "Wallpaper",
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f) // Ensures square images
                            .padding(8.dp)
                            .clickable {
                                navController.navigate("wallpaperDetail/${wallpaper.imageRes}")
                            },
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}
