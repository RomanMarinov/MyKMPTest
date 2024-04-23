package presentation.ui.help_screen

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.public_info.remote.dto.Faq
import domain.model.outdoor.Dvr
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.PopUpTo
import mykmptest.composeapp.generated.resources.Res
import mykmptest.composeapp.generated.resources.domofon_name_nav
import mykmptest.composeapp.generated.resources.help_title
import mykmptest.composeapp.generated.resources.help_title_first
import mykmptest.composeapp.generated.resources.help_title_second
import mykmptest.composeapp.generated.resources.ic_outdoor_create_shortcut
import mykmptest.composeapp.generated.resources.ic_play
import net.thauvin.erik.urlencoder.UrlEncoderUtil
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import util.ScreenRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpContentWithRefresh(
    items: List<Faq>,
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
    val colorsList = listOf(Color.LightGray, Color.White)

    Box(
        modifier = modifier
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
            .navigationBarsPadding()
            .padding(
                bottom = paddingValue.calculateBottomPadding()
            )
            .background(
                Brush.linearGradient(
                    colors = colorsList,
                    start = Offset.Zero,
                    end = Offset.Infinite
                )
            )
    ) {

        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            HelpContentTitle()

            HelpContentListFaq(
                lazyListState = lazyListState,
                listFaq = items
            )
            HelpContentSpeedTest()

            HelpContentMessengers()

            HelpContentVersionApp()

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
          //  modifier = Modifier.align(Alignment.CenterHorizontally),

            )


    }

}

@OptIn(ExperimentalResourceApi::class)
fun LazyListScope.HelpContentTitle() {
    item {
        Column(modifier = Modifier
            .fillMaxWidth()
        ) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                Text(
                    text = stringResource(Res.string.help_title_first),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }

            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
            ) {
                Text(
                    text = stringResource(Res.string.help_title_second),
                )
            }
        }
    }
}

fun LazyListScope.HelpContentListFaq(
    lazyListState: LazyListState,
    listFaq: List<Faq>,
) {
//    LazyColumn(
//        state = lazyListState,
//        contentPadding = PaddingValues(top = 16.dp, bottom = 16.dp),
//        modifier = Modifier.fillMaxWidth(),
//        verticalArrangement = Arrangement.spacedBy(16.dp)
//    ) {
        items(listFaq) { faq ->

            var contentExpanded by remember { mutableStateOf(false) }

            //  Row(modifier = Modifier.animateContentSize()) {
            Spacer(modifier = Modifier.height(8.dp))
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    // .padding(8.dp)
                    .animateContentSize()
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clickable {
                        contentExpanded = !contentExpanded
                    }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = faq.title,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .weight(2f)

                    )
                    IconButton(
                        onClick = { contentExpanded = !contentExpanded }) {
                        Icon(
                            modifier = Modifier
                                .size(30.dp)
                                .weight(1f)
                                .width(IntrinsicSize.Max),
                            imageVector = if (contentExpanded) {
                                Icons.Filled.KeyboardArrowUp
                            } else {
                                Icons.Filled.KeyboardArrowDown
                            },
                            contentDescription = if (contentExpanded) {
                                "ExpandLess"
                            } else {
                                "ExpandMore"
                            }
                        )
                    }
                }


                if (contentExpanded) {
                    Text(
                        text = faq.content
                    )
                }


            }
            //  }
        }
   // }
}

fun LazyListScope.HelpContentSpeedTest() {

    item {

        Button(
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            onClick = {

            },
            content = { Text("Проверить скорость интернета") },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = Color.Blue
            ),
            shape = RoundedCornerShape(10.dp)
        )
    }

}

fun LazyListScope.HelpContentMessengers() {
    item {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.fillMaxWidth()) {
//            Icon(
//                Icons.Outlined.,
//                contentDescription = "chat"
//            )
            }
        }
    }
}

fun LazyListScope.HelpContentVersionApp() {
    item {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Версия: четкая",
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
