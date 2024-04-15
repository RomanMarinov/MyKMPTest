package presentation.ui.outdoor_screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import moe.tlaster.precompose.flow.collectAsStateWithLifecycle
import moe.tlaster.precompose.navigation.Navigator
import mykmptest.composeapp.generated.resources.Res
import mykmptest.composeapp.generated.resources.ic_back
import mykmptest.composeapp.generated.resources.ic_profile
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.vectorResource
import org.koin.compose.koinInject
import util.ScreenRoute

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun OutdoorScreen(
    navigator: Navigator,
    viewModel: OutdoorScreenViewModel = koinInject()
     // viewModel: OutdoorScreenViewModel = koinViewModel()
   // viewModel: OutdoorScreenViewModel =

) {

// анимация топбара при скроле
    // https://www.youtube.com/watch?v=EqCvUETekjk

//   val viewModel: OutdoorScreenViewModel by inj()

    val outDoors = viewModel.outDoors.collectAsStateWithLifecycle()

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                   // modifier = Modifier.height(20.dp),
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
                    ),
                    title = {
                        Text(modifier = Modifier
                            .fillMaxWidth(),
                            text = "Мой двор",
                            textAlign = TextAlign.Center)

                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                navigator.popBackStack()
                            }
                        ) {
                            Icon(
                                modifier = Modifier
                                    .padding(start = 8.dp),
                                //.systemBarsPadding() // Добавить отступ от скрытого статус-бара
                                // .size(30.dp)
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
                                contentDescription = "Open profile"
                            )
                        }
                    }
                )
            }
        ) {
            Content()
            println("::::::::::outDoors=" + outDoors.value)
        }
    }

}

@OptIn(ExperimentalResourceApi::class, ExperimentalMaterial3Api::class)
@Composable
fun Content() {
    Column(modifier = Modifier.fillMaxSize()) {
        Text("OutdoorScreen")
        Button(onClick = {
           // navigator.navigate(ScreenRoute.WebViewScreen.route)
        }) {
            Text("next webview")
        }
    }
}
