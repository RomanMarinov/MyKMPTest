import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.graphics.painter.Painter
import moe.tlaster.precompose.PreComposeApp
import mykmptest.composeapp.generated.resources.Res
import mykmptest.composeapp.generated.resources.ic_navbar_domofon
import mykmptest.composeapp.generated.resources.ic_navbar_help
import mykmptest.composeapp.generated.resources.ic_navbar_home
import mykmptest.composeapp.generated.resources.ic_navbar_map
import mykmptest.composeapp.generated.resources.ic_navbar_moi_dvor
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


data class BottomNavigationItem(
    val title: String,
    val selectedIcon: Painter,
    val unSelectedIcon: Painter,
    val hasNews: Boolean,
    val badgeCount: Int? = null
)

@OptIn(ExperimentalResourceApi::class)
@Composable
@Preview
fun App() {
    MaterialTheme {

        PreComposeApp {
            var selectedItemIndex by rememberSaveable { mutableIntStateOf(0) }
            val items = listOf(
                BottomNavigationItem(
                    title = "Baza.net",
                    selectedIcon = painterResource(Res.drawable.ic_navbar_home),
                    unSelectedIcon = painterResource(Res.drawable.ic_navbar_home),
                    hasNews = false,
                    badgeCount = null
                ),
                BottomNavigationItem(
                    title = "Мой двор",
                    selectedIcon = painterResource(Res.drawable.ic_navbar_moi_dvor),
                    unSelectedIcon = painterResource(Res.drawable.ic_navbar_moi_dvor),
                    hasNews = false,
                    badgeCount = null
                ),
                BottomNavigationItem(
                    title = "Карта",
                    selectedIcon = painterResource(Res.drawable.ic_navbar_map),
                    unSelectedIcon = painterResource(Res.drawable.ic_navbar_map),
                    hasNews = false,
                    badgeCount = null
                ),

                BottomNavigationItem(
                    title = "Домофон",
                    selectedIcon = painterResource(Res.drawable.ic_navbar_domofon),
                    unSelectedIcon = painterResource(Res.drawable.ic_navbar_domofon),
                    hasNews = false,
                    badgeCount = null
                ),
                BottomNavigationItem(
                    title = "huettins",
                    selectedIcon = painterResource(Res.drawable.ic_navbar_help),
                    unSelectedIcon = painterResource(Res.drawable.ic_navbar_help),
                    hasNews = false,
                    badgeCount = null
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
                                        //  navController.navigate(item.title)
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
                                                painter = if (index == selectedItemIndex) {
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
                    // botto
                }
            }



//            var selectedItem by remember { mutableIntStateOf(0) }
//            val items = listOf("Songs", "Artists", "Playlists")
//
//            NavigationBar {
//                items.forEachIndexed { index, item ->
//                    NavigationBarItem(
//                        icon = { Icon(Icons.Filled.Favorite, contentDescription = item) },
//                        label = { Text(item) },
//                        selected = selectedItem == index,
//                        onClick = { selectedItem = index }
//                    )
//                }
//            }

            val lazyListState: LazyListState = rememberLazyListState()


        }


//        var showContent by remember { mutableStateOf(false) }
//        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//            Button(onClick = { showContent = !showContent }) {
//                Text("Click me now!")
//            }
//            AnimatedVisibility(showContent) {
//                val greeting = remember { Greeting().greet() }
//                Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
//                   // Image(painterResource(Res.drawable.compose_multiplatform), null)
//                    Image(painterResource(Res.drawable.ic_navbar_domofon), null)
//                    Text("Compose: $greeting")
//                }
//            }
//        }
    }
}