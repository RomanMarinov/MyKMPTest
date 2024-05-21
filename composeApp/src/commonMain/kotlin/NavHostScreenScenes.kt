import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import presentation.ui.domofon_screen.DomofonScreen
import presentation.ui.help_screen.HelpScreen
import presentation.ui.home_screen.HomeScreen
import presentation.ui.internet_tv_screen.InternetTvScreen
import presentation.ui.map_screen.MapScreen
import presentation.ui.outdoor_screen.OutdoorScreen
import presentation.ui.profile_screen.ProfileScreen
import presentation.ui.webview_screen.WebViewScreen
import util.ScreenRoute


const val ADDRESS = "ADDRESS"
const val VIDEO_URL = "VIDEO_URL"

@Composable
fun NavHostScreenScenes(navHostController: NavHostController) {

    NavHost(navController = navHostController, startDestination = ScreenRoute.HomeScreen.route) {
        composable(route = ScreenRoute.HomeScreen.route) {
            HomeScreen(navHostController = navHostController)
        }

        composable(
            route = ScreenRoute.OutdoorScreen.route,
        ) {
            OutdoorScreen(navHostController = navHostController)
        }

        composable(
            route = ScreenRoute.OutdoorScreen.route,
        ) {
            MapScreen(navHostController = navHostController)
        }

        composable(
            route = ScreenRoute.OutdoorScreen.route,
        ) {
            DomofonScreen(navHostController = navHostController)
        }

        composable(
            route = ScreenRoute.OutdoorScreen.route,
        ) {
            HelpScreen(navHostController = navHostController)
        }

        composable(
            route = ScreenRoute.WebViewScreen.route,
            arguments = listOf(
                navArgument(ADDRESS) {
                    type = NavType.StringType
                },
                navArgument(VIDEO_URL) {
                    type = NavType.StringType
                }
            )
        ) {
            val address = it.arguments?.getString(ADDRESS)
            val videoUrl = it.arguments?.getString(VIDEO_URL)
            WebViewScreen(
                navHostController = navHostController,
                address = address,
                videoUrl = videoUrl
            )
        }

        composable(
            route = ScreenRoute.ProfileScreen.route
        ) {
            ProfileScreen(navHostController = navHostController)
        }

        composable(
            route = ScreenRoute.InternetTvScreen.route
        ) {
            InternetTvScreen(navHostController = navHostController)
        }
    }
}