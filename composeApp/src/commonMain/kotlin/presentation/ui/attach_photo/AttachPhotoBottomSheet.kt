package presentation.ui.attach_photo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import domain.add_address.Data
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import mykmptest.composeapp.generated.resources.Res
import mykmptest.composeapp.generated.resources.attach_content
import mykmptest.composeapp.generated.resources.ic_attach_camera
import mykmptest.composeapp.generated.resources.ic_back
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import presentation.ui.attach_photo.platform.PermissionCallback
import presentation.ui.attach_photo.platform.PermissionStatus
import presentation.ui.attach_photo.platform.PermissionType
import presentation.ui.attach_photo.platform.createPermissionsManager
import presentation.ui.attach_photo.platform.rememberCameraManager
import presentation.ui.attach_photo.platform.rememberGalleryManager
import util.ColorCustomResources

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttachPhotoBottomSheet(
    address: String,
    dataAddress: Data?,
    navigationFrom: String,
    openBottomSheet: (Boolean) -> Unit
) {
    val openBottomSheetState by rememberSaveable { mutableStateOf(true) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val lazyListState = rememberLazyListState()
    val isScrollToLastIndex = remember { mutableStateOf(false) }
    val interactionSource = remember { MutableInteractionSource() }
    val inputTextPhoneNumber = remember { mutableStateOf("") }
    val isEnableAttachSendButton = remember { mutableStateOf(false) }


    if (openBottomSheetState) {
        ModalBottomSheet(
            modifier = Modifier
                .fillMaxWidth(),
//            containerColor = Color.White, не
//            contentColor = Color.White,
            onDismissRequest = { openBottomSheet(false) },
            sheetState = bottomSheetState,
            dragHandle = {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//
//                        .background(Color.White),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    BottomSheetDefaults.DragHandle()
//                }
            },
            shape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp
            )
        ) {
            Column(
                modifier = Modifier

                    .fillMaxSize()
                    .background(Color.White)
                    .padding(16.dp)
            ) {

                LazyColumn(
                    state = lazyListState
                ) {
                    item {
                        AttachTopTitle(
                            openBottomSheet = {

                            }
                        )
                        AttachAddressTitle(address = address)
                        AttachAvailableServicesContent()
                        AttachContent()
                        AttachPhoto(
                            onAttachPhoto = {
                                isScrollToLastIndex.value = it
                                isEnableAttachSendButton.value = it
                            }
                        )
                    }
                    item {
                        AttachSendButton(
                            isEnable = isEnableAttachSendButton.value
                        )
                    }
                }
            }
        }

        LaunchedEffect(isScrollToLastIndex.value) {
            if (isScrollToLastIndex.value) {
                delay(100)
                lazyListState.animateScrollToItem(1)
            }
        }
    }
}

@Composable
fun AttachTopTitle(
    openBottomSheet: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        //horizontalArrangement = Arrangement.SpaceBetween
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth(),

                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Card(
                    modifier = Modifier
                        .size(34.dp),
                    //    .align(Alignment.CenterEnd)
                    shape = RoundedCornerShape(5.dp),
                    colors = CardDefaults.cardColors(containerColor = ColorCustomResources.colorBackgroundClose),
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                openBottomSheet(false)
                            },
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            // close
                            modifier = Modifier
                                .size(24.dp),
                            imageVector = vectorResource(Res.drawable.ic_back),
                            contentDescription = null,
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Добавление адреса",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun AttachAddressTitle(address: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = address,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun AttachAvailableServicesContent() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "Доступные услуги на этом адресе:",
                color = Color.Black
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {

            Box(
                modifier = Modifier
                    .size(50.dp),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    // close
                    modifier = Modifier
                        .size(24.dp),
                    imageVector = vectorResource(Res.drawable.ic_back),
                    contentDescription = null,
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Интернет и ТВ",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Больше 250 каналов",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.LightGray)
        )

        ///////////////////////////

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {

            Box(
                modifier = Modifier
                    .size(50.dp),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    // close
                    modifier = Modifier
                        .size(24.dp),
                    imageVector = vectorResource(Res.drawable.ic_back),
                    contentDescription = null,
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Видеонаблюдение в моем доме",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Х камер",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.LightGray)
        )

        ////////////////////////
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {

            Box(
                modifier = Modifier
                    .size(50.dp),
                contentAlignment = Alignment.Center,
            ) {
                Icon(
                    // close
                    modifier = Modifier
                        .size(24.dp),
                    imageVector = vectorResource(Res.drawable.ic_back),
                    contentDescription = null,
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "Умный домофон",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(
                        text = "X камер",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.LightGray)
        )
    }
}

@Composable
fun AttachContent() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Text(
            text = stringResource(Res.string.attach_content),
            color = Color.Black
        )
    }
}

