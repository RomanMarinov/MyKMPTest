
import androidx.compose.runtime.Composable
import moe.tlaster.precompose.navigation.NavHost
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.path
import moe.tlaster.precompose.navigation.transition.NavTransition
import presentation.ui.domofon_screen.DomofonScreen
import presentation.ui.help_screen.HelpScreen
import presentation.ui.home_screen.HomeScreen
import presentation.ui.internet_tv_screen.InternetTvScreen
import presentation.ui.map_screen.MapScreen
import presentation.ui.outdoor_screen.OutdoorScreen
import presentation.ui.profile_screen.ProfileScreen
import presentation.ui.webview_screen.WebViewScreen
import util.ScreenRoute


@Composable
fun NavHostScreenScenes(navigator: Navigator) {
    NavHost(
        navigator = navigator, // Назначаем навигатор NavHost
        navTransition = NavTransition(), // Переход навигации для сцен в этом NavHost, это необязательно
        initialRoute = ScreenRoute.HomeScreen.route, // Начальный пункт назначения
    ) {
        scene(
            // Определить сцену для навигационного графа
            route = ScreenRoute.HomeScreen.route,  // Путь маршрута сцены
            navTransition = NavTransition(),  // Переход навигации для этой сцены, это необязательно
        ) {
            HomeScreen(navigator = navigator)
        }

        scene(
            // Определить сцену для навигационного графа
            route = ScreenRoute.OutdoorScreen.route,  // Путь маршрута сцены
            navTransition = NavTransition(),  // Переход навигации для этой сцены, это необязательно
        ) {
            OutdoorScreen(navigator = navigator)
        }

        scene(
            // Определить сцену для навигационного графа
            route = ScreenRoute.MapScreen.route,  // Путь маршрута сцены
            navTransition = NavTransition(),  // Переход навигации для этой сцены, это необязательно
        ) {
            MapScreen(navigator = navigator)
        }

        scene(
            // Определить сцену для навигационного графа
            route = ScreenRoute.DomofonScreen.route,  // Путь маршрута сцены
            navTransition = NavTransition(),  // Переход навигации для этой сцены, это необязательно
        ) {
            DomofonScreen(navigator = navigator)
        }

        scene(
            // Определить сцену для навигационного графа
            route = ScreenRoute.HelpScreen.route,  // Путь маршрута сцены
            navTransition = NavTransition(),  // Переход навигации для этой сцены, это необязательно
        ) {
            HelpScreen(navigator = navigator)
        }

        scene(
            // Определить сцену для навигационного графа
            route = "/webview_screen/{address}/{videourl}",  // Путь маршрута сцены
            navTransition = NavTransition(),  // Переход навигации для этой сцены, это необязательно
        ) {
            val address: String? = it.path<String>("address")
            val videoUrl: String? = it.path<String>("videourl")
            WebViewScreen(
                navigator = navigator,
                address = address,
                videoUrl = videoUrl
            )
        }

        scene(
            // Определить сцену для навигационного графа
            route = ScreenRoute.ProfileScreen.route,  // Путь маршрута сцены
            navTransition = NavTransition(),  // Переход навигации для этой сцены, это необязательно
        ) {
            ProfileScreen(
                navigator = navigator
            )
        }

        scene(
            route = ScreenRoute.InternetTvScreen.route,
            navTransition = NavTransition()
        ) {
            InternetTvScreen(navigator = navigator)
        }
    }
}