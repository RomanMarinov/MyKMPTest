package presentation.ui.call_activity

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

actual class CallPhonePlatform actual constructor() {
    @Composable
    actual fun MakeCall() {
        val context = LocalContext.current
        val uri = Uri.parse("tel:" + "88001000249")
        val intent = Intent(Intent.ACTION_DIAL, uri)
        context.startActivity(intent)
    }
}