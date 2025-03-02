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
import coil.compose.AsyncImage
import com.example.appwallpaper.viewmodel.ViewModelFactory
import com.example.appwallpaper.data.WallpaperRepository
import com.example.appwallpaper.data.remote.ApiService

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    val repository = remember { WallpaperRepository(ApiService.create()) }
    val viewModel: WallpaperViewModel = viewModel(factory = ViewModelFactory(repository))
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
                    if (wallpaper.imageRes != null) {
                        Image(
                            painter = painterResource(id = wallpaper.imageRes),
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth()
                        )
                    } else if (wallpaper.imageUrl != null) {
                        AsyncImage(
                            model = wallpaper.imageUrl,
                            contentDescription = null,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}
