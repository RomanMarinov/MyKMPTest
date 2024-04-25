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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import data.public_info.remote.dto.Faq
import data.public_info.remote.dto.MarkerOffice
import moe.tlaster.precompose.navigation.Navigator
import mykmptest.composeapp.generated.resources.Res
import mykmptest.composeapp.generated.resources.help_call_number_one
import mykmptest.composeapp.generated.resources.help_call_number_two
import mykmptest.composeapp.generated.resources.help_check_internet
import mykmptest.composeapp.generated.resources.help_contact_title
import mykmptest.composeapp.generated.resources.help_make_call
import mykmptest.composeapp.generated.resources.help_make_question
import mykmptest.composeapp.generated.resources.help_make_question_desc
import mykmptest.composeapp.generated.resources.help_title_first
import mykmptest.composeapp.generated.resources.help_title_second
import mykmptest.composeapp.generated.resources.help_title_support
import mykmptest.composeapp.generated.resources.ic_call
import mykmptest.composeapp.generated.resources.ic_chat
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import util.ColorCustomResources

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelpContentWithRefresh(
    itemsFaq: List<Faq>,
    itemsOffices: List<MarkerOffice>,
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

    val colorsList = listOf(
        ColorCustomResources.colorGradientLightBlueStart,
        ColorCustomResources.colorGradientWhiteEnd
    )

//    val colorsList = listOf(Color.LightGray, Color.White)

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
        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {

            helpContentTitle()

            helpContentListFaq(
                lazyListState = lazyListState,
                listFaq = itemsFaq
            )
            helpContentSpeedTest()

            helpContentMessengers()

            helpContentContactTitle()

            helpContentContactTowns(itemsOffices = itemsOffices)

            helpContentVersionApp()

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
fun LazyListScope.helpContentTitle() {
    item {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 8.dp)
            ) {
                Text(
                    text = stringResource(Res.string.help_title_first),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 8.dp)
            ) {
                Text(
                    text = stringResource(Res.string.help_title_second),
                )
            }
        }
    }
}

fun LazyListScope.helpContentListFaq(
    lazyListState: LazyListState,
    listFaq: List<Faq>,
) {
    items(listFaq) { faq ->
        var contentExpanded by remember { mutableStateOf(false) }
        //  Row(modifier = Modifier.animateContentSize()) {
        Spacer(modifier = Modifier.height(8.dp))
        Box(modifier = Modifier.fillMaxWidth()) {

        }

        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)

        ) {
            Column(
//                verticalArrangement = Arrangement.spacedBy(),
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    //.padding(start = 16.dp, end = 16.dp)
//                    .clip(RoundedCornerShape(10.dp))
//                    .border(1.dp, Color.LightGray, RoundedCornerShape(10.dp)) // Добавление границы
//                    .background(color = Color.White)
                    .clickable {
                        contentExpanded = !contentExpanded
                    }
                    .animateContentSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
                //.shadow(10.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = faq.title,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.weight(2f)
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
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                        text = faq.content,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
fun LazyListScope.helpContentSpeedTest() {
    item {
        ElevatedButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
//                .shadow(2.dp, RoundedCornerShape(2.dp)),

            onClick = {

            },
            content = { Text(stringResource(Res.string.help_check_internet)) },
            colors = ButtonDefaults.buttonColors(
                contentColor = Color.White,
                containerColor = ColorCustomResources.colorBazaMainBlue
            ),
            //shape = RoundedCornerShape(10.dp)
        )
    }
}

@OptIn(ExperimentalResourceApi::class)
fun LazyListScope.helpContentMessengers() {
    item {
        val colorsList = listOf(
            ColorCustomResources.colorBazaMainRed,
            ColorCustomResources.colorBazaMainBlueCard
        )

        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)

        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Brush.horizontalGradient(colors = colorsList))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)

                ) {
                    Icon(
                        imageVector = vectorResource(Res.drawable.ic_chat),
                        contentDescription = "chat",
                        tint = Color.White
                    )
                    Text(
                        modifier = Modifier.padding(start = 16.dp),
                        text = stringResource(Res.string.help_make_question),
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                    text = stringResource(Res.string.help_make_question_desc),
                    color = Color.White
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    Icon(
                        imageVector = vectorResource(Res.drawable.ic_chat),
                        contentDescription = "chat",
                        tint = Color.White
                    )
                    Icon(
                        imageVector = vectorResource(Res.drawable.ic_chat),
                        contentDescription = "chat",
                        tint = Color.White
                    )
                    Icon(
                        imageVector = vectorResource(Res.drawable.ic_chat),
                        contentDescription = "chat",
                        tint = Color.White
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Icon(
                        imageVector = vectorResource(Res.drawable.ic_call),
                        contentDescription = "call",
                        tint = Color.White
                    )
                    Text(
                        text = stringResource(Res.string.help_title_support),
                        color = Color.White,
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = stringResource(Res.string.help_call_number_one),
                    color = Color.White,
                    fontSize = 20.sp
                )
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = stringResource(Res.string.help_call_number_two),
                    color = Color.White,
                    fontSize = 20.sp
                )

                ElevatedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
//                .shadow(2.dp, RoundedCornerShape(2.dp)),

                    onClick = {

                    },
                    content = { Text(stringResource(Res.string.help_make_call)) },
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.White,
                        containerColor = ColorCustomResources.colorBazaMainBlue
                    ),
                    //shape = RoundedCornerShape(10.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
fun LazyListScope.helpContentContactTitle() {
    item {
        Box(
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center,
            content = {
                Text(
                    text = stringResource(Res.string.help_contact_title),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
            }
        )
    }
}

fun LazyListScope.helpContentContactTowns(
    itemsOffices: List<MarkerOffice>,
) {
    item {
        ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
        ) {
            Column(
                modifier = Modifier
                    // .padding(16.dp)
                    .background(Color.White)
            ) { // Отступ для элементов внутри Card
                itemsOffices.forEachIndexed { index, office ->
                    Column {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                            }) {
                            Text(
                                text = office.title,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .padding(16.dp)
                                    .weight(1f)
                            )
                            Icon(
                                imageVector = Icons.Filled.KeyboardArrowRight,
                                contentDescription = "arrow",
                                modifier = Modifier
                                    .padding(16.dp)
                            )
                        }
                        if (index < itemsOffices.size - 1) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .background(Color.Gray)
                            )
                        }
                    }
                }
            }
        }
    }
}

fun LazyListScope.helpContentVersionApp() {
    item {
        Box(
            modifier = Modifier.fillMaxWidth()
                .padding(vertical = 16.dp),
            contentAlignment = Alignment.Center,
            content = {
                Text(
                    text = "Версия: четкая",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        )
    }
}
