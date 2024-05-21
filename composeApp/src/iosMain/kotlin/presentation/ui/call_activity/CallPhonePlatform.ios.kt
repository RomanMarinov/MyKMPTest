package presentation.ui.call_activity

import androidx.compose.runtime.Composable
import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual class CallPhonePlatform actual constructor() {
    @Composable
    actual fun MakeCall() {
        val nsUrl = NSURL(string = "tel://88001000249")
        val application = UIApplication.sharedApplication()
        if (application.canOpenURL(nsUrl)) {
            application.openURL(nsUrl)
        }
    }

    @Composable
    actual fun getFingerprint(): String {
        TODO("Not yet implemented")
    }
}