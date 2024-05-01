//import org.koin.compose.KoinContext
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.flow.collectLatest
import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.rememberNavigator
import mykmptest.composeapp.generated.resources.Res
import mykmptest.composeapp.generated.resources.domofon_name_nav
import mykmptest.composeapp.generated.resources.help_name_nav
import mykmptest.composeapp.generated.resources.home_name_nav
import mykmptest.composeapp.generated.resources.ic_navbar_domofon
import mykmptest.composeapp.generated.resources.ic_navbar_help
import mykmptest.composeapp.generated.resources.ic_navbar_home
import mykmptest.composeapp.generated.resources.ic_navbar_map
import mykmptest.composeapp.generated.resources.ic_navbar_outdoor
import mykmptest.composeapp.generated.resources.map_name_nav
import mykmptest.composeapp.generated.resources.outdoor_name_nav
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import util.ColorCustomResources
import util.ScreenRoute


data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null,
    val route: String,
)

//@OptIn(ExperimentalResourceApi::class)
//@Composable
//fun imageResource(resource: DrawableResource): ImageBitmap {
//    val context = ContextAmbient.current
//    val drawable = ContextCompat.getDrawable(context, resource) ?: error("Resource not found")
//    val bitmap = (drawable as BitmapDrawable).bitmap
//    return bitmap.asImageBitmap()
//}

@Composable
fun App() {


    KoinContext {
        AppContent()



    }
}

@Composable
fun GetCurrentEntry(navigator: Navigator, onEntryChanged: (String) -> Unit) {
    LaunchedEffect(true) {
        navigator.currentEntry.collectLatest { entry ->
            onEntryChanged(entry?.route?.route ?: "")
        }
    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun AppContent() {
    // KoinContext{
    MaterialTheme {

        PreComposeApp {
            val currentEntryState = remember { mutableStateOf("") }
            var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
            val navigator = rememberNavigator()

            GetCurrentEntry(
                navigator = navigator,
                onEntryChanged = {
                    currentEntryState.value = it
                }
            )

            val items = listOf(
                BottomNavigationItem(
                    title = stringResource(Res.string.home_name_nav),
                    selectedIcon = vectorResource(Res.drawable.ic_navbar_home),
                    unSelectedIcon = vectorResource(Res.drawable.ic_navbar_home),
                    hasNews = false,
                    badgeCount = null,
                    route = ScreenRoute.HomeScreen.route
                ),
                BottomNavigationItem(
                    title = stringResource(Res.string.outdoor_name_nav),
                    selectedIcon = vectorResource(Res.drawable.ic_navbar_outdoor),
                    unSelectedIcon = vectorResource(Res.drawable.ic_navbar_outdoor),
                    hasNews = false,
                    badgeCount = null,
                    route = ScreenRoute.OutdoorScreen.route
                ),
                BottomNavigationItem(
                    title = stringResource(Res.string.map_name_nav),
                    selectedIcon = vectorResource(Res.drawable.ic_navbar_map),
                    unSelectedIcon = vectorResource(Res.drawable.ic_navbar_map),
                    hasNews = false,
                    badgeCount = null,
                    route = ScreenRoute.MapScreen.route
                ),

                BottomNavigationItem(
                    title = stringResource(Res.string.domofon_name_nav),
                    selectedIcon = vectorResource(Res.drawable.ic_navbar_domofon),
                    unSelectedIcon = vectorResource(Res.drawable.ic_navbar_domofon),
                    hasNews = false,
                    badgeCount = null,
                    route = ScreenRoute.DomofonScreen.route
                ),
                BottomNavigationItem(
                    title = stringResource(Res.string.help_name_nav),
                    selectedIcon = vectorResource(Res.drawable.ic_navbar_help),
                    unSelectedIcon = vectorResource(Res.drawable.ic_navbar_help),
                    hasNews = false,
                    badgeCount = null,
                    route = ScreenRoute.HelpScreen.route
                )
            )

            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
            Scaffold(
                bottomBar = {
                    if (!currentEntryState.value.contains("webview_screen")) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                            // .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp)),
                        ) {
                            NavigationBar {

                                items.forEachIndexed { index, item ->
                                    NavigationBarItem(
//                                            modifier = Modifier.background(Color.Green),
                                        selected = selectedItemIndex == index,
                                        onClick = {
                                            selectedItemIndex = index
                                            navigator.navigate(item.route)
                                        },
                                        label = {
                                            Text(
                                                text = item.title,
                                                fontSize = if (index == selectedItemIndex) 11.sp
                                                else 10.sp
                                            )
                                        },
                                        icon = {
                                            Icon(
                                                imageVector = if (index == selectedItemIndex) {
                                                    item.selectedIcon
                                                } else item.unSelectedIcon,
                                                contentDescription = item.title
                                            )
                                        },
                                        colors = NavigationBarItemDefaults.colors(
                                            selectedIconColor = ColorCustomResources.colorBazaMainRed,
                                            selectedTextColor = ColorCustomResources.colorBazaMainRed,
                                            unselectedIconColor = ColorCustomResources.colorBazaMainBlue,
                                            unselectedTextColor = ColorCustomResources.colorBazaMainBlue,
                                            indicatorColor = Color.LightGray
                                        ),

                                    )

                                }


                            }
                        }
                    }
                }

            ) { paddingValues ->

                // передаем падинг чтобы список BottomNavigationBar не накладывался по поверх списка
                Box(

                   // modifier = Modifier.shadow(40.dp)
//                        modifier = Modifier
//                            .padding(paddingValues = paddingValues)
                ) {
                    // было
                    // Log.d("4444", " MainScreensActivity SetPermissionsAndNavigation box ")
                    //вызывается 3 раза

                    // MainScreensNavigationGraph(navHostController = navController)

                    NavHostScreenScenes(navigator = navigator)
                }
            }
            //   }
               }

            val lazyListState: LazyListState = rememberLazyListState()
            //     }
        }
    }

}