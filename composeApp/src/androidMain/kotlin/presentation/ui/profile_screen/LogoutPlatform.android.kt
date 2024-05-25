package presentation.ui.profile_screen

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.dev_marinov.my_compose_multi.AuthActivity

actual class LogoutPlatform actual constructor() {
    @Composable
    actual fun Logout() {
        val context = LocalContext.current
        context.startActivity(Intent(context, AuthActivity::class.java))
    }

}