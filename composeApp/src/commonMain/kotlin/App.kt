import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

import moe.tlaster.precompose.PreComposeApp
import moe.tlaster.precompose.navigation.rememberNavigator
import mykmptest.composeapp.generated.resources.Res
import mykmptest.composeapp.generated.resources.ic_navbar_domofon
import mykmptest.composeapp.generated.resources.ic_navbar_help
import mykmptest.composeapp.generated.resources.ic_navbar_home
import mykmptest.composeapp.generated.resources.ic_navbar_map
import mykmptest.composeapp.generated.resources.ic_navbar_outdoor
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.vectorResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import util.ScreenRoute


data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
    val hasNews: Boolean,
    val badgeCount: Int? = null,
    val route: String
)

//@OptIn(ExperimentalResourceApi::class)
//@Composable
//fun imageResource(resource: DrawableResource): ImageBitmap {
//    val context = ContextAmbient.current
//    val drawable = ContextCompat.getDrawable(context, resource) ?: error("Resource not found")
//    val bitmap = (drawable as BitmapDrawable).bitmap
//    return bitmap.asImageBitmap()
//}

@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun App() {
    MaterialTheme {



        PreComposeApp {


//            val res = imageResource(Res.drawable.ic_navbar_map)
//            val res = vectorResource(Res.drawable.ic_navbar_home)

            val scope = CoroutineScope(Dispatchers.IO)
            scope.launch  {
               // val appName = getString(Res.string.hello)
            }

            var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
            val navigator = rememberNavigator()





            val items = listOf(
                BottomNavigationItem(
                    title = "Baza.net",
                    selectedIcon = vectorResource(Res.drawable.ic_navbar_home),
                    unSelectedIcon = vectorResource(Res.drawable.ic_navbar_home),
                    hasNews = false,
                    badgeCount = null,
                    route = ScreenRoute.HomeScreen.route
                ),
                BottomNavigationItem(
                    title = "Мой двор",
                    selectedIcon = vectorResource(Res.drawable.ic_navbar_outdoor),
                    unSelectedIcon = vectorResource(Res.drawable.ic_navbar_outdoor),
                    hasNews = false,
                    badgeCount = null,
                    route = ScreenRoute.OutdoorScreen.route
                ),
                BottomNavigationItem(
                    title = "Карта",
                    selectedIcon = vectorResource(Res.drawable.ic_navbar_map),
                    unSelectedIcon = vectorResource(Res.drawable.ic_navbar_map),
                    hasNews = false,
                    badgeCount = null,
                    route = ScreenRoute.MapScreen.route
                ),

                BottomNavigationItem(
                    title = "Домофон",
                    selectedIcon = vectorResource(Res.drawable.ic_navbar_domofon),
                    unSelectedIcon = vectorResource(Res.drawable.ic_navbar_domofon),
                    hasNews = false,
                    badgeCount = null,
                    route = ScreenRoute.DomofonScreen.route
                ),
                BottomNavigationItem(
                    title = "Помощь",

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
                        NavigationBar {
                            items.forEachIndexed { index, item ->
                                NavigationBarItem(
                                    selected = selectedItemIndex == index,
                                    onClick = {
                                        selectedItemIndex = index


                                        navigator.navigate(item.route)


                                    },
                                    label = {
                                        Text(text = item.title)
                                    },
                                    icon = {
                                        BadgedBox(
                                            badge = {
                                                if (item.badgeCount != null) {
                                                    Badge {
                                                        Text(text = item.badgeCount.toString())
                                                    }
                                                } else if(item.hasNews) {
                                                    Badge()
                                                }
                                            }
                                        ) {
                                            Icon(
                                                imageVector = if (index == selectedItemIndex) {
                                                    item.selectedIcon
                                                } else item.unSelectedIcon,
                                                contentDescription = item.title
                                            )

                                        }
                                    }
                                )

                            }

                        }


                    }

                ) {
                        paddingValues ->



                    // передаем падинг чтобы список BottomNavigationBar не накладывался по поверх списка
                    Box(
                        modifier = Modifier
                           // .background(colorResource(id = R.color.main_violet_light))
                            .padding(paddingValues = paddingValues)
                    ) {
                        // было
                       // Log.d("4444", " MainScreensActivity SetPermissionsAndNavigation box ")
                        //вызывается 3 раза

                       // MainScreensNavigationGraph(navHostController = navController)

                        Nav(navigator = navigator)
                    }
                }
            }


            val lazyListState: LazyListState = rememberLazyListState()




        }
    }
}