package presentation.ui.outdoor_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import co.touchlab.kermit.Logger
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.Navigator
import mykmptest.composeapp.generated.resources.Res
import mykmptest.composeapp.generated.resources.ic_back
import mykmptest.composeapp.generated.resources.ic_outdoor_create_shortcut
//import mykmptest.composeapp.generated.resources.ic_outdoor_create_shortcut
import mykmptest.composeapp.generated.resources.ic_profile
import mykmptest.composeapp.generated.resources.outdoor_title
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.koinInject
import presentation.ui.outdoor_screen.model.OutdoorUiState
import util.ScreenRoute

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun OutdoorScreen(
    navigator: Navigator,
    viewModel: OutdoorScreenViewModel = koinInject(),
    //viewModel: OutdoorScreenViewModel = koinViewModel()
    // viewModel: OutdoorScreenViewModel =

) {
    Logger.d { " 4444 OutdoorScreen opened" }


// анимация топбара при скроле
    // https://www.youtube.com/watch?v=EqCvUETekjk

    //val viewModel: OutdoorScreenViewModel by koin

//    val outDoors by viewModel.outDoorsUiState.collectAsState()
    val outDoorsUiState by viewModel.outDoorsUiState.collectAsState()

    var isRefreshing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()


    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
                //.nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    // modifier = Modifier.height(20.dp),
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
                    ),
                    title = {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = stringResource(Res.string.outdoor_title),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )

                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navigator.popBackStack()
                            }
                        ) {
                            Icon(
                                modifier = Modifier
                                    //.padding(),
                                //.systemBarsPadding() // Добавить отступ от скрытого статус-бара
                                 .size(35.dp),
                                // .clip(RoundedCornerShape(50)),
                                imageVector = vectorResource(Res.drawable.ic_back),
                                contentDescription = "Go back",

                                )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                navigator.navigate(ScreenRoute.ProfileScreen.route)
                            }
                        ) {
                            Icon(
                                imageVector = vectorResource(Res.drawable.ic_profile),
                                contentDescription = "Open profile",
                                modifier = Modifier
                                    .size(50.dp)
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior

                )
            }
        ) { paddingValue ->

            Column(
                modifier = Modifier
                    .padding(paddingValue)
                    .padding(
                        bottom = paddingValue.calculateBottomPadding()
                    )
            ) {
                OutdoorContentWithRefresh(
                    items = outDoorsUiState.outdoors,
                    isRefreshing = isRefreshing,
                    onRefresh = {
                        scope.launch {
                            isRefreshing = true
                            delay(2000L)
                            isRefreshing = false
                        }
                    },
                    navigator = navigator,
                    paddingValue = paddingValue
                )
            }
        }
    }
}




///// на всякий пожарный
//@OptIn(ExperimentalResourceApi::class)
//@Composable
//fun ContentOutdoor(
//    outDoorsUiState: OutdoorUiState,
//    paddingValues: PaddingValues,
//    snackbarHostState: SnackbarHostState,
//) {
//
//    val snackBarState = remember { mutableStateOf(false) }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(paddingValues)
//            .background(Color.Red)
//    ) {
//        LazyColumn {
//            items(outDoorsUiState.outdoors) {
//
//                Column(modifier = Modifier.fillMaxWidth()) {
//                    Row(modifier = Modifier.fillMaxWidth()) {
//                        Text(it.address)
//                        Row(modifier = Modifier.fillMaxWidth()
//                            .clickable {
//                                snackBarState.value = true
//                                CoroutineScope(Dispatchers.Main).launch {
////                                    val time = System.currentTimeMillis()
////                                    Log.d(TAG, "showing snackbar")
//                                    snackbarHostState.showSnackbar(
//                                        message = "Hey look a snackbar",
//                                        actionLabel = "Hide",
//                                        duration = SnackbarDuration.Short
//                                    )
////                                    Log.d(TAG, "done ${System.currentTimeMillis()-time}") // <-- Never called
//                                }
//                            }) {
//                            Icon(
//                                vectorResource(Res.drawable.ic_outdoor_create_shortcut),
//                                contentDescription = "cake",
//                            )
//
//                            Text("Создать ярлык")
//                        }
//
//                    }
//                    KamelImage(
//                        resource = asyncPainterResource(it.previewUrl),
//                        contentDescription = it.previewUrl,
//                        contentScale = ContentScale.Crop,
//                        //modifier = Modifier.align()
//                    )
//                }
//            }
//        }
//
//
////        if (snackBarState.value) {
////            Logger.d { " 4444 snackBarState.value=" + snackBarState.value }
////            Snackbar(
////                modifier = Modifier.padding(8.dp).align(alignment = Alignment.BottomCenter),
//////                    dismissAction = {
//////                        Logger.d { " 4444 2 snackBarState.value=" + snackBarState.value }
//////                        snackBarState.value = false
//////                    },
////                actionOnNewLine = true,
////                action = {
////                    Button(modifier = Modifier.padding(8.dp),onClick = {
////                        snackBarState.value = false
////                    }) {
////                        Text("Да понятно")
////                    }
////                },
////                shape = RoundedCornerShape(20.dp),
////            ) {
////                Text(text = "Написать реализацию для создания ярлыка")
////            }
////        }
//
//        SnackbarHost(
//            modifier = Modifier.fillMaxWidth().wrapContentHeight(),
//            hostState = snackbarHostState,
//            snackbar = {
//                Snackbar(
//                    modifier = Modifier.padding(8.dp),
////                    dismissAction = {
////                        Logger.d { " 4444 2 snackBarState.value=" + snackBarState.value }
////                        snackBarState.value = false
////                    },
//                    actionOnNewLine = true,
//                    action = {
//                        Button(modifier = Modifier.padding(8.dp), onClick = {
//                            snackbarHostState.currentSnackbarData?.dismiss()
//                        }) {
//                            Text("Да понятно")
//                        }
//                    },
//                    shape = RoundedCornerShape(20.dp),
//                ) {
//                    Text(text = "Написать реализацию для создания ярлыка")
//                }
//            }
//        )
//    }
//}