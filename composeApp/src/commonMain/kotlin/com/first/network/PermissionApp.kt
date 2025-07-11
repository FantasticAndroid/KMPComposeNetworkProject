package com.first.network

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.input.key.Key.Companion.R
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import coil3.ImageLoader
import coil3.compose.AsyncImage
import coil3.compose.rememberConstraintsSizeResolver
import coil3.compose.setSingletonImageLoaderFactory
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.mohamedrejeb.calf.camerapicker.rememberCameraPickerLauncher
import com.mohamedrejeb.calf.core.LocalPlatformContext
import com.mohamedrejeb.calf.io.KmpFile
import com.mohamedrejeb.calf.io.exists
import com.mohamedrejeb.calf.io.readByteArray
import com.mohamedrejeb.calf.permissions.ExperimentalPermissionsApi
import com.mohamedrejeb.calf.permissions.Permission
import com.mohamedrejeb.calf.permissions.PermissionStatus
import com.mohamedrejeb.calf.permissions.isDenied
import com.mohamedrejeb.calf.permissions.isGranted
import com.mohamedrejeb.calf.permissions.rememberPermissionState
import com.mohamedrejeb.calf.permissions.shouldShowRationale
import com.mohamedrejeb.calf.picker.FilePickerFileType
import com.mohamedrejeb.calf.picker.FilePickerSelectionMode
import com.mohamedrejeb.calf.picker.coil.KmpFileFetcher
import com.mohamedrejeb.calf.picker.rememberFilePickerLauncher
import firstnetworkproject.composeapp.generated.resources.Res
import firstnetworkproject.composeapp.generated.resources.compose_multiplatform
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonNull.content
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@OptIn(ExperimentalPermissionsApi::class)
@Composable
@Preview
fun PermissionApp() {

    MaterialTheme {

        var selectedFile: KmpFile? by remember { mutableStateOf(null) }
        val settings = remember { SettingsLauncher() }
        settings.open()

        // For multiple permission together
        // val requiredPermissions = rememberMultiplePermissionsState(listOf(Permission.Camera, Permission.Gallery))

        val cameraPermission = rememberPermissionState(Permission.Camera)
        val galleryPermission = rememberPermissionState(Permission.Gallery)

        val cameraPicker  = rememberCameraPickerLauncher { selectedFile = it }
        val galleryPicker = rememberFilePickerLauncher(type = FilePickerFileType.Image, selectionMode = FilePickerSelectionMode.Single) {
            selectedFile = it.firstOrNull()
        }

        val scope = rememberCoroutineScope()
        // Read if any previous censor text available in datastore (Datastore read)
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
            Button(onClick = {
                Logger.withTag("PermissionApp").d("Camera Clicked: PStatus: ${cameraPermission.status}")

                cameraPicker.launch()
                return@Button

                if(cameraPermission.status.isGranted){
                    cameraPicker.launch()
                }else if(cameraPermission.status.isDenied && !cameraPermission.status.shouldShowRationale){
                    cameraPermission.launchPermissionRequest()
                } else if(cameraPermission.status.isDenied && cameraPermission.status.shouldShowRationale){
                    settings.Handle()
                }else{
                    cameraPermission.launchPermissionRequest()
                }
            }) {
                val text = when (cameraPermission.status){
                    is PermissionStatus.Denied -> "Permission Denied: CAMERA"
                    PermissionStatus.Granted -> "Permission Granted: CAMERA"
                }
                Text(text)
            }
            Button(onClick = {
                Logger.withTag("PermissionApp").d("Gallery Clicked: PStatus: ${cameraPermission.status}")
                if(galleryPermission.status.isGranted){
                    galleryPicker.launch()
                }else if(galleryPermission.status.shouldShowRationale){
                    galleryPermission.launchPermissionRequest()
                } else if(galleryPermission.status is PermissionStatus.Denied && !galleryPermission.status.shouldShowRationale){
                    settings.Handle()
                }else{
                    galleryPermission.launchPermissionRequest()
                }
            }) {
                val text = when (galleryPermission.status){
                    is PermissionStatus.Denied -> "Permission Denied: GALLERY"
                    PermissionStatus.Granted -> "Permission Granted: GALLERY"
                }
                Text(text)
            }

            setSingletonImageLoaderFactory { context ->
                ImageLoader.Builder(context)
                    .components {
                        //add(KtorNetworkFetcherFactory())   // for network support
                        add(KmpFileFetcher.Factory())      // for KmpFile support
                    }
                    .build()
            }

            Spacer(Modifier.height(8.dp))
            selectedFile?.let { file ->
                Logger.withTag("PermissionApp").d("file: $file")
                AsyncImage(
                    model = file,  // KmpFile handled automatically via Calf Coil KMP integration

                    /* OR more granular
                    model = ImageRequest.Builder(context)
                        .data(file)  // Pass the KmpFile here
                        .crossfade(true)
                        .build(),*/

                    placeholder = painterResource(Res.drawable.compose_multiplatform),
                    error = painterResource(Res.drawable.compose_multiplatform),

                    contentDescription = "Selected image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}