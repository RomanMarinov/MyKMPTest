package presentation.ui.domofon_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import data.domofon.remote.model.Sputnik
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.PopUpTo
import mykmptest.composeapp.generated.resources.Res
import mykmptest.composeapp.generated.resources.ic_lock
import mykmptest.composeapp.generated.resources.ic_outdoor_create_shortcut
import mykmptest.composeapp.generated.resources.ic_play
import mykmptest.composeapp.generated.resources.outdoor_add_address
import mykmptest.composeapp.generated.resources.outdoor_create_shortcut
import net.thauvin.erik.urlencoder.UrlEncoderUtil
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import util.ColorCustomResources
import util.ScreenRoute
import util.navigateToWebViewHelper
import util.shimmerEffect

@OptIn(ExperimentalMaterial3Api::class, ExperimentalResourceApi::class)
@Composable
fun DomofonContentWithRefresh(
    items: List<Sputnik>,
    // content: @Composable (T) -> Unit,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    lazyListState: LazyListState = rememberLazyListState(),
    navigator: Navigator,
    paddingValue: PaddingValues,
) {
    val pullToRefreshState = rememberPullToRefreshState()
    val snackbarHostState = remember { SnackbarHostState() }

    var isLoading by remember { mutableStateOf(true) }

    /////////////////////////////////////////////
//    помотреть тут где я на шару писал navigationBarsPadding
/////////////////////////////////////////////////

    Box(
        modifier = modifier
            .navigationBarsPadding()
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
            .navigationBarsPadding()
            .padding(
                bottom = paddingValue.calculateBottomPadding()
            )
    ) {
        LazyColumn(
            state = lazyListState,
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.navigationBarsPadding()
                .fillMaxSize().navigationBarsPadding(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items) { sputnik ->


                ContentLazyList(
                    sputnik = sputnik,
                    snackbarHostState = snackbarHostState,
                    navigator = navigator
                )


//                LaunchedEffect(true) {
//                    delay(2000L)
//                    isLoading = false
//                }
//
//                ShimmerListOutdoorOrDomofonHelper(
//                    isLoading = isLoading,
//                    contentAfterLoading = {
//
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                    // .padding(16.dp)
//                )
            }

            item {
                ElevatedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
//                .shadow(2.dp, RoundedCornerShape(2.dp)),

                    onClick = {

                    },
                    content = { Text(stringResource(Res.string.outdoor_add_address)) },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = ColorCustomResources.colorBazaMainBlue
                    ),
                    //shape = RoundedCornerShape(10.dp)
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
                .align(Alignment.BottomCenter).navigationBarsPadding()
            // .padding(paddingValue)
        )


    }

}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ContentLazyList(
    sputnik: Sputnik,
    //snackBarState: Boolean,
    snackbarHostState: SnackbarHostState,
    navigator: Navigator,
) {

    var switchCheckedState by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth().navigationBarsPadding()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ) {
            Row(modifier = Modifier.weight(2f)) {
                Text(
                    text = sputnik.title,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                modifier = Modifier
                    .padding(start = 8.dp)
                    .width(IntrinsicSize.Max)
                    .clickable {
                        CoroutineScope(Dispatchers.Main).launch {
                            snackbarHostState.showSnackbar(
                                message = "Hey look a snackbar",
                                actionLabel = "Hide",
                                duration = SnackbarDuration.Short
                            )
                        }
                    },
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ) {
                Icon(
                    vectorResource(Res.drawable.ic_outdoor_create_shortcut),
                    contentDescription = "shortcut",
                    tint = ColorCustomResources.colorShortcut
                )
                Text(
                    modifier = Modifier.weight(1f).width(IntrinsicSize.Max),
                    text = stringResource(Res.string.outdoor_create_shortcut),
                    color = ColorCustomResources.colorShortcut
                )
            }
        }

        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                KamelImage(
                    onLoading = {
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .shimmerEffect()
                            .height(200.dp)
                            .padding(start = 16.dp, end = 16.dp))
                    },
                    onFailure = {
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .shimmerEffect()
                            .height(200.dp)
                            .padding(start = 16.dp, end = 16.dp))
                    },
                    resource = asyncPainterResource(sputnik.previewUrl),
                    contentDescription = sputnik.previewUrl,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))

//                        .placeholder(
//                            color = Color.Gray,
//                            shape = RoundedCornerShape(8.dp) // Опционально, если нужно закруглить углы
//                        )
//                        .shimmer(
//                            baseColor = Color.LightGray,
//                            highlightColor = Color.Gray,
//                            animationSpec = shimmerAnimationSpec
//                        )
                        .clickable {
                            navigateToWebViewHelper(
                                navigator = navigator,
                                route = ScreenRoute.DomofonScreen.route,
                                address = sputnik.title,
                                videoUrl = sputnik.videoUrl
                            )
                        }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        vectorResource(Res.drawable.ic_play),
                        contentDescription = "play",
                        tint = Color.White,
                        modifier = Modifier
                            //.fillMaxWidth()
                            // .weight(1f)
                            .size(80.dp)
                            .clickable {
                                navigateToWebViewHelper(
                                    navigator = navigator,
                                    route = ScreenRoute.DomofonScreen.route,
                                    address = sputnik.title,
                                    videoUrl = sputnik.videoUrl
                                )
                            }
                    )
                    Icon(
                        vectorResource(Res.drawable.ic_lock),
                        contentDescription = "lock",
                        tint = Color.White,
                        modifier = Modifier
                            //.weight(1f)
                            //.fillMaxWidth()
                            .size(80.dp)
                            .clickable {
                            }
                    )
                }
            }

            Row(
                modifier = Modifier.fillMaxSize().padding(top = 16.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Принимать звонки")
                Switch(
                    modifier = Modifier.height(14.dp).padding(start = 16.dp),
                    checked = switchCheckedState,
                    onCheckedChange = { switchCheckedState = it },
                    colors = SwitchDefaults.colors(
                        checkedTrackColor = Color.White,
                        checkedThumbColor = ColorCustomResources.colorBazaMainBlue,
                        checkedIconColor = Color.DarkGray,
                        checkedBorderColor = ColorCustomResources.colorBazaMainBlue,
                        uncheckedThumbColor = ColorCustomResources.colorBazaMainRed,
                        uncheckedIconColor = ColorCustomResources.colorBazaMainBlue,
                        disabledCheckedThumbColor = ColorCustomResources.colorSwitch,
                        disabledUncheckedThumbColor = ColorCustomResources.colorBazaMainRed,
                    )
                )
            }
        }
    }
}

fun navigateDomofonToWebView(navigator: Navigator, address: String, videoUrl: String) {
    val videoUrlEncode = UrlEncoderUtil.encode(videoUrl)
    navigator.navigate(
        ScreenRoute.WebViewScreen.withArgs(
            address = address,
            videourl = videoUrlEncode
        ),
        NavOptions(
            popUpTo = PopUpTo(
                // The destination of popUpTo
                route = ScreenRoute.DomofonScreen.route,
                // Whether the popUpTo destination should be popped from the back stack.
                inclusive = false,
            )
        )
    )
}