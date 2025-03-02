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
//import com.example.appwallpaper.data.ApiService

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
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            if (wallpapers.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Loading wallpapers, please wait...", style = MaterialTheme.typography.bodyMedium)
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.fillMaxSize().padding(8.dp),
                    contentPadding = PaddingValues(8.dp)
                ) {
                    items(wallpapers) { wallpaper ->
                        Box(modifier = Modifier.fillMaxWidth().padding(4.dp)) {
                            if (wallpaper.imageRes != null) {
                                Image(
                                    painter = painterResource(id = wallpaper.imageRes),
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxWidth().clickable {
                                        navController.navigate("wallpaperDetail/${wallpaper.id}")
                                    }
                                )
                            } else if (wallpaper.imageUrl != null) {
                                AsyncImage(
                                    model = wallpaper.imageUrl,
                                    contentDescription = null,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxWidth().clickable {
                                        navController.navigate("wallpaperDetail/${wallpaper.id}")
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
