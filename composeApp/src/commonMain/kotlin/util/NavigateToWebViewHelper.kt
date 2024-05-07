package util

import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import moe.tlaster.precompose.navigation.PopUpTo
import net.thauvin.erik.urlencoder.UrlEncoderUtil

fun navigateToWebViewHelper(navigator: Navigator, route: String, address: String, videoUrl: String) {
    val videoUrlEncode = UrlEncoderUtil.encode(videoUrl)
    navigator.navigate(
        ScreenRoute.WebViewScreen.withArgs(
            address = address,
            videourl = videoUrlEncode
        ),
        NavOptions(
            popUpTo = PopUpTo(
                // The destination of popUpTo
                route = route,
                // Whether the popUpTo destination should be popped from the back stack.
                inclusive = false,
            )
        )
    )
}