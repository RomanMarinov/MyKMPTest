@file:OptIn(ExperimentalMaterial3Api::class)

package presentation.ui.home_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.PopUpTo
import mykmptest.composeapp.generated.resources.Res
import mykmptest.composeapp.generated.resources.ic_back
import mykmptest.composeapp.generated.resources.ic_profile
import mykmptest.composeapp.generated.resources.map_title
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import util.ScreenRoute

@OptIn(ExperimentalResourceApi::class)
@Composable
fun HomeScreen(navigator: Navigator) {




    var isRefreshing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {

        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                TopAppBar(
                    // modifier = Modifier.height(20.dp),
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
                    ),
                    title = {
                        Column(horizontalAlignment = Alignment.Start) {
                            Text(
                                text = "Привет!",
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp
                            )
                            Text(text = "+7 (930) 349 35 63", fontSize = 16.sp)
                        }
                    },
//                    navigationIcon = {
//
//
//                    },
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
                    }
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


//                OutdoorContentWithRefresh(
//                    items = outDoorsUiState.outdoors,
//                    isRefreshing = isRefreshing,
//                    onRefresh = {
//                        scope.launch {
//                            isRefreshing = true
//                            delay(2000L)
//                            isRefreshing = false
//                        }
//                    },
//                    navigator = navigator,
//                    paddingValue = paddingValue
//                )


            }
        }
    }
}
