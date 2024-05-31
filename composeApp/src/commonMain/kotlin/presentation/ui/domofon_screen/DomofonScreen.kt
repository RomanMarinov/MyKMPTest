package presentation.ui.domofon_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
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
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
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
import util.ColorCustomResources
import util.ScreenRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DomofonScreen(
    navHostController: NavHostController,
    viewModel: DomofonScreenViewModel = koinInject()
) {

    val sputnikUiState by viewModel.domofonUiState.collectAsState()
    val items = sputnikUiState?.domofon?.sputnik
    var isRefreshing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val pullToRefreshState = rememberPullToRefreshState()
    val snackbarHostState = remember { SnackbarHostState() }

    var isLoading by remember { mutableStateOf(true) }

    val onShowGroupState = remember { mutableStateOf(true) }
    val onShowGroupStateFirst = remember { mutableStateOf(false) }

    val groupItems: Map<Int, List<Sputnik>>? = items?.groupBy { it.addrId }
    groupItems?.let {
        if (it.size in 1..2) {
            onShowGroupStateFirst.value = false
        }
        if (it.size >= 3) {
            onShowGroupStateFirst.value = true
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
                                if (!onShowGroupStateFirst.value) {
                                    navHostController.navigate(
                                        ScreenRoute.HomeScreen.route,
                                        NavOptions.Builder().setPopUpTo(
                                            // The destination of popUpTo
                                            route = ScreenRoute.HomeScreen.route,
                                            // Whether the popUpTo destination should be popped from the back stack.
                                            inclusive = false,
                                        ).build()
                                    )
                                } else { // >=3
                                    if (onShowGroupState.value) { // стоит на группе
                                        navHostController.navigate(
                                            ScreenRoute.HomeScreen.route,
                                            NavOptions.Builder().setPopUpTo(
                                                // The destination of popUpTo
                                                route = ScreenRoute.HomeScreen.route,
                                                // Whether the popUpTo destination should be popped from the back stack.
                                                inclusive = false,
                                            ).build()
                                        )
                                    } else { // стоит не на группе
                                        onShowGroupState.value = true
                                    }
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
        ) { paddingValue ->

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValue)
                    .padding(
                        bottom = paddingValue.calculateBottomPadding()
                    )
                    .background(ColorCustomResources.colorBackgroundMain)
            ) {
                Box(
                    modifier = Modifier
                        .nestedScroll(pullToRefreshState.nestedScrollConnection)
                        .navigationBarsPadding()
                        .padding(
                            bottom = paddingValue.calculateBottomPadding()
                        )
                ) {
                    groupItems?.let {
                        //listGroup ->
                        if (it.size in 1..2) {
                            NotGroupedContent(
                                //lazyListState = lazyListState,
                                items = items,
                                snackbarHostState = snackbarHostState,
                                navHostController = navHostController
                            )
                        }
                        if (it.size >= 3) {
                            if (onShowGroupState.value) {
                                GroupedContent(
                                    onShowGroup = { bool ->
                                        onShowGroupState.value = bool
                                    },
                                    navHostController = navHostController
                                )
                            } else {
                                NotGroupedContent(
                                    items = items,
                                    snackbarHostState = snackbarHostState,
                                    navHostController = navHostController
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
                }
            }
        }
    }
}
