import androidx.compose.ui.window.ComposeUIViewController
import di.commonModule
import org.koin.core.context.startKoin
import org.koin.core.context.startKoin
import org.koin.mp.KoinPlatform.startKoin

actual fun getPlatformName(): String = "iOS"

fun MainViewController() = ComposeUIViewController { App() }

fun initKoin() {
    startKoin {
        modules(commonModule())
    }
}