package util

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

object SnackBarHostHelper {

    @Composable
    fun WithOkButton(message: String) {
        var isSnackBarVisible by remember { mutableStateOf(true) }
        if (isSnackBarVisible) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 22.dp)
                    .navigationBarsPadding(),
                contentAlignment = Alignment.BottomEnd
            ) {
                Snackbar(
                    modifier = Modifier.padding(16.dp),
                    actionOnNewLine = true,
                    action = {
                        TextButton(onClick = {
                            isSnackBarVisible = false
                        }) {
                            Text(
                                text = "OK",
                                color = Color.White
                            )
                        }
                    }
                ) {
                    Text(text = message)
                }
            }
        }
    }


    @Composable
    fun ShortShortTime(message: String, paddingValue: PaddingValues) {
        val snackBarHostState = remember { SnackbarHostState() }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValue)
                .navigationBarsPadding(),
            contentAlignment = Alignment.BottomEnd
        ) {
            SnackbarHost(
                hostState = snackBarHostState,
                modifier = Modifier.fillMaxWidth()
            ) { snackBarData ->
                Snackbar(
//                    modifier = Modifier.padding(16.dp),
                    snackbarData = snackBarData,
                    //contentColor = Color.Gray
                )
            }
        }

        LaunchedEffect(Unit) {
            snackBarHostState.showSnackbar(message = message)
            delay(5000)
            snackBarHostState.currentSnackbarData?.dismiss()
        }
    }
}