
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import di.commonModule
import org.koin.core.context.startKoin
import presentation.ui.call_activity.CallActivityContent

fun MainViewController() = ComposeUIViewController {

    val moveState = remember { mutableStateOf(false) }

    CallActivityContent(
        onMoveToMainActivity = {
            moveState.value = true
        },
        onMakeCall = {

        }
    )
    if(moveState.value) {
       // moveState.value = false
        App()
    }

   // App()

}



fun initKoin() {
    startKoin {
        modules(commonModule())
    }
}

