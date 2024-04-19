import androidx.compose.ui.window.ComposeUIViewController

import org.koin.core.context.startKoin
import di.commonModule


fun MainViewController() = ComposeUIViewController { App() }

fun initKoin() {
    startKoin {
        modules(commonModule())
    }
}