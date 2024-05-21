package presentation.ui.call_activity

import androidx.compose.runtime.Composable

expect class CallPhonePlatform() {
    @Composable
    fun MakeCall()

    @Composable
    fun getFingerprint() : String
}