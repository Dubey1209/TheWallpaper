package com.example.appwallpaper.ui_screens

import android.app.WallpaperManager
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Environment
import android.provider.MediaStore
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import android.graphics.Bitmap
import java.io.FileOutputStream
import java.io.OutputStream
import androidx.core.content.FileProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WallpaperDetailScreen(imageRes: Int) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var showWallpaperOptions by remember { mutableStateOf(false) }  // Show Home/Lock/Both options
    var showApplyDialog by remember { mutableStateOf(false) }  // Show Apply & View options
    var selectedOption by remember { mutableStateOf<Int?>(null) } // Store user choice

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Wallpaper Detail") })
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            // Full-screen Image
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Wallpaper Detail",
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentScale = ContentScale.Crop
            )

            // Buttons for Download, Set Wallpaper & Share
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = { scope.launch { downloadImage(context, imageRes) } }) {
                    Text("Download")
                }
                Button(onClick = { showWallpaperOptions = true }) {
                    Text("Set Wallpaper")
                }
                Button(onClick = { shareImage(context, imageRes) }) {
                    Text("Share")
                }
            }
        }
    }

    // Dialog for Home/Lock/Both selection
    if (showWallpaperOptions) {
        AlertDialog(
            onDismissRequest = { showWallpaperOptions = false },
            title = { Text("Set Wallpaper") },
            text = { Text("Where do you want to set this wallpaper?") },
            confirmButton = {
                Column {
                    Button(onClick = { selectedOption = WallpaperManager.FLAG_SYSTEM; showWallpaperOptions = false; showApplyDialog = true }) {
                        Text("Home Screen")
                    }
                    Button(onClick = { selectedOption = WallpaperManager.FLAG_LOCK; showWallpaperOptions = false; showApplyDialog = true }) {
                        Text("Lock Screen")
                    }
                    Button(onClick = { selectedOption = WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK; showWallpaperOptions = false; showApplyDialog = true }) {
                        Text("Both")
                    }
                }
            },
            dismissButton = {
                Button(onClick = { showWallpaperOptions = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    // Dialog for Apply & View AFTER user selects Home/Lock/Both
    if (showApplyDialog) {
        AlertDialog(
            onDismissRequest = { showApplyDialog = false },
            title = { Text("Wallpaper Ready to Apply") },
            text = { Text("Would you like to apply it now?") },
            confirmButton = {
                Button(onClick = { scope.launch { selectedOption?.let { setWallpaper(context, imageRes, it) } }; showApplyDialog = false }) {
                    Text("Apply")
                }
            },
            dismissButton = {
                Button(onClick = { showApplyDialog = false }) {
                    Text("View")
                }
            }
        )
    }
}

// Function to Set Wallpaper only when Apply is clicked
suspend fun setWallpaper(context: Context, imageRes: Int, flag: Int) {
    val wallpaperManager = WallpaperManager.getInstance(context)
    val drawable = ContextCompat.getDrawable(context, imageRes) as BitmapDrawable
    val bitmap = drawable.bitmap

    withContext(Dispatchers.IO) {
        wallpaperManager.setBitmap(bitmap, null, true, flag)
    }

    withContext(Dispatchers.Main) {
        Toast.makeText(context, "Wallpaper Set!", Toast.LENGTH_SHORT).show()
    }
}

suspend fun downloadImage(context: Context, imageRes: Int) {
    val drawable = ContextCompat.getDrawable(context, imageRes) as BitmapDrawable
    val bitmap = drawable.bitmap

    val filename = "Wallpaper_${System.currentTimeMillis()}.jpg"
    val contentValues = ContentValues().apply {
        put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
        put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/Wallpapers")
    }

    val resolver = context.contentResolver
    val imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

    imageUri?.let { uri ->
        resolver.openOutputStream(uri)?.use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            Toast.makeText(context, "Image Downloaded!", Toast.LENGTH_SHORT).show()
        }
    } ?: Toast.makeText(context, "Download Failed!", Toast.LENGTH_SHORT).show()
}

fun shareImage(context: Context, imageRes: Int) {
    val drawable = ContextCompat.getDrawable(context, imageRes) as BitmapDrawable
    val bitmap = drawable.bitmap

    val file = File(context.cacheDir, "shared_wallpaper.jpg")
    val outputStream: OutputStream = FileOutputStream(file)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
    outputStream.flush()
    outputStream.close()

    val uri = FileProvider.getUriForFile(
        context,
        "${context.packageName}.provider",
        file
    )

    val shareIntent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_STREAM, uri)
        type = "image/jpeg"
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    context.startActivity(Intent.createChooser(shareIntent, "Share via"))
}
