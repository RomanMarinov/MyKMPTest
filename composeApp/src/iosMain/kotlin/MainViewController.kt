
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import di.commonModule
import org.koin.core.context.startKoin
import util.SnackBarHostHelper

fun MainViewController() = ComposeUIViewController {

    val moveState = remember { mutableStateOf(false) }
    val snackBarState = remember { mutableIntStateOf(-1) }

//    CallActivityContent(
//        onMoveToMainActivity = {
//            moveState.value = true
//        },
//        onShowSnackBarAuth = {value ->
//            value?.let {
//                snackBarState.intValue = it
//            }
//        }
//    )


    if (moveState.value) {
        // moveState.value = false
        App()
    }

    when(snackBarState.intValue) {
        404 -> {
            // statusCodeSnackBarState = -1
            SnackBarHostHelper.WithOkButton(
                message = "С указанного номера не было звонка",
                onShowSnackBarAuth = {
                    snackBarState.intValue - 1
                }
            )
        }
        0 -> {
            //statusCodeSnackBarState = -1
            SnackBarHostHelper.WithOkButton(
                message = "Hе правильно введен номер телефона",
                onShowSnackBarAuth = {
                    snackBarState.intValue - 1
                }
            )
        }
    }

}


fun initKoin() {
    startKoin {
        modules(commonModule())
    }
}

