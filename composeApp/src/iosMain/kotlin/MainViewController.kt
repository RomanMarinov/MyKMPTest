
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.window.ComposeUIViewController
import co.touchlab.kermit.Logger
import di.commonModule
import org.koin.core.context.startKoin
import presentation.ui.auth_activity.AuthActivityContent
import presentation.ui.splash_activity.SplashActivityContent
import util.SnackBarHostHelper
import util.StartActivity

fun MainViewController() = ComposeUIViewController {

    val moveState = remember { mutableStateOf(false) }
    val snackBarStateAuthPhone = remember { mutableIntStateOf(-1) }
    val snackBarStateAuthWiFi = remember { mutableStateOf("") }
    val nextActivityState = remember { mutableStateOf(StartActivity.DEFAULT) }
    val snackBarStateWarning = remember { mutableStateOf(false) }

    SplashActivityContent(
        onMoveToNextActivity = {
            nextActivityState.value = it
        }
    )

    if (snackBarStateAuthWiFi.value.isNotEmpty()) {
        SnackBarHostHelper.WithOkButton(
            message = snackBarStateAuthWiFi.value,
//            onAction = {
//                snackBarStateAuthWiFi.value = ""
//            }
        )
    }

    Logger.d("4444 nextActivityState.value=" + nextActivityState.value)
    when (nextActivityState.value) {
        StartActivity.AUTH_ACTIVITY -> {
            AuthActivityContent(
                onMoveToMainActivity = {
                    moveState.value = true
                },
                onShowSnackBarAuthPhone = {
                    snackBarStateAuthPhone.intValue = it
                },
                onShowSnackBarAuthWiFi = {
                    snackBarStateAuthWiFi.value = it
                },
                onShowWarning = {
                    snackBarStateWarning.value = it
                }
            )

            when (snackBarStateAuthPhone.intValue) {
                404 -> {
                    Logger.d("4444 MainViewController 404 С указанного номера не было звонка")
                    SnackBarHostHelper.WithOkButton(
                        message = "С указанного номера не было звонка",
//                onAction = {
//                    snackBarStateAuthPhone.intValue - 1
//                }
                    )
                }

                0 -> {
                    Logger.d("4444 MainViewController 0 Ошибка авторизации")
                    SnackBarHostHelper.WithOkButton(
                        message = "Ошибка авторизации",
//                onAction = {
//                    snackBarStateAuthPhone.intValue - 1
//                }
                    )
                }
            }

            if(snackBarStateWarning.value) {
                SnackBarHostHelper.ShortShortTime(
                    message = "Проверьте правильность введенного номера",
                    onFinishTime = {
                        snackBarStateWarning.value = false
                    }
                )
            }
        }

        StartActivity.HOME_ACTIVITY -> {
            App()
        }

        StartActivity.DEFAULT -> { // ничего }
        }
    }

    if (moveState.value) {

        Logger.d("4444 moveState.value=" + moveState.value)
        App()
    }
}

fun initKoin() {
    startKoin {
        modules(commonModule())
    }
}

