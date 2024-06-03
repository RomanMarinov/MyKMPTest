package presentation.ui.domofon_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import co.touchlab.kermit.Logger
import domain.model.user_info.Sputnik
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import mykmptest.composeapp.generated.resources.Res
import mykmptest.composeapp.generated.resources.domofon_title
import mykmptest.composeapp.generated.resources.ic_back
import mykmptest.composeapp.generated.resources.ic_profile
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.koinInject
import presentation.ui.domofon_screen.model.UnLockState
import util.ColorCustomResources
import util.ScreenRoute
import util.SnackBarHostHelper

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun DomofonScreen(
//    bottomNavigationPaddingValue: PaddingValues,
//    navHostController: NavHostController,
//    viewModel: DomofonScreenViewModel = koinInject()
//) {
//
//    val sputnikUiState by viewModel.domofonUiState.collectAsState()
//    val items: List<Sputnik>? = sputnikUiState?.domofon?.sputnik
//    val statusDomofonUnlockDoor by viewModel.statusDomofonUnlockDoor.collectAsStateWithLifecycle()
//    var isRefreshing by remember { mutableStateOf(false) }
//    val scope = rememberCoroutineScope()
//
//    Logger.d("4444 statusDomofonUnlockDoor=" + statusDomofonUnlockDoor)
//
//    val pullToRefreshState = rememberPullToRefreshState()
//    val snackbarHostState = remember { SnackbarHostState() }
//
//    var isLoading by remember { mutableStateOf(true) }
//
//    val isShowGroupState = remember { mutableStateOf(true) }
//    val isShowGroupStateFirst = remember { mutableStateOf(false) }
//
//    val groupItems: Map<Int, List<Sputnik>>? = items?.groupBy { it.addrId }
//    groupItems?.let {
//        if (it.size == 0) {
//            Logger.d("4444 проверка 0")
//        }
//        if (it.size in 1..2) {
//            Logger.d("4444 проверка 1-2")
//            isShowGroupStateFirst.value = false
//        }
//        if (it.size >= 3) {
//            Logger.d("4444 проверка 3")
//            isShowGroupStateFirst.value = true
//        }
//    }
//
//    Surface(
//        modifier = Modifier.fillMaxSize(),
//        color = MaterialTheme.colorScheme.background
//    ) {
//        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()
//
//        Scaffold(
//            modifier = Modifier
//                .fillMaxSize(),
//            // .nestedScroll(scrollBehavior.nestedScrollConnection),
//            topBar = {
//                TopAppBar(
//                    // modifier = Modifier.height(20.dp),
////               colors = TopAppBarDefaults.smallTopAppBarColors(
////                  containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
////               ),
//                    title = {
//                        Text(
//                            modifier = Modifier
//                                .fillMaxWidth(),
//                            text = stringResource(Res.string.domofon_title),
//                            textAlign = TextAlign.Center,
//                            fontWeight = FontWeight.Bold
//                        )
//
//                    },
//                    navigationIcon = {
//                        IconButton(
//                            onClick = {
//                                // <3
//                                if (!isShowGroupStateFirst.value) {
//                                    navHostController.navigate(
//                                        ScreenRoute.HomeScreen.route,
//                                        NavOptions.Builder().setPopUpTo(
//                                            // The destination of popUpTo
//                                            route = ScreenRoute.HomeScreen.route,
//                                            // Whether the popUpTo destination should be popped from the back stack.
//                                            inclusive = false,
//                                        ).build()
//                                    )
//                                } else { // >=3
//                                    if (isShowGroupState.value) { // стоит на группе
//                                        navHostController.navigate(
//                                            ScreenRoute.HomeScreen.route,
//                                            NavOptions.Builder().setPopUpTo(
//                                                // The destination of popUpTo
//                                                route = ScreenRoute.HomeScreen.route,
//                                                // Whether the popUpTo destination should be popped from the back stack.
//                                                inclusive = false,
//                                            ).build()
//                                        )
//                                    } else { // стоит не на группе
//                                        isShowGroupState.value = true
//                                    }
//                                }
//                            }
//                        ) {
//                            Icon(
//                                modifier = Modifier
//                                    //.padding(),
//                                    //.systemBarsPadding() // Добавить отступ от скрытого статус-бара
//                                    .size(35.dp),
//                                // .clip(RoundedCornerShape(50)),
//                                imageVector = vectorResource(Res.drawable.ic_back),
//                                contentDescription = "Go back",
//
//                                )
//                        }
//                    },
//                    actions = {
//                        IconButton(
//                            onClick = {
//                                navHostController.navigate(ScreenRoute.ProfileScreen.route)
//                            }
//                        ) {
//                            Icon(
//                                imageVector = vectorResource(Res.drawable.ic_profile),
//                                contentDescription = "Open profile",
//                                modifier = Modifier
//                                    .size(24.dp)
//                            )
//                        }
//                    },
//                    modifier = Modifier
//                        .shadow(4.dp),
//                    scrollBehavior = scrollBehavior
//                )
//            }
//        ) { domofonTopBarPaddingValue ->
//
//            Column(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(top = domofonTopBarPaddingValue.calculateTopPadding())
//                    .padding(bottom = bottomNavigationPaddingValue.calculateBottomPadding())
//                    .background(ColorCustomResources.colorBackgroundMain)
//            ) {
//                Box(
//                    modifier = Modifier
//                        .nestedScroll(pullToRefreshState.nestedScrollConnection)
////                        .navigationBarsPadding()
////                        .padding(
////                            bottom = paddingValue.calculateBottomPadding()
////                        )
//                ) {
//                    groupItems?.let {
//                        //listGroup ->
//                        if (it.size in 1..2) {
//                            NotGroupedContent(
//                                //lazyListState = lazyListState,
//                                items = items,
//                                snackbarHostState = snackbarHostState,
//                                navHostController = navHostController,
//                                viewModel = viewModel
//                            )
//                        }
//                        if (it.size >= 3) {
//                            val addrIdFromGroup = remember { mutableIntStateOf(-1) }
//
//                            if (isShowGroupState.value) {
//                                GroupedContent(
//                                    items = items,
//                                    onShowGroup = { bool ->
//                                        isShowGroupState.value = bool
//                                    },
//                                    onAddrId = { addrId ->
//                                        addrIdFromGroup.value = addrId
//                                    },
//                                    viewModel = viewModel,
//                                    onShowSnackBarUnlockDoorStatus = {
//
//                                        // isShowSnackBarUnlockDoorStatus.value = status
//                                    },
//                                    navHostController = navHostController
//                                )
//                            } else {
//                                // сюда отдавать список подъездов только одного адреса
//                                val listFilterByAddrId = items.filter { sputnikItem ->  sputnikItem.addrId == addrIdFromGroup.value }
//
//                                NotGroupedContentFrom(
//                                    items = listFilterByAddrId,
//                                    snackbarHostState = snackbarHostState,
//                                    navHostController = navHostController,
//                                    viewModel = viewModel
//                                )
//                            }
//                        }
//                    }
//
//                    if (pullToRefreshState.isRefreshing) {
//                        LaunchedEffect(true) {
//                            scope.launch {
//                                isRefreshing = true
//                                delay(2000L)
//                                isRefreshing = false
//                            }
//                        }
//                    }
//
//                    LaunchedEffect(isRefreshing) {
//                        if (isRefreshing) {
//                            pullToRefreshState.startRefresh()
//                        } else {
//                            pullToRefreshState.endRefresh()
//                        }
//                    }
//
//                    PullToRefreshContainer(
//                        state = pullToRefreshState,
//                        modifier = Modifier
//                            .align(Alignment.TopCenter),
//                        containerColor = Color.White
//                    )
//
//                    when (statusDomofonUnlockDoor) {
//                        UnLockState.OPENED_DOOR -> {
//                            SnackBarHostHelper.ShortShortTime(
//                                message = "Дверь открыта",
//                                onFinishTime = {
//                                    viewModel.resetSnackBarUnLockState()
//                                }
//                            )
//                        }
//
//                        UnLockState.ERROR_OPEN -> {
//                            SnackBarHostHelper.ShortShortTime(
//                                message = "Ошибка открытия двери",
//                                onFinishTime = {
//                                    viewModel.resetSnackBarUnLockState()
//                                }
//                            )
//                        }
//                        UnLockState.DEFAULT -> {}
//                    }
//                }
//            }
//        }
//    }
//}


///////////////////////////////////


enum class DomofonContent {
    LIST_GROUP, LIST, LIST_ZERO, DEFAULT
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DomofonScreen(
    bottomNavigationPaddingValue: PaddingValues,
    navHostController: NavHostController,
    viewModel: DomofonScreenViewModel = koinInject()
) {

    val sputnikUiState by viewModel.domofonUiState.collectAsState()
    val items: List<Sputnik> = sputnikUiState?.domofon?.sputnik ?: emptyList()
    val statusDomofonUnlockDoor by viewModel.statusDomofonUnlockDoor.collectAsStateWithLifecycle()
    var isRefreshing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val pullToRefreshState = rememberPullToRefreshState()
    val snackbarHostState = remember { SnackbarHostState() }

    var isLoading by remember { mutableStateOf(true) }
    val domofonContentState = remember { mutableStateOf(DomofonContent.DEFAULT) }
//    val domofonContentState = remember { mutableStateOf(DomofonContent.DEFAULT) }
    val addrIdFromGroup = remember { mutableIntStateOf(-1) }
//    val isShowGroupState = remember { mutableStateOf(true) }
//    val isShowGroupStateFirst = remember { mutableStateOf(false) }
    val countGroup = remember { mutableStateOf(-1) }


    val groupItems: Map<Int, List<Sputnik>>? = items?.groupBy { it.addrId }

    LaunchedEffect(groupItems) {
        groupItems?.let {
            if (it.size == 0) {
                countGroup.value = 0
                domofonContentState.value = DomofonContent.LIST_ZERO
                Logger.d("4444 -2 domofonContentState.value=" + domofonContentState.value + " countGroup.value=" + countGroup.value)
            }
            if (it.size in 1..2) {
                countGroup.value = it.size
                domofonContentState.value = DomofonContent.LIST
//            isShowGroupStateFirst.value = false
                Logger.d("4444 -1 domofonContentState.value=" + domofonContentState.value + " countGroup.value=" + countGroup.value)
            }
            if (it.size >= 3) {
                countGroup.value = it.size
                domofonContentState.value = DomofonContent.LIST_GROUP
//            isShowGroupStateFirst.value = true
                Logger.d("4444 0 domofonContentState.value=" + domofonContentState.value + " countGroup.value=" + countGroup.value)
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            // .nestedScroll(scrollBehavior.nestedScrollConnection),
            topBar = {
                TopAppBar(
                    // modifier = Modifier.height(20.dp),
//               colors = TopAppBarDefaults.smallTopAppBarColors(
//                  containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
//               ),
                    title = {
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = stringResource(Res.string.domofon_title),
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )

                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                // <3
                                if (countGroup.value in 1..2 && domofonContentState.value == DomofonContent.LIST) {
//                                if (!isShowGroupStateFirst.value) {
                                    navHostController.navigate(
                                        ScreenRoute.HomeScreen.route,
                                        NavOptions.Builder().setPopUpTo(
                                            // The destination of popUpTo
                                            route = ScreenRoute.HomeScreen.route,
                                            // Whether the popUpTo destination should be popped from the back stack.
                                            inclusive = false,
                                        ).build()
                                    )
                                } // >=3
                                if (countGroup.value > 2 && domofonContentState.value == DomofonContent.LIST_GROUP) { // стоит на группе
//                                    if (isShowGroupState.value) { // стоит на группе
                                    navHostController.navigate(
                                        ScreenRoute.HomeScreen.route,
                                        NavOptions.Builder().setPopUpTo(
                                            // The destination of popUpTo
                                            route = ScreenRoute.HomeScreen.route,
                                            // Whether the popUpTo destination should be popped from the back stack.
                                            inclusive = false,
                                        ).build()
                                    )
                                }
                                if (countGroup.value > 2 && domofonContentState.value == DomofonContent.LIST) { // стоит на группе
                                    domofonContentState.value = DomofonContent.LIST_GROUP
                                }
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
                                navHostController.navigate(ScreenRoute.ProfileScreen.route)
                            }
                        ) {
                            Icon(
                                imageVector = vectorResource(Res.drawable.ic_profile),
                                contentDescription = "Open profile",
                                modifier = Modifier
                                    .size(24.dp)
                            )
                        }
                    },
                    modifier = Modifier
                        .shadow(4.dp),
                    scrollBehavior = scrollBehavior
                )
            }
        ) { domofonTopBarPaddingValue ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = domofonTopBarPaddingValue.calculateTopPadding())
                    .padding(bottom = bottomNavigationPaddingValue.calculateBottomPadding())
                    .background(ColorCustomResources.colorBackgroundMain)
            ) {
                Box(
                    modifier = Modifier
                        .nestedScroll(pullToRefreshState.nestedScrollConnection)
//                        .navigationBarsPadding()
//                        .padding(
//                            bottom = paddingValue.calculateBottomPadding()
//                        )
                ) {
                    groupItems?.let {

                        if (it.size == 0 && domofonContentState.value == DomofonContent.LIST_ZERO) {
                            Logger.d("4444 пусто domofonContentState.value=" + domofonContentState.value)

                            DomofonListContent(
                                //lazyListState = lazyListState,
                                items = items,
                                snackbarHostState = snackbarHostState,
                                navHostController = navHostController,
                                viewModel = viewModel
                            )
                        }

                        if (it.size in 1..2 && domofonContentState.value == DomofonContent.LIST) {
                            Logger.d("4444 1 domofonContentState.value=" + domofonContentState.value)
                            DomofonListContent(
                                //lazyListState = lazyListState,
                                items = items,
                                snackbarHostState = snackbarHostState,
                                navHostController = navHostController,
                                viewModel = viewModel
                            )
                        }

//                        if (it.size > 2 && domofonContentState.value == DomofonContent.LIST_GROUP) {
                        if (it.size > 2) {
                            if (domofonContentState.value == DomofonContent.LIST_GROUP) {
                                Logger.d("4444 2 domofonContentState.value=" + domofonContentState.value)
                                DomofonListGroupContent(
                                    items = items,
                                    onGoDomofonContentList = {
                                        domofonContentState.value = DomofonContent.LIST
                                        Logger.d("4444 3 domofonContentState.value=" + domofonContentState.value)
//                                        isShowGroupState.value = bool
                                    },
                                    onAddrId = { addrId ->
                                        addrIdFromGroup.value = addrId
                                    },
                                    viewModel = viewModel,
                                    onShowSnackBarUnlockDoorStatus = {
                                        // isShowSnackBarUnlockDoorStatus.value = status
                                    },
                                    navHostController = navHostController
                                )
                            }

                            if (domofonContentState.value == DomofonContent.LIST) {
                                Logger.d("4444 4 domofonContentState.value=" + domofonContentState.value)
                                val listFilterByAddrId =
                                    items.filter { sputnikItem -> sputnikItem.addrId == addrIdFromGroup.value }
                                DomofonListContent(
                                    //lazyListState = lazyListState,
                                    items = listFilterByAddrId,
                                    snackbarHostState = snackbarHostState,
                                    navHostController = navHostController,
                                    viewModel = viewModel
                                )
                            }
                        }
                    }

                    if (pullToRefreshState.isRefreshing) {
                        LaunchedEffect(true) {
                            scope.launch {
                                isRefreshing = true
                                delay(2000L)
                                isRefreshing = false
                            }
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
                        modifier = Modifier
                            .align(Alignment.TopCenter),
                        containerColor = Color.White
                    )

                    when (statusDomofonUnlockDoor) {
                        UnLockState.OPENED_DOOR -> {
                            SnackBarHostHelper.ShortShortTime(
                                message = "Дверь открыта",
                                onFinishTime = {
                                    viewModel.resetSnackBarUnLockState()
                                }
                            )
                        }

                        UnLockState.ERROR_OPEN -> {
                            SnackBarHostHelper.ShortShortTime(
                                message = "Ошибка открытия двери",
                                onFinishTime = {
                                    viewModel.resetSnackBarUnLockState()
                                }
                            )
                        }

                        UnLockState.DEFAULT -> {}
                    }
                }
            }
        }
    }
}
