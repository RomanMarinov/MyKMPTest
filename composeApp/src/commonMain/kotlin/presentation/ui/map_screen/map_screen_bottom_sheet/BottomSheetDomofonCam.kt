package presentation.ui.map_screen.map_screen_bottom_sheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import moe.tlaster.precompose.navigation.Navigator
import mykmptest.composeapp.generated.resources.Res
import mykmptest.composeapp.generated.resources.ic_map_city_cam
import mykmptest.composeapp.generated.resources.map_sheet_domofon
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import presentation.ui.map_screen.model.MarkerDetail
import util.ScreenRoute
import util.navigateToWebViewHelper
import util.shimmerEffect

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun BottomSheetDomofonCam(
    navigator: Navigator,
    markerDetail: MarkerDetail,
    openBottomSheet: (Boolean) -> Unit,
){
    //Log.d("4444", " BottomSheet сработал markerDetail=" + markerDetail)
    val scope = rememberCoroutineScope()

    val openBottomSheetState by rememberSaveable { mutableStateOf(true) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (openBottomSheetState) {
        ModalBottomSheet(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp),
            onDismissRequest = { openBottomSheet(false) },
            sheetState = bottomSheetState,
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                ) {
                    Icon(
                        modifier = Modifier
                            .size(35.dp)
                            .padding(start = 16.dp)
                            .align(Alignment.CenterStart),
                        imageVector = vectorResource(Res.drawable.ic_map_city_cam),
                        contentDescription = null
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.Center),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            fontWeight = FontWeight.Bold,
                            text = "Умный домофон"
                        )
                    }
                }
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    text = markerDetail.titleAddress.toString()
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    KamelImage(
                        onLoading = {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .shimmerEffect()
                                    .padding(start = 16.dp, end = 16.dp)
                            )
                        },
                        onFailure = {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .shimmerEffect()
                                    .height(200.dp)
                                    .padding(start = 16.dp, end = 16.dp)
                            )
                        },
                        resource = asyncPainterResource(markerDetail.previewUrl ?: ""),
                        contentDescription = markerDetail.previewUrl,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .clickable {
                                navigateToWebViewHelper(
                                    navigator = navigator,
                                    route = ScreenRoute.MapScreen.route,
                                    address = markerDetail.titleAddress.toString(),
                                    videoUrl = markerDetail.videoUrl.toString()
                                )
                            }
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        text = stringResource(Res.string.map_sheet_domofon)
                    )
                }
            }


//            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
//                Button(
//                    // Note: If you provide logic outside of onDismissRequest to remove the sheet,
//                    // you must additionally handle intended state cleanup, if any.
//                    onClick = {
//                        openBottomSheet(false)
//
//                        scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
//                            if (!bottomSheetState.isVisible) {
//                                //    openBottomSheet = false
//                            }
//                        }
//                    }
//                ) {
//                    Text("Hide Bottom Sheet")
//                }
//            }
        }
    }
}