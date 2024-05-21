package util

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

object SnackbarBackOnlineHelper {
//    fun execute(rootConstraintLayout: ConstraintLayout): Snackbar {
//        val context = rootConstraintLayout.context
//        return Snackbar.make(
//            rootConstraintLayout,
//            context.getString(R.string.back_online),
//            Snackbar.LENGTH_LONG
//        )
//            .setBackgroundTint(context.resources.getColor(R.color.bazanet_main_color, null))
//            .setTextColor(context.resources.getColor(R.color.white, null))
//    }


    @Composable
    fun execute(isShow: Boolean, text: String) {
        val snackBarHostState = remember { SnackbarHostState() }

        if (!isShow) {
            snackBarHostState.currentSnackbarData?.dismiss()
        } else {
            SnackbarHost(
                hostState = snackBarHostState,
                snackbar = {
                    Snackbar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
//                    dismissAction = {
//                        Logger.d { " 4444 2 snackBarState.value=" + snackBarState.value }
//                        snackBarState.value = false
//                    },
                        actionOnNewLine = true,
//                        action = {
//                            Button(modifier = Modifier.padding(8.dp), onClick = {
//                                snackBarHostState.currentSnackbarData?.dismiss()
//                            }) {
//                                Text("Да понятно")
//                            }
//                        },
                        shape = RoundedCornerShape(20.dp),
                    ) {
                        Text(text = text)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                //.align(Alignment.BottomCenter).navigationBarsPadding()
                // .padding(paddingValue)
            )
        }
    }
}