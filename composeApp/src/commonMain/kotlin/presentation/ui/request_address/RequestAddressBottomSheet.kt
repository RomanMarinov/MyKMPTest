package presentation.ui.request_address

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import domain.add_address.Data

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RequestAddressBottomSheet(
    address: String,
    dataAddress: Data?,
    navigationFrom: String,
    openBottomSheet: (Boolean) -> Unit
) {
    val openBottomSheetState by rememberSaveable { mutableStateOf(true) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val interactionSource = remember { MutableInteractionSource() }
    val inputTextPhoneNumber = remember { mutableStateOf("") }
    if (openBottomSheetState) {
        ModalBottomSheet(
            modifier = Modifier
                .fillMaxWidth(),
//            containerColor = Color.White, не
//            contentColor = Color.White,
            onDismissRequest = { openBottomSheet(false) },
            sheetState = bottomSheetState,
            dragHandle = {
//                Column(
//                    modifier = Modifier
//                        .fillMaxWidth()
//
//                        .background(Color.White),
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    BottomSheetDefaults.DragHandle()
//                }
            },
            shape = RoundedCornerShape(
                topStart = 20.dp,
                topEnd = 20.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
            ) {


            }
        }
    }


}