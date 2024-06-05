package presentation.ui.outdoor_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import domain.model.user_info.Dvr
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mykmptest.composeapp.generated.resources.Res
import mykmptest.composeapp.generated.resources.ic_play
import mykmptest.composeapp.generated.resources.ic_plus_square
import mykmptest.composeapp.generated.resources.outdoor_create_shortcut
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import util.ColorCustomResources
import util.ScreenRoute
import util.navigateToWebViewHelper
import util.shimmerEffect

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun OutdoorContentWithRefresh(
    items: List<Dvr>,
    // content: @Composable (T) -> Unit,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
    navHostController: NavHostController
) {
    val pullToRefreshState = rememberPullToRefreshState()
    val snackbarHostState = remember { SnackbarHostState() }

    Box(
        modifier = modifier
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
    ) {
        LazyColumn(
            state = lazyListState,
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items) { dvr ->
                ContentLazyList(
                    dvr = dvr,
                    snackbarHostState = snackbarHostState,
                    navHostController = navHostController
                )
            }
        }

        if (pullToRefreshState.isRefreshing) {
            LaunchedEffect(true) {
                onRefresh()
            }
        }

        LaunchedEffect(isRefreshing) {
            if (isRefreshing) {
                pullToRefreshState.startRefresh()
            } else {
                pullToRefreshState.endRefresh()
            }
        }

        PullToRefreshContainer(
            state = pullToRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            containerColor = Color.White,
            contentColor = ColorCustomResources.colorBazaMainBlue
        )

        SnackbarHost(
            hostState = snackbarHostState,
            snackbar = {
                Snackbar(
                    modifier = Modifier
                        .padding(8.dp),
//                    dismissAction = {
//                        Logger.d { " 4444 2 snackBarState.value=" + snackBarState.value }
//                        snackBarState.value = false
//                    },
                    actionOnNewLine = true,
                    action = {
                        Button(modifier = Modifier.padding(8.dp), onClick = {
                            snackbarHostState.currentSnackbarData?.dismiss()
                        }) {
                            Text("Да понятно")
                        }
                    },
                    shape = RoundedCornerShape(20.dp),
                ) {
                    Text(text = "Написать реализацию для создания ярлыка")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
            //.navigationBarsPadding()
            // .padding(paddingValue)
        )


    }

}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ContentLazyList(
    dvr: Dvr,
    //snackBarState: Boolean,
    snackbarHostState: SnackbarHostState,
    navHostController: NavHostController,
) {

    Column(
        modifier = Modifier.fillMaxWidth()
        //    .navigationBarsPadding()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            // horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp),
                //.fillMaxWidth()
                verticalAlignment = Alignment.CenterVertically,
                // horizontalArrangement = Arrangement.Start
            ) {
                Text(
                    modifier = Modifier,
                    text = dvr.address,
                    fontWeight = FontWeight.Bold
                )
            }

            Row(
                modifier = Modifier,
                //.fillMaxWidth()
                //.weight(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                Box(
                    modifier = Modifier,
                    //.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd,
                ) {
                    Card(
                        modifier = Modifier
                            //.fillMaxWidth()
                            .height(40.dp),
                        shape = RoundedCornerShape(100.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                    ) {
                        Row(
                            modifier = Modifier
                                .clickable {
                                    CoroutineScope(Dispatchers.Main).launch {
                                        snackbarHostState.showSnackbar(
                                            message = "Hey look a snackbar",
                                            actionLabel = "Hide",
                                            duration = SnackbarDuration.Short
                                        )
                                    }
                                }
                                .fillMaxHeight()
                                .padding(start = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.End
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(24.dp),
                                imageVector = vectorResource(Res.drawable.ic_plus_square),
                                contentDescription = null,
                                tint = ColorCustomResources.colorBazaMainBlue
                            )
                            Text(
                                modifier = Modifier
                                    //.fillMaxWidth()
                                    .padding(start = 16.dp, end = 8.dp),
                                text = stringResource(Res.string.outdoor_create_shortcut),
                                color = ColorCustomResources.colorBazaMainBlue
                            )
                        }
                    }
                }
            }
        }
        Box(
            contentAlignment = Alignment.Center
        ) {
            KamelImage(
                onLoading = {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .shimmerEffect()
                            .height(200.dp)
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
                resource = asyncPainterResource(dvr.previewUrl),
                contentDescription = dvr.previewUrl,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        navigateToWebViewHelper(
                            navHostController = navHostController,
                            route = ScreenRoute.OutdoorScreen.route,
                            address = dvr.address,
                            videoUrl = dvr.videoUrl
                        )
                    }
            )
            Icon(
                vectorResource(Res.drawable.ic_play),
                contentDescription = "play",
                tint = Color.White,
                modifier = Modifier
                    .fillMaxSize()
                    .size(80.dp)
                    .clickable {
                        navigateToWebViewHelper(
                            navHostController = navHostController,
                            route = ScreenRoute.OutdoorScreen.route,
                            address = dvr.address,
                            videoUrl = dvr.videoUrl
                        )
                    }
            )
        }
    }
}


// не нужен
//fun navigateToWebView(navigator: Navigator, address: String, videoUrl: String) {
//    val videoUrlEncode = UrlEncoderUtil.encode(videoUrl)
//    navigator.navigate(
//        ScreenRoute.WebViewScreen.withArgs(
//            address = address,
//            videourl = videoUrlEncode
//        ),
//        NavOptions(
//            popUpTo = PopUpTo(
//                // The destination of popUpTo
//                route = ScreenRoute.OutdoorScreen.route,
//                // Whether the popUpTo destination should be popped from the back stack.
//                inclusive = false,
//            )
//        )
//    )
//}