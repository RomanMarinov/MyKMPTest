package util
sealed class ScreenRoute(val route: String) {
    object HomeScreen : ScreenRoute("/baza_screen")
    object OutdoorScreen : ScreenRoute("/outdoor_screen")
    object MapScreen : ScreenRoute("/map_screen")
    object DomofonScreen : ScreenRoute("/domofon_screen")
    object HelpScreen : ScreenRoute("/help_screen")

    object WebViewScreen : ScreenRoute("/webview_screen")

    object ProfileScreen : ScreenRoute("/profile_screen")
}