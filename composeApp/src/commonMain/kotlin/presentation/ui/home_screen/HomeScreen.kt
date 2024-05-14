@file:OptIn(ExperimentalMaterial3Api::class)

package presentation.ui.home_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import moe.tlaster.precompose.navigation.Navigator
import mykmptest.composeapp.generated.resources.Res
import mykmptest.composeapp.generated.resources.ic_profile
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.vectorResource
import util.ColorCustomResources
import util.ScreenRoute

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navigator: Navigator) {




    var isRefreshing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    val colorsList = listOf(
        ColorCustomResources.colorGradientLightBlueStart,
        ColorCustomResources.colorGradientWhiteEnd
    )

//    Surface(
//        modifier = Modifier.fillMaxSize(),
//        color = MaterialTheme.colorScheme.background
//    ) {


        Scaffold(
            modifier = Modifier
                .fillMaxSize(),
            topBar = {
                TopAppBar(
                    // modifier = Modifier.height(20.dp),
//                    colors = TopAppBarDefaults.smallTopAppBarColors(
//                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
//                    ),
//                    colors = TopAppBarColors(
//                        containerColor = Color.Magenta,
//                        scrolledContainerColor = Color.Yellow,
//                        navigationIconContentColor = Color.Green,
//                        titleContentColor = Color.Cyan,
//                        actionIconContentColor = Color.Red
//                    ),
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
                                    .size(24.dp)
                            )
                        }
                    },
                    modifier = Modifier
                        .shadow(4.dp)
                )
            },

        ) { paddingValue ->

            Column(
                modifier = Modifier.fillMaxSize()
                    .padding(paddingValue)
                    .padding(bottom = paddingValue.calculateBottomPadding())
                    .background(ColorCustomResources.colorBackgroundMain)
            ) {


                HomeContentWithRefresh(
                   // items = outDoorsUiState.outdoors,
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
   // }
}
