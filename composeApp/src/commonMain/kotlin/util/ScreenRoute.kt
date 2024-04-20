package util

const val ADDRESS = "address"
const val VIDEOURL = "videourl"

sealed class ScreenRoute(val route: String) {
    object HomeScreen : ScreenRoute("/baza_screen")
    object OutdoorScreen : ScreenRoute("/outdoor_screen")
    object MapScreen : ScreenRoute("/map_screen")
    object DomofonScreen : ScreenRoute("/domofon_screen")
    object HelpScreen : ScreenRoute("/help_screen")

    object WebViewScreen : ScreenRoute("/webview_screen/{$ADDRESS}/{$VIDEOURL}") {
        fun withArgs(address: String, videourl: String) : String {
            return "/webview_screen/$address/$videourl"
        }
    }

    object ProfileScreen : ScreenRoute("/profile_screen")
}