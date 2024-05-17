
import androidx.compose.ui.window.ComposeUIViewController
import di.commonModule
import org.koin.core.context.startKoin

fun MainViewController() = ComposeUIViewController {

    App()
}

fun initKoin() {
    startKoin {
        modules(commonModule())
    }
}

