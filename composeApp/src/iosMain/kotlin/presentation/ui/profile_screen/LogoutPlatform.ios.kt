package presentation.ui.profile_screen

import androidx.compose.runtime.Composable
import presentation.ui.auth_activity.AuthActivityContent

actual class LogoutPlatform actual constructor() {
    @Composable
    actual fun Logout() {
        AuthActivityContent(
            onShowSnackBarAuthPhone = {},
            onShowWarning = {},
            onMoveToMainActivity = {},
            onShowSnackBarAuthWiFi = {}
        )
    }
}