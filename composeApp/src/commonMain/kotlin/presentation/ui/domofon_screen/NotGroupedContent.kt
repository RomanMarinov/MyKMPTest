package presentation.ui.domofon_screen

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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import domain.model.user_info.Sputnik
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mykmptest.composeapp.generated.resources.Res
import mykmptest.composeapp.generated.resources.ic_lock
import mykmptest.composeapp.generated.resources.ic_play
import mykmptest.composeapp.generated.resources.ic_plus_square
import mykmptest.composeapp.generated.resources.outdoor_add_address
import mykmptest.composeapp.generated.resources.outdoor_create_shortcut
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import util.ColorCustomResources
import util.ScreenRoute
import util.navigateToWebViewHelper
import util.shimmerEffect

@Composable
fun NotGroupedContent(
//    lazyListState: LazyListState,
    items: List<Sputnik>,
    snackbarHostState: SnackbarHostState,
    navHostController: NavHostController
) {
    val lazyListState = rememberLazyListState()

    LazyColumn(
        state = lazyListState,
        contentPadding = PaddingValues(16.dp),
        modifier = Modifier.navigationBarsPadding()
            .fillMaxSize().navigationBarsPadding(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items) { sputnik ->
            ContentLazyListItem(
                sputnik = sputnik,
                snackbarHostState = snackbarHostState,
                navHostController = navHostController
            )
        }

        item {
            ElevatedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                shape = RoundedCornerShape(8.dp),
                onClick = {

                },
                content = { Text(stringResource(Res.string.outdoor_add_address)) },
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = ColorCustomResources.colorBazaMainBlue
                )
            )
        }
    }
}


@Composable
fun ContentLazyListItem(
    sputnik: Sputnik,
    //snackBarState: Boolean,
    snackbarHostState: SnackbarHostState,
    navHostController: NavHostController,
) {

    var switchCheckedState by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth().navigationBarsPadding()) {
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
                    text = sputnik.title,
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

        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier.fillMaxWidth(),
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
                                navHostController = navHostController,
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
                                    navHostController = navHostController,
                                    route = ScreenRoute.DomofonScreen.route,
                                    address = sputnik.title,
                                    videoUrl = sputnik.videoUrl
                                )
                            }
                    )

                    if (sputnik.fullControl) {
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