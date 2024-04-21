@file:OptIn(ExperimentalMaterial3Api::class)

package presentation.ui.outdoor_screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import domain.model.outdoor.Dvr
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import io.ktor.utils.io.charsets.Charsets
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.PopUpTo
import mykmptest.composeapp.generated.resources.Res
import mykmptest.composeapp.generated.resources.ic_outdoor_create_shortcut
import mykmptest.composeapp.generated.resources.ic_play
import net.thauvin.erik.urlencoder.UrlEncoderUtil
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.vectorResource
import util.ScreenRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutdoorContentWithRefresh(
    items: List<Dvr>,
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
            items(items) { dvr ->
                ContentLazyList(
                    dvr = dvr,
                    snackbarHostState = snackbarHostState,
                    navigator = navigator
                )
            }

            item {
                Button(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
                    onClick = {

                    },
                    content = { Text("Добавить адрес") },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = Color.Blue
                    ),
                    shape = RoundedCornerShape(10.dp)
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
    dvr: Dvr,
    //snackBarState: Boolean,
    snackbarHostState: SnackbarHostState,
    navigator: Navigator,
) {

    Column(modifier = Modifier.fillMaxWidth().navigationBarsPadding()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.Bottom
        ) {
            Row(
                modifier = Modifier
                    .weight(2f)
            ) {
                Text(text = dvr.address)
            }
            Row(
                modifier = Modifier.padding(start = 8.dp)
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
                    tint = Color.Blue
                )
                Text(
                    modifier = Modifier.weight(1f).width(IntrinsicSize.Max),
                    text = "Создать ярлык",
                    color = Color.Blue
                )
            }
        }
        Box(
            contentAlignment = Alignment.Center
        ) {
            KamelImage(
                resource = asyncPainterResource(dvr.previewUrl),
                contentDescription = dvr.previewUrl,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        navigateToWebView(
                            navigator = navigator,
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
                        navigateToWebView(
                            navigator = navigator,
                            address = dvr.address,
                            videoUrl = dvr.videoUrl
                        )
                    }
            )
        }
    }
}

fun navigateToWebView(navigator: Navigator, address: String, videoUrl: String) {
    val videoUrlEncode = UrlEncoderUtil.encode(videoUrl)
    navigator.navigate(
        ScreenRoute.WebViewScreen.withArgs(
            address = address,
            videourl = videoUrlEncode
        ),
        NavOptions(
            popUpTo = PopUpTo(
                // The destination of popUpTo
                route = ScreenRoute.OutdoorScreen.route,
                // Whether the popUpTo destination should be popped from the back stack.
                inclusive = false,
            )
        )
    )
}