@Composable
fun AttachPhoto(
    onAttachPhoto: (Boolean) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    var imageSourceOptionDialog by remember { mutableStateOf(value = false) }
    var launchCamera by remember { mutableStateOf(value = false) }
    var launchGallery by remember { mutableStateOf(value = false) }
    var launchSetting by remember { mutableStateOf(value = false) }
    var permissionRationalDialog by remember { mutableStateOf(value = false) }
    val permissionsManager = createPermissionsManager(object : PermissionCallback {
        override fun onPermissionStatus(
            permissionType: PermissionType,
            status: PermissionStatus
        ) {
            when (status) {
                PermissionStatus.GRANTED -> {
                    when (permissionType) {
                        PermissionType.CAMERA -> launchCamera = true
                        PermissionType.GALLERY -> launchGallery = true
                    }
                }

                else -> {
                    permissionRationalDialog = true
                }
            }
        }


    })

    val cameraManager = rememberCameraManager {
        coroutineScope.launch {
            val bitmap = withContext(Dispatchers.Default) {
                it?.toImageBitmap()
            }
            imageBitmap = bitmap
        }
    }

    val galleryManager = rememberGalleryManager {
        coroutineScope.launch {
            val bitmap = withContext(Dispatchers.Default) {
                it?.toImageBitmap()
            }
            imageBitmap = bitmap
        }
    }
    if (imageSourceOptionDialog) {
        ImageSourceOptionDialog(onDismissRequest = {
            imageSourceOptionDialog = false
        }, onGalleryRequest = {
            imageSourceOptionDialog = false
            launchGallery = true
        }, onCameraRequest = {
            imageSourceOptionDialog = false
            launchCamera = true
        })
    }
    if (launchGallery) {
        if (permissionsManager.isPermissionGranted(PermissionType.GALLERY)) {
            galleryManager.launch()
        } else {
            permissionsManager.askPermission(PermissionType.GALLERY)
        }
        launchGallery = false
    }
    if (launchCamera) {
        if (permissionsManager.isPermissionGranted(PermissionType.CAMERA)) {
            cameraManager.launch()
        } else {
            permissionsManager.askPermission(PermissionType.CAMERA)
        }
        launchCamera = false
    }
    if (launchSetting) {
        permissionsManager.launchSettings()
        launchSetting = false
    }
    if (permissionRationalDialog) {
        AlertMessageDialog(
            onPositiveClick = {
                permissionRationalDialog = false
                launchSetting = true
            },
            onNegativeClick = {
                permissionRationalDialog = false
            }
        )
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                imageSourceOptionDialog = true
            }
            .heightIn(min = 100.dp),
        colors = CardDefaults.cardColors(containerColor = ColorCustomResources.colorBackgroundClose),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            imageBitmap?.let {
                Image(
                    bitmap = it, // прикрепленное изображение документа
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(min = 100.dp), // Минимальная высота, чтобы гарантировать что изображение не будет слишком маленьким
                    contentScale = ContentScale.FillWidth // Растягивает изображение по ширине контейнера, сохраняя соотношение сторон
                )
                onAttachPhoto(true)
            } ?: run {
                Image(
                    modifier = Modifier
                        .height(100.dp)
                        .padding(end = 16.dp)
                        .size(50.dp),
                    imageVector = vectorResource(Res.drawable.ic_attach_camera),
                    contentDescription = null,
                )
                Text(
                    text = "Выбрать фото документа"
                )
            }
        }
    }



//
//    Box(
//        modifier = Modifier.fillMaxWidth().height(200.dp).background(Color.DarkGray),
//        contentAlignment = Alignment.Center
//    ) {
//        if (imageBitmap != null) {
//            Image(
//                bitmap = imageBitmap!!,
//                contentDescription = "Profile",
//                modifier = Modifier.size(100.dp).clip(CircleShape).clickable {
//                    imageSourceOptionDialog = true
//                },
//                contentScale = ContentScale.Crop
//            )
//        } else {
//            Image(
//                modifier = Modifier.size(100.dp).clip(CircleShape).clickable {
//                    imageSourceOptionDialog = true
//                },
//                imageVector = vectorResource(Res.drawable.ic_person_account),
//                contentDescription = "Profile",
//            )
//        }
//    }
//
//
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(top = 16.dp)
//            .clip(RoundedCornerShape(10.dp))
//            .clickable {
//            },
//        colors = CardDefaults.cardColors(containerColor = ColorCustomResources.colorBackgroundClose),
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(100.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.Center
//        ) {
//            Icon(
//                // close
//                modifier = Modifier
//                    .size(50.dp),
//                imageVector = vectorResource(Res.drawable.ic_attach_camera),
//                contentDescription = null,
//            )
//
//            Text(
//                text = "Выбрать фото документа"
//            )
//        }
//    }


}

@Composable
fun AttachSendButton(
    isEnable: Boolean
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {

        ElevatedButton(
            enabled = isEnable,
            modifier = Modifier
                //.fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 30.dp, bottom = 30.dp),
            //                .shadow(2.dp, RoundedCornerShape(2.dp)),
            shape = RoundedCornerShape(8.dp),

            onClick = {

            },
            content = { Text("Отправить на проверку") },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = ColorCustomResources.colorBazaMainBlue
            )
        )
    }
}














