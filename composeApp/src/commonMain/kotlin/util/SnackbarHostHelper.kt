package util

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

object SnackbarHostHelper {
    @Composable
    fun Snackbar(
        text: String
        ) {
        val snackbarHostState = remember { SnackbarHostState() }

        SnackbarHost(
            hostState = snackbarHostState,
            snackbar = {
                Snackbar(
                    modifier = Modifier
                        .padding(8.dp),
//                    dismissAction = {
//                        Logger.d { " 4444 2 snackBarState.value=" + snackBarState.value }
//                        snackBarState.value = false
//                    },
                    actionOnNewLine = true,
                    action = {
                        Button(modifier = Modifier.padding(8.dp), onClick = {
                            snackbarHostState.currentSnackbarData?.dismiss()
                        }) {
                            Text("Понятно")
                        }
                    },
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

