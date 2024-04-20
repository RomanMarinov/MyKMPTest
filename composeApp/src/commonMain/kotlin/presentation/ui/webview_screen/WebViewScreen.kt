package presentation.ui.webview_screen

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import kotlinx.serialization.json.Json
import moe.tlaster.precompose.navigation.Navigator
import net.thauvin.erik.urlencoder.UrlEncoderUtil

@Composable
fun WebViewScreen(
    navigator: Navigator,
    address: String?,
    videoUrl: String?
) {


    videoUrl?.let {
        val decodedUrl = UrlEncoderUtil.decode(it)
        Text("WebViewScreen address=" + address + " " + decodedUrl)
    }
}